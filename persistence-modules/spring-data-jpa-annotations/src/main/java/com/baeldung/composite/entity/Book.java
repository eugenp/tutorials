package com.baeldung.composite.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Book {

    @EmbeddedId
    private BookId id;
    private String genre;
    private Integer price;

    public Book() {
    }

    public Book(String author, String name, String genre, Integer price) {
        BookId id = new BookId(author, name);
        this.id = id;
        this.genre = genre;
        this.price = price;
    }

    public BookId getId() {
        return id;
    }

    public void setId(BookId id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
