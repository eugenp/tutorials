package com.baeldung.ditypes.domain;

import org.springframework.stereotype.Component;

@Component
public class Book {

    private String name;
    private Category category;

    public Book() {
    }

    public Book(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book [name=" + name + ", category=" + category + "]";
    }
}
