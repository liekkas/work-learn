/*
 * Copyright (c) www.ultrapower.com.cn
 */

package pc.v1;

/**
 * Created by liekkas on 15/5/4.
 */
public class Consumer {

    private Depot depot;

    public Consumer(Depot depot) {
        this.depot = depot;
    }

    //消费产品，新建一个线程从仓库中消费产品
    public void consume(final int value){
        new Thread(){
            public void run() {
                depot.consume(value);
            }
        }.start();
    }
}
