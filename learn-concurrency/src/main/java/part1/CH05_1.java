/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 * 线程等待与唤醒
 * 在Object.java中，定义了wait(), notify()和notifyAll()等接口。wait()的作用是让当前线程进入等待状态，同时，wait()也会让当前线程释放它所持有的锁。而notify()和notifyAll()的作用，则是唤醒当前对象上的等待线程；notify()是唤醒单个线程，而notifyAll()是唤醒所有的线程。


 */
public class CH05_1 extends Thread{

    public CH05_1(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (this){
            System.out.println(Thread.currentThread().getName() + " is run");

            while (true) ;
//            try {
//                sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            //唤醒当前的wait线程
//            notify();
        }
    }
}
