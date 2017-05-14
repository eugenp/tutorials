package com.baeldung.di.setter.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Author {

    private String name;

    @Autowired
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author [name=" + name + "]";
    }

}
