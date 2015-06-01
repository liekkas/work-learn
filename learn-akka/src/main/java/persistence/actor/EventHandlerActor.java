/*
 * Copyright (c) www.ultrapower.com.cn
 */

package persistence.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by liekkas on 15/5/6.
 */
public class EventHandlerActor extends UntypedActor{

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("Handled Event:" + message);
    }
}
