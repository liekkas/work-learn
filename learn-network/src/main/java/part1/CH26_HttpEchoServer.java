/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by liekkas on 15/4/30.
 * ch26 在服务端接收和发送数据
 */
public class CH26_HttpEchoServer extends Thread {

    private Socket socket;

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            osw.write("HTTP/1.1 200 OK\r\n\r\n");

            String s = "";
            while (!(s = br.readLine()).equals("")){
                osw.write("<html><body>"+s+"<br></body></html>");
            }
            osw.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CH26_HttpEchoServer(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println(">>> 服务器已启动，端口：8888");
        while (true){
            Socket socket = serverSocket.accept();
            new CH26_HttpEchoServer(socket).start();
        }
    }
}
