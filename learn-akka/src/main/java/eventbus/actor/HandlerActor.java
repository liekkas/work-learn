/*
 * Copyright (c) www.ultrapower.com.cn
 */

package eventbus.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by liekkas on 15/5/6.
 *
 * 处理从eventstream中发射出来的事件
 */
public class HandlerActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("处理信息啦：" + message);
    }
}
