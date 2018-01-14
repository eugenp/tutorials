package com.baeldung.di.autowire.example.store;

import org.springframework.stereotype.Component;

@Component("book")
public class Book implements Item {

    private String title = "History of Java";
    private String type  = "Book";
    @Override
    public String getTitle() {

        return title;
    }

    @Override
    public String getType() {
        return type;
    }

}
