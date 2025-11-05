package com.baeldung.socketchannel;

import java.io.Serializable;

class MyObject implements Serializable {

    private String name;
    private int age;

    public MyObject(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}