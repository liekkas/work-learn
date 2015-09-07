/*
 * Copyright (c) www.ultrapower.com.cn
 */

package fastjson;

import com.alibaba.fastjson.JSON;
import vo.Group;
import vo.Person;

import java.util.List;

/**
 * Created by liekkas on 15/4/24.
 */
public class TestFastjson {

    public static void main(String[] args) {

        Group group = new Group();
        Person users = new Person();
        Person users2 = new Person();
        Person users3 = new Person();

        users.setId(1L);
        users.setName("ylchou");

        users2.setId(2L);
        users2.setName("chousoul");

        users3.setId(3L);
        users3.setName("shayue");

        group.setId(7L);
        group.setName("gongsi");
        group.getUsers().add(users);//注意
        group.getUsers().add(users2);//注意
        group.getUsers().add(users3);
//		group.getUsers().add(users3);//重复不会得到想要的

        String groupJsonString = JSON.toJSONString(group);
        System.out.println("encode:"+groupJsonString);

        Group classGroup = JSON.parseObject(groupJsonString, Group.class);
        System.out.println("discode:"+classGroup);

        Object classGroup2 = JSON.parseObject(groupJsonString, Group.class);
        System.out.println("discode:"+classGroup2);

        //得到的仍然是JSON，所有parseObject中参数要为转化后的类.class，不能为Object.class。
        Object classGroup3 = JSON.parseObject(groupJsonString, Object.class);
        System.out.println("discode:"+classGroup3);

    }
}
