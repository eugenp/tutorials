package org.baeldung.hexa.domain;

import java.util.Objects;

public class Book {

    private String name;

    private String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Book)) {
            return false;
        }

        Book other = (Book) obj;

        return Objects.equals(name, other.getName()) && Objects.equals(author, other.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author);
    }

}
