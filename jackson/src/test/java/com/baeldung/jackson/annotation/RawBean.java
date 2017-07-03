package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class RawBean {
    private String name;

    @JsonRawValue
    private String json;

    public RawBean() {

    }

    public RawBean(final String name, final String json) {
        this.name = name;
        this.json = json;
    }
}
