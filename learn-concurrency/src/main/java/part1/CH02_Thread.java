/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part1;

/**
 * Created by liekkas on 15/4/30.
 * 02之 常用的实现多线程的两种方式
 * http://www.cnblogs.com/skywang12345/p/3479063.html#part01
 *
 * (01) CH02_Thread继承于Thread，它是自定义个线程。每个CH02_Thread都会卖出10张票。
   (02) 测试类中主线程main创建并启动3个CH02_Thread子线程。每个子线程都各自卖出了10张票
 */
public class CH02_Thread extends Thread{

    private int ticket = 10;

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (ticket > 0){
                System.out.println(this.getName() + " 卖票：" + ticket--);
            }
        }
    }
}
