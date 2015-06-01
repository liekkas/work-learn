/*
 * Copyright (c) www.ultrapower.com.cn
 */

package parentchild;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parentchild.actor.ParentActor;
import parentchild.command.SimpleCommand;

/**
 * Created by liekkas on 15/5/6.
 * test
 */
public class App {

    public static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException {
        final ActorSystem actorSystem = ActorSystem.create("simple-actor-system");

        Thread.sleep(2000);

        final ActorRef actorRef = actorSystem.actorOf(Props.create(ParentActor.class),"parent-actor");

        actorRef.tell(new SimpleCommand("CMD 1"),null);
        actorRef.tell(new SimpleCommand("CMD 2"),null);
        actorRef.tell(new SimpleCommand("CMD 3"),null);
        actorRef.tell("echo",null);
        actorRef.tell("hahaha",null);
        actorRef.tell(new SimpleCommand("CMD 4"),null);
        actorRef.tell(new SimpleCommand("CMD 5"),null);

        Thread.sleep(2000);

        log.info("Actor System Shutdown Starting...");
        actorSystem.shutdown();
    }

}
