/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by liekkas on 15/4/30.
 * 获取ServerSocket信息的方法及FTP原理
 * 当构造方法中端口为0时会自动生成端口，一般适合短连接场景
 */
public class CH28 {

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 5; i++) {
            ServerSocket serverSocket = new ServerSocket(0);
//            serverSocket.bind(new InetSocketAddress("localhost",2345));
            System.out.println(serverSocket.getInetAddress()+ ":" +serverSocket.getLocalPort());
        }
    }
}
