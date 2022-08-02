package com.baeldung.uuid.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book extends UuidIdentifiedEntity {    
    
    private String title;
    private String author;
            
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
