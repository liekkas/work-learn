/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part3;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liekkas on 15/5/4.
 * http://www.cnblogs.com/skywang12345/p/3496716.html
 *
 * Condition除了支持上面的功能之外，它更强大的地方在于：能够更加精细的控制多线程的休眠与唤醒。
 * 对于同一个锁，我们可以创建多个Condition，在不同的情况下使用不同的Condition。
 例如，假如多线程读/写同一个缓冲区：当向缓冲区中写入数据之后，唤醒"读线程"；当从缓冲区读出数据之后，唤醒"写线程"；
 并且当缓冲区满的时候，"写线程"需要等待；当缓冲区为空时，"读线程"需要等待。
 如果采用Object类中的wait(), notify(), notifyAll()实现该缓冲区，当向缓冲区写入数据之后需要唤醒"读线程"时，
 不可能通过notify()或notifyAll()明确的指定唤醒"读线程"，而只能通过notifyAll唤醒所有线程(但是notifyAll无法
 区分唤醒的线程是读线程，还是写线程)。  但是，通过Condition，就能明确的指定唤醒读线程。

 结果说明：
 (01) BoundedBuffer 是容量为5的缓冲，缓冲中存储的是Object对象，支持多线程的读/写缓冲。多个线程操作
 “一个BoundedBuffer对象”时，它们通过互斥锁lock对缓冲区items进行互斥访问；
 而且同一个BoundedBuffer对象下的全部线程共用“notFull”和“notEmpty”这两个Condition。
 notFull用于控制写缓冲，notEmpty用于控制读缓冲。当缓冲已满的时候，调用put的线程会执行notFull.await()进行等待；
 当缓冲区不是满的状态时，就将对象添加到缓冲区并将缓冲区的容量count+1，最后，调用notEmpty.signal()缓冲notEmpty上
 的等待线程(调用notEmpty.await的线程)。 简言之，notFull控制“缓冲区的写入”，当往缓冲区写入数据之后会唤醒notEmpty上
 的等待线程。
 同理，notEmpty控制“缓冲区的读取”，当读取了缓冲区数据之后会唤醒notFull上的等待线程。
 (02) 在ConditionTest2的main函数中，启动10个“写线程”，向BoundedBuffer中不断的写数据(写入0-9)；同时，
 也启动10个“读线程”，从BoundedBuffer中不断的读数据。
 (03) 简单分析一下运行结果。

 1, p1线程向缓冲中写入1。    此时，缓冲区数据:   | 1 |   |   |   |   |
 2, p4线程向缓冲中写入4。    此时，缓冲区数据:   | 1 | 4 |   |   |   |
 3, p5线程向缓冲中写入5。    此时，缓冲区数据:   | 1 | 4 | 5 |   |   |
 4, p0线程向缓冲中写入0。    此时，缓冲区数据:   | 1 | 4 | 5 | 0 |   |
 5, p2线程向缓冲中写入2。    此时，缓冲区数据:   | 1 | 4 | 5 | 0 | 2 |
 此时，缓冲区容量为5；缓冲区已满！如果此时，还有“写线程”想往缓冲中写入数据，会调用put中的notFull.await()等待，
 直接缓冲区非满状态，才能继续运行。
 6, t0线程从缓冲中取出数据1。此时，缓冲区数据:   |   | 4 | 5 | 0 | 2 |
 7, p3线程向缓冲中写入3。    此时，缓冲区数据:   | 3 | 4 | 5 | 0 | 2 |
 8, t1线程从缓冲中取出数据4。此时，缓冲区数据:   | 3 |   | 5 | 0 | 2 |
 9, p6线程向缓冲中写入6。    此时，缓冲区数据:   | 3 | 6 | 5 | 0 | 2 |
 ...
 */
public class ConditionTest2 {

    static Buffer buffer = new Buffer();

    public static void main(String[] args) {
        // 启动10个“写线程”，向BoundedBuffer中不断的写数据(写入0-9)；
        // 启动10个“读线程”，从BoundedBuffer中不断的读数据。
        for (int i=0; i<10; i++) {
            new PutThread("p"+i, i).start();
            new TakeThread("t"+i).start();
        }
    }

    static class PutThread extends Thread{
        private int num;

        public PutThread(String name, int num) {
            super(name);
            this.num = num;
        }

        public void run(){
            try{
                Thread.sleep(1);
                buffer.put(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class TakeThread extends Thread{
        public TakeThread(String name) {
            super(name);
        }

        public void run(){
            try{
                Thread.sleep(1);
                Object o = buffer.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class Buffer{
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[5];
    int putptr,takeptr,count;

    public void put(Object o){
        lock.lock();
        try {
            //如果缓存已满，则等待，否则添加o到缓存中
            while (count == items.length){
                notFull.await();
            }

            items[putptr] = o;

            if(++putptr == items.length){
                putptr = 0;
            }

            count += 1;

            //唤醒take线程，因为take线程是通过notEmpty.await()等待的
            notEmpty.signal();

            System.out.println(Thread.currentThread().getName() + " put " + o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object take(){
        lock.lock();
        try {
            // 如果“缓冲为空”，则等待；直到“缓冲”不为空，才将x从缓冲中取出。
            while (count == 0)
                notEmpty.await();
            // 将x从缓冲中取出
            Object x = items[takeptr];
            // 将“take统计数takeptr+1”；如果“缓冲为空”，则设takeptr为0。
            if (++takeptr == items.length) takeptr = 0;
            // 将“缓冲”数量-1
            --count;
            // 唤醒put线程，因为put线程通过notFull.await()等待
            notFull.signal();

            // 打印取出的数据
            System.out.println(Thread.currentThread().getName() + " take "+ (Integer)x);

            return x;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();    // 释放锁
        }
        return null;
    }
}