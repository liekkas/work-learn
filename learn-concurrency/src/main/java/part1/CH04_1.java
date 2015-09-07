/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 * synchronized关键字
 * 我们将synchronized的基本规则总结为下面3条，并通过实例对它们进行说明。
 第一条: 当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，
    其他线程对“该对象”的该“synchronized方法”或者“synchronized代码块”的访问将被阻塞。
 第二条: 当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，
    其他线程仍然可以访问“该对象”的非同步代码块。
 第三条: 当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，
    其他线程对“该对象”的其他的“synchronized方法”或者“synchronized代码块”的访问将被阻塞。
 */
public class CH04_1 implements Runnable{

    public void run() {
        synchronized (this){
//            try{
                for (int i = 0; i < 5; i++) {
//                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " loop " + i);
                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }
    }
}
