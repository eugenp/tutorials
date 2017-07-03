package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "id" })
public class BeanWithIgnore {
    @JsonIgnore
    private int id;
    private String name;

    public BeanWithIgnore() {

    }

    public BeanWithIgnore(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
}
