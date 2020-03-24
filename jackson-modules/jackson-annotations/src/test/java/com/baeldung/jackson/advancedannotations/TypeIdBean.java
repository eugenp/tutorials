package com.baeldung.jackson.advancedannotations;

import com.fasterxml.jackson.annotation.JsonTypeId;

public class TypeIdBean {
    private int id;
    @JsonTypeId
    private String name;

    public TypeIdBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

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