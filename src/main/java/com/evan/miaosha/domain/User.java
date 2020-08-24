package com.evan.miaosha.domain;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-07-31
 */
public class User {
    private int id;
    private String name;

    public User() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
