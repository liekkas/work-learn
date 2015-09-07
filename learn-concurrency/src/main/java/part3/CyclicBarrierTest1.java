/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part3;

/**
 * Created by liekkas on 15/5/4.
 * CyclicBarrier是一个同步辅助类，允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 * 因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 *
 * CyclicBarrier是包含了"ReentrantLock对象lock"和"Condition对象trip"，它是通过"独占锁"实现的。本身实质是个共享锁
 * 而CountDownLatch是通过共享锁来实现

    例子1：新建5个线程，这5个线程达到一定的条件时，它们才继续往后运行。
 */
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class CyclicBarrierTest1 {

    private static int SIZE = 5;
    private static CyclicBarrier cb;
    public static void main(String[] args) {

        cb = new CyclicBarrier(SIZE);

        // 新建5个任务
        for(int i=0; i<SIZE; i++)
            new InnerThread().start();
    }

    static class InnerThread extends Thread{
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " wait for CyclicBarrier.");

                // 将cb的参与者数量加1
                cb.await();

                // cb的参与者数量等于5时，才继续往后执行
                System.out.println(Thread.currentThread().getName() + " continued.");
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}