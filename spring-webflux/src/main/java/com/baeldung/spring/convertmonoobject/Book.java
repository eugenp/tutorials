package com.baeldung.spring.convertmonoobject;

public class Book {

    private String bookId;
    private String title;
    private double price;
    private boolean available;

    public Book(String bookId, String title) {
        this.bookId = bookId;
        this.title = title;
    }

    public Book(String bookId, String title, int price, boolean available) {
        this.bookId = bookId;
        this.title = title;
        this.price = price;
        this.available = available;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
