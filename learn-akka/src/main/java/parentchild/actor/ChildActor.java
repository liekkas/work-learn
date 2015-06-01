/*
 * Copyright (c) www.ultrapower.com.cn
 */

package parentchild.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by liekkas on 15/5/6.
 *
 * 从父Actor那里获取事件，并把消息打印出来
 */
public class ChildActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart(){
        log.info("Child Actor Starting");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("老爸老爸，我收到你的信息啦：" + message);
    }
}
