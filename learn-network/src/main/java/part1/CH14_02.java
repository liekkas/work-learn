/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by liekkas on 15/4/29.
 * 使用socket扫描哪些端口已打开，多线程/单线程方式
 */
public class CH14_02 extends Thread{

    private int minPort,maxPort;
    public CH14_02(int minPort,int maxPort){
        this.maxPort = maxPort;
        this.minPort = minPort;
    }

    public void run(){
       long b = System.currentTimeMillis();
        for (int i = minPort; i < maxPort; i++) {
            try {
                Socket s = new Socket("lab",i);
                System.out.println(this.getName()+"扫描的已打开的端口："+i);
                s.close();
            } catch (IOException e) {

            }
        }

        System.out.println(this.getName()+"完成用时："+(System.currentTimeMillis()-b));
    }

    public static void main(String[] args) {
        multthreads();
//        singlethread();
    }

    private static void multthreads(){
        int minPort = 1;
        int maxPort = 34567;
        int threadNum = 10;
        int portInc = ((maxPort-minPort+1)/threadNum)
                + (((maxPort-minPort+1)%threadNum)==0?0:1);

        CH14_02[] instances = new CH14_02[threadNum];
        for (int i = 0; i < threadNum; i++) {
            int begin = minPort+portInc*i;
            int end = minPort+portInc*i+portInc-1;
            instances[i] = new CH14_02(begin,end);
            System.out.println(instances[i].getName()+" 扫描的端口范围："+begin+" - "+end);
            instances[i].start();
        }
    }

    private static void singlethread(){
        CH14_02 ch14_02 = new CH14_02(1,10000);
        ch14_02.start();
    }

}
