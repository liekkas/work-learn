/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 * (01) 和上面“MyThread继承于Thread”不同；这里的MyThread实现了Thread接口。
 (02) 主线程main创建并启动3个子线程，而且这3个子线程都是基于“mt这个Runnable对象”而创建的。
   运行结果是这3个子线程一共卖出了10张票。这说明它们是共享了MyThread接口的。
 */
public class CH02_Runnable implements Runnable{
    private int ticket = 10;

    public void run() {
        for (int i = 0; i < 20; i++) {
            if (ticket > 0){
                System.out.println(Thread.currentThread().getName() + " 卖票：" + ticket--);
            }
        }
    }

    //方法加锁，变量ticketvolatile修饰
//    private volatile int ticket = 10;
//
//    public synchronized void run() {
//        for (int i = 0; i < 20; i++) {
//            if (ticket > 0){
//                System.out.println(Thread.currentThread().getName() + " 卖票：" + ticket--);
//            }
//        }
//    }
}
