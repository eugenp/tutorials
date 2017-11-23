package com.baeldung.domain;

import org.springframework.stereotype.Component;

@Component
public class Student {
    private int id = 1;
    private String name = "Albert Einstein";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
