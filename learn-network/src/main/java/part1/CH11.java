/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by liekkas on 15/4/29.
 * 使用NetworkInterface类获得网络接口信息
 */
public class CH11 {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        InetAddress address = InetAddress.getByName("localhost");
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(address);
        System.out.println(networkInterface);

        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        while (nis.hasMoreElements()) {
            NetworkInterface o =  nis.nextElement();
            System.out.println(o+ " "+ o.getDisplayName() );
        }
    }
}
