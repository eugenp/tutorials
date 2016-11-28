package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BeanWithGetter {
    public int id;
    private String name;

    public BeanWithGetter() {

    }

    public BeanWithGetter(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("name")
    @JsonSetter("name")
    public void setTheName(final String name) {
        this.name = name;
    }

    @JsonProperty("name")
    @JsonGetter("name")
    public String getTheName() {
        return name;
    }
}
