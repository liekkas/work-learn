/*
 * Copyright (c) www.ultrapower.com.cn
 */

package learn1;

import akka.actor.UntypedActor;

/**
 * Created by liekkas on 15/5/6.
 */
public class HelloWorld extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Greeter.MSG.DONE){
            System.out.println(">>> DONE!");
            getContext().stop(getSelf());
        } else {
            unhandled(message);
        }
    }
}
