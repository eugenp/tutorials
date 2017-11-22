package com.baeldung.domain;

import org.springframework.stereotype.Component;

@Component
public class Employee {

    private int id = 1;
    private String name = "Robert Langdon";

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
