package com.example.appproject;

import java.io.Serializable;

public class Person implements Serializable {

    String id;
    String pw;
    String name;

    public Person(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpw() {
        return pw;
    }

    public void setpw(String pw) {
        this.pw = pw;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

}
