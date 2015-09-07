/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 * 当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，
 * 其他线程仍然可以访问“该对象”的非同步代码块。
 结果说明：
 主线程中新建了两个子线程t1和t2。t1会调用count对象的synMethod()方法，该方法内含有同步块；
 而t2则会调用count对象的nonSynMethod()方法，该方法不是同步方法。t1运行时，
 虽然调用synchronized(this)获取“count的同步锁”；但是并没有造成t2的阻塞，因为t2没有用到“count”同步锁。

 */
public class CH04_3 {

    public void synMethod(){
        synchronized (this){
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " syn loop " + i);
            }
        }
    }

    public void nosynMethod(){
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " nosyn loop " + i);
        }
    }
}
