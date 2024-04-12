package com.baeldung.jackson.advancedannotations;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class PropertyDescriptionBean {
    private int id;
    @JsonPropertyDescription("This is a description of the name property")
    private String name;

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