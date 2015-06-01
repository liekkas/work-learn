/*
 * Copyright (c) www.ultrapower.com.cn
 */

package ch04;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by liekkas on 15/5/8.
 *
 * 原生阻塞方式
 */
public class PlainOioServer {

    public void server(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true){
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);

                new Thread(new Runnable() {
                    public void run() {
                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();

                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (Exception e){
            e.printStackTrace();
            serverSocket.close();
        }
    }

}
