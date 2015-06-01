/*
 * Copyright (c) www.ultrapower.com.cn
 */

package eventbus.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import eventbus.command.SimpleCommand;
import eventbus.event.SimpleEvent;

import java.util.UUID;

/**
 * Created by liekkas on 15/5/6.
 * 接受命令并发射事件，侦听该事件的都能收到消息。解耦合
 */
public class EmitterActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(),this);

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof SimpleCommand){
            final String data = ((SimpleCommand) message).getData();
            final SimpleEvent event = new SimpleEvent(data, UUID.randomUUID().toString());

            log.info(">>> Emitting Event:"+message);
            getContext().system().eventStream().publish(event);
        } else if (message.equals("echo")) {
            log.info("ECHO!");
        }

    }
}
