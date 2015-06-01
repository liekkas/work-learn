/*
 * Copyright (c) www.ultrapower.com.cn
 */

package parentchild.event;

import java.io.Serializable;

/**
 * Created by liekkas on 15/5/6.
 */
public class SimpleEvent implements Serializable {

    private final String data;
    private final String uuid;

    public String getData() {
        return data;
    }

    public String getUuid() {
        return uuid;
    }

    public SimpleEvent(String data, String uuid) {
        this.data = data;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "SimpleEvent{" +
                "data='" + data + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
