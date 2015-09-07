/*
 * Copyright (c) www.ultrapower.com.cn
 */

package vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liekkas on 15/4/24.
 */
public class Group {

    private Long id;
    private String name;
    private List<Person> users = new ArrayList<Person>();
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Person> getUsers() {
        return users;
    }
    public void setUsers(List<Person> users) {
        this.users = users;
    }


}

