/*
 * Copyright (c) www.ultrapower.com.cn
 */

package simple.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import simple.command.SimpleCommand;
import simple.event.SimpleEvent;

import java.util.UUID;

/**
 * Created by liekkas on 15/5/6.
 */
public class SimpleActor extends UntypedActor{

    LoggingAdapter log = Logging.getLogger(getContext().system(),this);

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("Received Command: " + message);

        if (message instanceof SimpleCommand){
            final String data = ((SimpleCommand) message).getData();
            final SimpleEvent event = new SimpleEvent(data, UUID.randomUUID().toString());

            //emmit an event somewhere...
            log.info(">>> will be emmit");
        } else if(message.equals("echo")){
            log.info("ECHO!");
        } else {
            unhandled(message);
        }
    }
}
