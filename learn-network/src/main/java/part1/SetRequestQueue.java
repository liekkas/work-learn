/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by liekkas on 15/4/30.
 *
 * 先运行SetRequestQueue，再运行TestRequestQueue
 */
public class SetRequestQueue
{
    public static void main(String[] args) throws Exception
    {
        int queueLength = 5;
        ServerSocket serverSocket = new ServerSocket(1234, queueLength);
        System.out.println("端口(1234)已经绑定，请按回车键开始处理客户端请求！");
        System.in.read();
        int n = 0;
        while (true)
        {
            System.out.println("<准备接收第" + (++n) + "个客户端请求！");
            Socket socket = serverSocket.accept();
            System.out.println("正在处理第" + n + "个客户端请求");
            Thread.sleep(3000);
            System.out.println("第" + n + "个客户端请求已经处理完毕!>");
        }
    }
}

