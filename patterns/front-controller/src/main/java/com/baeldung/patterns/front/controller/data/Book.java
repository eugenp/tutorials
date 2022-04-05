package com.baeldung.patterns.front.controller.data;

public interface Book {
    String getAuthor();

    void setAuthor(String author);

    String getTitle();

    void setTitle(String title);

    Double getPrice();

    void setPrice(Double price);
}
