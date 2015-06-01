/*
 * Copyright (c) www.ultrapower.com.cn
 */

package ch01;

/**
 * Created by liekkas on 15/5/7.
 */
public class MyFetcher implements Fetcher {

    final Data data;

    public MyFetcher(Data data) {
        this.data = data;
    }

    public void fetchData(FetcherCallback fetcherCallback) {

        try {
            fetcherCallback.onData(data);
        } catch (Exception e){
            fetcherCallback.onError(e);
        }
    }
}
