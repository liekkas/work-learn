/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by liekkas on 15/4/29.
 */
public class CH14_01 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("lab",22);
        System.out.println("connection!");
        socket.close();
    }
}
