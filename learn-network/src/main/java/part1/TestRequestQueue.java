/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.net.Socket;

/**
 * Created by liekkas on 15/4/30.
 * 先运行SetRequestQueue，再运行TestRequestQueue
 */
public class TestRequestQueue {

    public static void main(String[] args) throws Exception
    {
        for (int i = 0; i < 10; i++)
        {
            Socket socket = new Socket("localhost", 1234);
            socket.getOutputStream().write(1);
            System.out.println("已经成功创建第" + String.valueOf(i + 1) + "个客户端连接!");
        }
    }

}
