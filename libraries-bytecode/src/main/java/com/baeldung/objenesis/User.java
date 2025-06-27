package com.baeldung.objenesis;

import java.io.Serializable;

public class User implements Serializable {

    private String name;

    public User() {
        throw new RuntimeException("User constructor should not be called!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

