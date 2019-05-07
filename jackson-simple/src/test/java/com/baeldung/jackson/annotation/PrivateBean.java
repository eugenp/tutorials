package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class PrivateBean {
    private int id;
    private String name;

    public PrivateBean() {

    }

    public PrivateBean(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
}
