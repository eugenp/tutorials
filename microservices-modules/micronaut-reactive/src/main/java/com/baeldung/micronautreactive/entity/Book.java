package com.baeldung.micronautreactive.entity;

import io.micronaut.core.annotation.Generated;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.types.ObjectId;

@Serdeable
@MappedEntity
public class Book {
    @Id
    @Generated
    private @Nullable ObjectId id;
    private String title;

    private Author author;
    private int year;

    public Book() {
    }

    public Book(@Nullable ObjectId id, String title, Author author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(String title, Author author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public @Nullable ObjectId getId() {
        return id;
    }

    public void setId(@Nullable ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
