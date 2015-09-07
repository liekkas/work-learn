/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part5;

/**
 * Created by liekkas on 15/5/5.
 * 结果说明：
 主线程中创建了线程池pool，线程池的容量是2。即，线程池中最多能同时运行2个线程。
 紧接着，将ta,tb,tc,td,te这5个线程添加到线程池中运行。
 最后，通过shutdown()关闭线程池。
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ThreadPoolTest1 {

    public static void main(String[] args) {

        // 创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread ta = new MyThread();
        Thread tb = new MyThread();
        Thread tc = new MyThread();
        Thread td = new MyThread();
        Thread te = new MyThread();
        // 将线程放入池中进行执行
        pool.execute(ta);
        pool.execute(tb);
        pool.execute(tc);
        pool.execute(td);
        pool.execute(te);
        // 关闭线程池
        pool.shutdown();
    }
}

class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+ " is running.");
    }
}