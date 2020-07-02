package com.baeldung.composite.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookId implements Serializable {

    private String author;
    private String name;

    public BookId() {
    }

    public BookId(String author, String name) {
        this.author = author;
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BookId bookId = (BookId) o;
        return Objects.equals(author, bookId.author) && Objects.equals(name, bookId.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, name);
    }
}
