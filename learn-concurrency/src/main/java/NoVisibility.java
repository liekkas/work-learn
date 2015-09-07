/*
 * Copyright (c) www.ultrapower.com.cn
 */

/**
 * Created by liekkas on 15/4/28.
 */
public class NoVisibility {

    private static boolean ready = false;
    private static int number = 0;
    private static Object lock = new Object();

    private static class ReaderThread extends Thread{
        public void run(){
            synchronized (lock){
                while (!ready){
                    Thread.yield();
                }
                System.out.println(number);
            }
        }
    }

    public static void main(String[] args) {
        synchronized (lock){
            new ReaderThread().start();
            number = 43;
            ready = true;
        }

    }
}
