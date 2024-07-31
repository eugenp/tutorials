package com.baeldung.spring.redis.configuration.entity;

import java.io.Serializable;

public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + "]";
    }

}
