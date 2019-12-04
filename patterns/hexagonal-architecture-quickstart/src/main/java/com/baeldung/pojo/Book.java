package com.baeldung.pojo;

import lombok.Data;

@Data
public class Book {

    private String bookNo;

    private String title;

    private String author;

    public Book(String bookNo, String title, String author) {
        super();
        this.bookNo = bookNo;
        this.title = title;
        this.author = author;
    }

}
