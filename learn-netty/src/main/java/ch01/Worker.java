/*
 * Copyright (c) www.ultrapower.com.cn
 */

package ch01;

/**
 * Created by liekkas on 15/5/7.
 */
public class Worker {

    public void doWork(){
        Fetcher fetcher = new MyFetcher(new Data(1,0));
        fetcher.fetchData(new FetcherCallback() {
            public void onData(Data data) throws Exception {
                System.out.println("Data Received:"+data);
            }

            public void onError(Throwable cause) {
                System.out.println("An error accor:"+cause.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        new Worker().doWork();
    }
}
