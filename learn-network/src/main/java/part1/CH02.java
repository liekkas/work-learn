/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by liekkas on 15/4/29.
 * 创建InetAddress对象的四个静态方法
 * http://androidguy.blog.51cto.com/974126/214818
 * http://androidguy.blog.51cto.com/974126/214809
 */
public class CH02 {

    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("本机地址:" + address);

        InetAddress address1 = InetAddress.getByName("lab");
        System.out.println(address1);
        //getCanonicalHostName是主机名，getHostAddress是主机别名
        System.out.println("address1.getHostName():"+address1.getHostName()+
                " address1.getHostAddress()"+address1.getHostAddress()+" address1.getCanonicalHostName()"+address1.getCanonicalHostName());

        InetAddress[] address2 = InetAddress.getAllByName("localhost");
        for (InetAddress  inetAddress: address2) {
            System.out.println(inetAddress);
        }

        //感觉毫无用处啊，要知道Ip了还用这个做什么
        byte[] ip = new byte[] { (byte) 141, (byte) 146, 8 , 66};
        InetAddress address3 = InetAddress.getByAddress(ip);
        InetAddress address4 = InetAddress.getByAddress("Oracle官方网站", ip);
        System.out.println(address3);
        System.out.println(address4);

    }
}
