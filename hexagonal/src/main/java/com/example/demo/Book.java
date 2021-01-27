package com.example.demo;

import java.io.Serializable;

public class Book implements Serializable{
    private static final long serialVersionUID = 1L;
    private String title;

    public Book() {
    }

    public String getTitle() {
        return title;
    }
    
    public String setTitle(String title) {
        return this.title = title;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + "]";
    }
}