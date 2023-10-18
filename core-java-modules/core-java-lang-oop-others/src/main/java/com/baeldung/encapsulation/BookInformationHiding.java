package com.baeldung.encapsulation;

public class BookInformationHiding {
    private String author;
    private int isbn;
    private int id = 1;

    public BookInformationHiding(String author, int isbn) {
        setAuthor(author);
        setIsbn(isbn);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        if (isbn < 0) {
            throw new IllegalArgumentException("ISBN can't be negative");
        }
        this.isbn = isbn;
    }

    public String getBookDetails() {
        return "author id: " + id + " author name: " + author + " ISBN: " + isbn;
    }

}
