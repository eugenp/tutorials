package com.baeldung.spring.data.persistence.saveperformance;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String author;

    public Book(final String title, final String author) {
        this.title = title;
        this.author = author;
    }

    public Book() {
    }
}
