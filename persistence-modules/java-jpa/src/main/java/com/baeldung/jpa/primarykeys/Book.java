package com.baeldung.jpa.primarykeys;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Book {

    @EmbeddedId
    private BookId bookId;

    private String description;

    public Book() {

    }

    public Book(BookId bookId) {
        this.bookId = bookId;
    }

    public BookId getBookId() {
        return bookId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
