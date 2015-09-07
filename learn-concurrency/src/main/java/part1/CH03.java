/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 * Thread中start()和run()的区别
 */
public class CH03 extends Thread{

    public CH03(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+ " is running");
    }
}
