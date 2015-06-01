/*
 * Copyright (c) www.ultrapower.com.cn
 */

package parentchild.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import parentchild.command.SimpleCommand;
import parentchild.event.SimpleEvent;

import java.util.UUID;

/**
 * Created by liekkas on 15/5/6.
 */
public class ParentActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(),this);

    private final ActorRef childActor;

    public ParentActor() {
        this.childActor = getContext().actorOf(Props.create(ChildActor.class),"child-actor");
    }

    @Override
    public void onReceive(Object message) throws Exception {

        log.info("Received Command:" + message);
        if (message instanceof SimpleCommand){
            final String data = ((SimpleCommand) message).getData();
            final SimpleEvent event = new SimpleEvent(data, UUID.randomUUID().toString());

            //emit to child
            childActor.tell(event,getSelf());
        } else if (message.equals("echo")) {
            log.info("ECHO!");
        }

    }
}
