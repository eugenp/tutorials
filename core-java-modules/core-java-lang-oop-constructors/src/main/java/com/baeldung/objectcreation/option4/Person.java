package com.baeldung.objectcreation.option4;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private int uid;

    public Person(String name, int uid) {
        super();
        this.name = name;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}
