/*
 * Copyright (c) www.ultrapower.com.cn
 */

package eventbus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import eventbus.actor.EmitterActor;
import eventbus.actor.HandlerActor;
import eventbus.actor.HandlerActor2;
import eventbus.command.SimpleCommand;
import eventbus.event.SimpleEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liekkas on 15/5/6.
 * test
 */
public class App {

    public static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException {
        final ActorSystem actorSystem = ActorSystem.create("simple-actor-system");

        Thread.sleep(2000);

        final ActorRef emitter = actorSystem.actorOf(Props.create(EmitterActor.class),"emitter");
        final ActorRef handler = actorSystem.actorOf(Props.create(HandlerActor.class),"handler1");
        final ActorRef handler2 = actorSystem.actorOf(Props.create(HandlerActor2.class),"handler2");

        actorSystem.eventStream().subscribe(handler, SimpleEvent.class);
        actorSystem.eventStream().subscribe(handler2, SimpleEvent.class);

        for (int i = 0; i < 10; i++) {
            emitter.tell(new SimpleCommand("CMD " + i),null);
        }

        Thread.sleep(2000);

        log.info("Actor System Shutdown Starting...");
        actorSystem.shutdown();
    }

}
