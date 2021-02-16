package com.baeldung.spring.data.redis_ttl.entity;

import java.io.Serializable;

public class Book implements Serializable{
    private static final long serialVersionUID = 1L;
    private String title;
    private String language;

    public Book(String title, String language) {
        this.title = title;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }
    
    public String setTitle(String title) {
        return this.title = title;
    }
    
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String genere) {
        this.language = genere;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", genere=" + language + "]";
    }
}