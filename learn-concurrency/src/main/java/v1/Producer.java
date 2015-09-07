/*
 * Copyright (c) www.ultrapower.com.cn
 */

package pc.v1;

/**
 * Created by liekkas on 15/5/4.
 */
public class Producer {

    private Depot depot;

    public Producer(Depot depot) {
        this.depot = depot;
    }

    //生产产品，新建一个线程向仓库生产产品
    public void produce(final int value){
        new Thread(){
            public void run(){
                depot.produce(value);
            }
        }.start();
    }
}
