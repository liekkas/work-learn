/*
 * Copyright (c) www.ultrapower.com.cn
 */

package learn1;

import akka.actor.UntypedActor;

/**
 * Created by liekkas on 15/5/6.
 */
public class Greeter extends UntypedActor {
    public static enum MSG{
        GREET,
        DONE
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == MSG.GREET){
            System.out.println(">>> Hello World!");
            getSender().tell(MSG.DONE,getSelf());
        } else {
            unhandled(message);
        }
    }
}
