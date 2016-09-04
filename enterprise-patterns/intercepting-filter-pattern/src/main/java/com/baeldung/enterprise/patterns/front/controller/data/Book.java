package com.baeldung.enterprise.patterns.front.controller.data;

public interface Book {
    String getIsbn();

    void setIsbn(String isbn);

    String getAuthor();

    void setAuthor(String author);

    String getTitle();

    void setTitle(String title);

    Double getPrice();

    void setPrice(Double price);
}
