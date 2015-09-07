/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by liekkas on 15/4/29.
 * DNS缓存
 * http://androidguy.blog.51cto.com/974126/214814
 */
public class CH04 {
    public static void main(String[] args) throws IOException {
        // args[0]: 本机名 args[1]：缓冲时间

        String hostname = "lab";
        String duration = 5+"";
        java.security.Security.setProperty("networkaddress.cache.ttl", duration);
        long time = System.currentTimeMillis();
        InetAddress addresses1[] = InetAddress.getAllByName(hostname);
        System.out.println("addresses1:   "
                + String.valueOf(System.currentTimeMillis() - time)
                + "毫秒");
        for (InetAddress address : addresses1)
            System.out.println(address);
        System.out.print("按任意键继续");
        System.in.read();
        time = System.currentTimeMillis();
        InetAddress addresses2[] = InetAddress.getAllByName(hostname);
        System.out.println("addresses2:   "
                + String.valueOf(System.currentTimeMillis() - time)
                + "毫秒");
        for (InetAddress address : addresses2)
            System.out.println(address);
    }
}
