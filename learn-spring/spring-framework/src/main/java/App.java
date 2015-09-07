/*
 * Copyright (c) www.ultrapower.com.cn
 */

import beans.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by liekkas on 15/5/4.
 */
public class App {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");

        HelloWorld hw = (HelloWorld)context.getBean("helloBean");
        System.out.println(hw.toString());
    }
}
