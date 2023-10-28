package com.baeldung.encapsulation;

public class BookEncapsulation {
    public String author;
    public int isbn;
    public int id = 1;

    public BookEncapsulation(String author, int isbn) {
        this.author = author;
        this.isbn = isbn;
    }

    public String getBookDetails() {
        return "author id: " + id + " author name: " + author + " ISBN: " + isbn;
    }

}
