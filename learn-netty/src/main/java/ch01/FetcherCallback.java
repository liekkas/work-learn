/*
 * Copyright (c) www.ultrapower.com.cn
 */

package ch01;

/**
 * Created by liekkas on 15/5/7.
 */
public interface FetcherCallback {

    void onData(Data data) throws Exception;

    void onError(Throwable cause);

}
