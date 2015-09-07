/*
 * Copyright (c) www.ultrapower.com.cn
 */

package tcp;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by liekkas on 15/4/29.
 */
public class FileServer {
    public static void main(String args[]){
        try {
            final ServerSocket serverSocket = new ServerSocket(2000);

            new Thread(new Runnable() {
                public void run() {
                    while (true){
                        try {
                            System.out.println(">>> 开始侦听...");
                            Socket socket = serverSocket.accept();

                            System.out.println(">>> 处理一个连接：");
                            receiveFile(socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).run();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveFile(Socket socket) throws IOException {
        byte[] inputByte = new byte[1024];
        int length = 0;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        String filePath = "learn-network/src/main/java/tcp/recv/"+new Random().nextInt(10000);

        dis = new DataInputStream(socket.getInputStream());
        File f = new File("learn-network/src/main/java/tcp/recv");
        if(!f.exists()){
            f.mkdir();
        }

        fos = new FileOutputStream(new File(filePath));

        System.out.println(">>> 开始接收数据。。。");

        while ((length = dis.read(inputByte,0,inputByte.length)) > 0){
            fos.write(inputByte,0,length);
            fos.flush();
        }

        System.out.println(">>> 完成接收！");

        if(fos != null) fos.close();
        if(dis != null) dis.close();
        if(socket != null) socket.close();
    }
}
