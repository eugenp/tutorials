package com.baeldung.di.types.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Author {
    private String name;

    public String getName() {
        return name;
    }

    @Autowired
    public void setName(@Value("Java Champion") String name) {
        this.name = name;
    }
}
