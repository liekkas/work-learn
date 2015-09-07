/*
 * Copyright (c) www.ultrapower.com.cn
 */

package beans;

/**
 * Created by liekkas on 15/5/4.
 */
public class HelloWorld {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HelloWorld{" +
                "name='" + name + '\'' +
                '}';
    }
}
