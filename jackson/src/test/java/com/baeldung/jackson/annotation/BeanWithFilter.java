package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("myFilter")
public class BeanWithFilter {
    private int id;
    private String name;

    public BeanWithFilter() {

    }

    public BeanWithFilter(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
}
