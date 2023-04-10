package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonValue;

public class GeneralBean {
    Integer id;

    @JsonValue
    String name;

    public GeneralBean() {
    }

    public GeneralBean(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
