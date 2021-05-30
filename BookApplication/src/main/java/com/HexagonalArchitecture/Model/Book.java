package com.HexagonalArchitecture.Model;

import javax.persistence.*;

@Entity
@Table(name = "Book")
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "author")
    private String author;
    
    public Book(){}
    
    public Book(long id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    
    
    
}