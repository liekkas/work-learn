/*
 * Copyright (c) www.ultrapower.com.cn
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liekkas on 15/4/28.
 */
public class Count {
    
    public static AtomicInteger count = new AtomicInteger(0);
    
    public static void inc() {

        count.addAndGet(1);
    }
    
    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(Integer.MAX_VALUE);

        //同时启动1000个线程，去进行i++计算，看看实际结果
        
        for (int i = 0; i < 1000; i++) {
            service.execute(new Runnable() {
                public void run() {
                    inc();
                }
            });
        }
        service.shutdown();

        service.awaitTermination(300, TimeUnit.SECONDS);

        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + Count.count.get());

    }
}
