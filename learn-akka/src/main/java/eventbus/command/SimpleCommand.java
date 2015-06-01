/*
 * Copyright (c) www.ultrapower.com.cn
 */

package eventbus.command;

import java.io.Serializable;

/**
 * Created by liekkas on 15/5/6.
 */
public class SimpleCommand implements Serializable {

    private final String data;

    public String getData() {
        return data;
    }

    public SimpleCommand(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SimpleCommand{" +
                "data='" + data + '\'' +
                '}';
    }
}
