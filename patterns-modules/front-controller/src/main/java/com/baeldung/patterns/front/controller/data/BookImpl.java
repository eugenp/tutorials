package com.baeldung.patterns.front.controller.data;

public class BookImpl implements Book {
    private String author;
    private String title;
    private Double price;

    public BookImpl() {
    }

    public BookImpl(String author, String title, Double price) {
        this.author = author;
        this.title = title;
        this.price = price;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public void setPrice(Double price) {
        this.price = price;
    }
}
