package com.baeldung.boot.architecture.hexagonal.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    private Long id;

    private String title;

    public Book(String title) {
        this.title = title;
    }

    public Book() {

    }

    public String description() {
        return "Title: " + title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


}