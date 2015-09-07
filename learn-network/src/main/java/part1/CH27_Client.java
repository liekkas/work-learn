/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by liekkas on 15/4/30.
 */
public class CH27_Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost",1234);
        Thread.sleep(1000);

        System.out.println(">>> read():" + socket.getInputStream().read());
        System.out.println(">>> isConnected():" + socket.isConnected());
        System.out.println(">>> isClosed():" + socket.isClosed());

    }
}
