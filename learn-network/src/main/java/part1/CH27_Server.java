/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by liekkas on 15/4/30.
 */
public class CH27_Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        while (true){
            Socket socket = serverSocket.accept();
//            socket.close();
            serverSocket.close();

            if (serverSocket.isBound() && !serverSocket.isClosed()){
                System.out.println(">>> 服务器正常运行");
            }else {
                System.out.println(">>> 服务器挂了...");
            }
        }
    }
}
