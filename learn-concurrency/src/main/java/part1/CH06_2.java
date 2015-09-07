/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 * 我们知道，wait()的作用是让当前线程由“运行状态”进入“等待(阻塞)状态”的同时，也会释放同步锁。而yield()的作用是让步，
 * 它也会让当前线程离开“运行状态”。它们的区别是：
 (01) wait()是让线程由“运行状态”进入到“等待(阻塞)状态”，而不yield()是让线程由“运行状态”进入到“就绪状态”。
 (02) wait()是会线程释放它所持有对象的同步锁，而yield()方法不会释放锁。
 *
 *主线程main中启动了两个线程t1和t2。t1和t2在run()会引用同一个对象的同步锁，即synchronized(obj)。在t1运行过程中，虽然它会调用Thread.yield()；
 * 但是，t2是不会获取cpu执行权的。因为，t1并没有释放“obj所持有的同步锁”！
 */
public class CH06_2 extends Thread{

    private static Object lockj = new Object();

    public void run(){
        synchronized (lockj){
            for(int i=0; i <10; i++){
                System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i);
                // i整除4时，调用yield
                if (i%4 == 0)
                    Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        new CH06_2().start();
        new CH06_2().start();
    }
}
