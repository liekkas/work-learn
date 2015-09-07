/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 * 实例锁和全局锁
 *
 * 实例锁 -- 锁在某一个实例对象上。如果该类是单例，那么该锁也具有全局锁的概念。
 实例锁对应的就是synchronized关键字。
 全局锁 -- 该锁针对的是类，无论实例多少个对象，那么线程都共享该锁。
 全局锁对应的就是static synchronized（或者是锁在该类的class或者classloader对象上）。
 */
public class CH04_5 {

    public synchronized void isSynA(){
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " isSynA loop " + i);
        }
    }

    public synchronized void isSynB(){
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " isSynB loop " + i);
        }
    }
    public static synchronized void isSSynA(){
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " isSSynA loop " + i);
        }
    }

    public static synchronized void isSSynB(){
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " isSSynB loop " + i);
        }
    }
}
