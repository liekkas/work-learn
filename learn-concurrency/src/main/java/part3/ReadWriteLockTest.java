/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part3;

/**
 * Created by liekkas on 15/5/4.
 * ReadWriteLock，顾名思义，是读写锁。它维护了一对相关的锁 — — “读取锁”和“写入锁”，
 * 一个用于读取操作，另一个用于写入操作。
    “读取锁”用于只读操作，它是“共享锁”，能同时被多个线程获取。
    “写入锁”用于写入操作，它是“独占锁”，写入锁只能被一个线程锁获取。
 注意：不能同时存在读取锁和写入锁！
 ReadWriteLock是一个接口。ReentrantReadWriteLock是它的实现类，ReentrantReadWriteLock包括子类ReadLock和WriteLock。
 *
 * 结果说明：
 (01) 观察Thread0和Thread-2的运行结果，我们发现，Thread-0启动并获取到“读取锁”，在它还没运行完毕的时候，
 Thread-2也启动了并且也成功获取到“读取锁”。
 因此，“读取锁”支持被多个线程同时获取。
 (02) 观察Thread-1,Thread-3,Thread-5这三个“写入锁”的线程。只要“写入锁”被某线程获取，则该线程运行完毕了，才释放该锁。
 因此，“写入锁”不支持被多个线程同时获取。
 */
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    public static void main(String[] args) {
        // 创建账户
        MyCount myCount = new MyCount("4238920615242830", 10000);
        // 创建用户，并指定账户
        User user = new User("Tommy", myCount);

        // 分别启动3个“读取账户金钱”的线程 和 3个“设置账户金钱”的线程
        for (int i=0; i<3; i++) {
            user.getCash();
            user.setCash((i+1)*1000);
        }
    }
}

class User {
    private String name;            //用户名
    private MyCount myCount;        //所要操作的账户
    private ReadWriteLock myLock;   //执行操作所需的锁对象

    User(String name, MyCount myCount) {
        this.name = name;
        this.myCount = myCount;
        this.myLock = new ReentrantReadWriteLock();
    }

    public void getCash() {
        new Thread() {
            public void run() {
                myLock.readLock().lock();
                try {
                    System.out.println(Thread.currentThread().getName() +" getCash start");
                    myCount.getCash();
                    Thread.sleep(1);
                    System.out.println(Thread.currentThread().getName() +" getCash end");
                } catch (InterruptedException e) {
                } finally {
                    myLock.readLock().unlock();
                }
            }
        }.start();
    }

    public void setCash(final int cash) {
        new Thread() {
            public void run() {
                myLock.writeLock().lock();
                try {
                    System.out.println(Thread.currentThread().getName() +" setCash start");
                    myCount.setCash(cash);
                    Thread.sleep(1);
                    System.out.println(Thread.currentThread().getName() +" setCash end");
                } catch (InterruptedException e) {
                } finally {
                    myLock.writeLock().unlock();
                }
            }
        }.start();
    }
}

class MyCount {
    private String id;         //账号
    private int    cash;       //账户余额

    MyCount(String id, int cash) {
        this.id = id;
        this.cash = cash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCash() {
        System.out.println(Thread.currentThread().getName() +" getCash cash="+ cash);
        return cash;
    }

    public void setCash(int cash) {
        System.out.println(Thread.currentThread().getName() +" setCash cash="+ cash);
        this.cash = cash;
    }
}
