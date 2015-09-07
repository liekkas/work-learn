/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 */

import java.net.Socket;
import java.net.*;

public class CH29
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(1234);
        serverSocket.setReceiveBufferSize(2048); // 将接收缓冲区设为2K
        while (true)
        {
            Socket socket = serverSocket.accept();
            // 如果客户端请求使用的是本地IP地址，重新将Socket对象的接
            // 收缓冲区设为1K
            if (socket.getInetAddress().isLoopbackAddress())
                socket.setReceiveBufferSize(1024);
            System.out.println("serverSocket:"
                    + serverSocket.getReceiveBufferSize());
            System.out.println("socket:" + socket.getReceiveBufferSize());
            socket.close();
        }
    }
}

