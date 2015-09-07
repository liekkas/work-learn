/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 */
public class CH04_2 extends Thread {

    public CH04_2(String name) {
        super(name);
    }

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
