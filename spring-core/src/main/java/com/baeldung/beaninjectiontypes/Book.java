package com.baeldung.beaninjectiontypes;

import org.springframework.stereotype.Component;

@Component("book")
public class Book {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
