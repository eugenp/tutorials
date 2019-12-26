package com.baeldung.jnosql.diana.key;

import java.io.Serializable;

public class Book implements Serializable {

    private String isbn;
    private String name;
    private String author;
    private int pages;

    public Book() {
    }

    public Book(String isbn, String name, String author, int pages) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                '}';
    }
}
