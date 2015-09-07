/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 * 线程休眠sleep
 * 我们知道，wait()的作用是让当前线程由“运行状态”进入“等待(阻塞)状态”的同时，也会释放同步锁。
 * 而sleep()的作用是也是让当前线程由“运行状态”进入到“休眠(阻塞)状态”。
 但是，wait()会释放对象的同步锁，而sleep()则不会释放锁。
 */
// SleepLockTest.java的源码
public class CH07{

    private static Object obj = new Object();

    public static void main(String[] args){
        ThreadA t1 = new ThreadA("t1");
        ThreadA t2 = new ThreadA("t2");
        t1.start();
        t2.start();
    }

    static class ThreadA extends Thread{
        public ThreadA(String name){
            super(name);
        }
        public void run(){
            // 获取obj对象的同步锁
            synchronized (obj) {
                try {
                    for(int i=0; i <10; i++){
                        System.out.printf("%s: %d\n", this.getName(), i);
                        // i能被4整除时，休眠100毫秒
                        if (i%4 == 0)
                            Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
