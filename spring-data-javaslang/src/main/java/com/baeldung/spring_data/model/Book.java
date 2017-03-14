package com.baeldung.spring_data.model;

import javaslang.collection.Seq;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@Table(name = "book")
public class Book {
    
    @GeneratedValue
    @Id
    private Long id;
    
    private String title;

    private Seq<String> authors;
    
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getTitle(){
        return this.title;
    }
   
    public Long getId(){
        return this.id;
    }
    
    public void setAuthors(Seq authors){
        this.authors = authors;
    }
    
    public Seq getAuthors(){
        return this.authors;
    }
}
