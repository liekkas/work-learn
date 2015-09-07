/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Created by liekkas on 15/4/29.
 */
public class CH15 {
    public static void main(String[] args) {
        try {
            Socket socket1 = new Socket("www.baidu.com",80);
            SocketAddress socketAddress = socket1.getRemoteSocketAddress();
            socket1.close();;

            Socket socket2 = new Socket();
            socket2.connect(socketAddress);

            InetSocketAddress inetSocketAddress1 = (InetSocketAddress)socketAddress;
            System.out.println("服务器域名:"
                    + inetSocketAddress1.getAddress().getHostName());
            System.out.println("服务器IP:"
                    + inetSocketAddress1.getAddress().getHostAddress());
            System.out.println("服务器端口:" + inetSocketAddress1.getPort());
            InetSocketAddress inetSocketAddress2 = (InetSocketAddress) socket2
                    .getLocalSocketAddress();
            System.out.println("本地IP:"
                    + inetSocketAddress2.getAddress().getLocalHost()
                    .getHostAddress());
            System.out.println("本地端口:" + inetSocketAddress2.getPort());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
