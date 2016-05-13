package com.baeldung.spring.data.neo4j.model;

import org.neo4j.ogm.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@NodeEntity
public class Person {

    private static final AtomicLong TS = new AtomicLong();

    @GraphId
    private Long id;
    private String name;
    private int born;

    @Relationship(type = "AUTHORED_BY")
    private List<Book> books;

    public Person() {
        this.id = TS.incrementAndGet();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBorn() {
        return born;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
