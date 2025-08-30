package com.baeldung.webmagic;

public class Book {
    private final String title;
    private final String price;

    public Book(String title, String price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }
}

