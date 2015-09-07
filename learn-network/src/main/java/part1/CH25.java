/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by liekkas on 15/4/30.
 * 创建ServerSocket对象
 */
public class CH25 {
    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            try {
                ServerSocket ss = new ServerSocket(i,0, InetAddress.getByName("lab"));
                ss.close();
                System.out.println(">>> 端口未打开：" + i);
            } catch (IOException e) {
//                System.out.println(">>> 端口已打开：" + i);
            }
        }
    }
}
