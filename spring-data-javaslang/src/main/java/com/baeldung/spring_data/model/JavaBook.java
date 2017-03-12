package com.baeldung.spring_data.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.List;

@Entity
@Table(name = "java_book")
public class JavaBook {
    
    @GeneratedValue
    @Id
    private Long id;
    
    private String title;
    
    @ElementCollection
    private List<String> authors;
    
    
    public void setAuthors(List<String> authors){
        this.authors = authors;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getTitle(){
        return this.title;
    }
   
    public Long getId(){
        return this.id;
    }
}

