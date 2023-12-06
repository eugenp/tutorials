package com.baeldung.mismatchedinputexception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Animals {

    private final int id;
    private String name;


    public Animals(@JsonProperty("id") int id, @JsonProperty("name")String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
