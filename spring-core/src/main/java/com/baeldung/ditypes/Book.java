package com.baeldung.ditypes;

public class Book {
    public String title;
    public String isbn;

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", isbn=" + isbn + "]";
    }

}
