package com.baeldung.domain;

import java.io.Serializable;

public class Book implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String author;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    
}
