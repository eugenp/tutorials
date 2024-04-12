package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

@JsonIncludeProperties({ "name" })
public class BeanWithInclude {
    public int id;
    public String name;

    public BeanWithInclude() {

    }

    public BeanWithInclude(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
}
