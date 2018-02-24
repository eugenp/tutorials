package org.baeldung.spring.di.demo;

public class Book {

    private String bookTitle;
    private Author author;
    private double price;

    public Book(String bookTitle, Author author, double price) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.price = price;
    }

    public void bookDetails() {
        System.out.println(bookTitle);
        author.authorDetails();
        System.out.println(price);
    }

}
