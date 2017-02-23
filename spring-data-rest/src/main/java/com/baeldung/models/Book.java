package com.baeldung.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library bookLibrary;
    
    @ManyToMany(mappedBy = "authorBooks")
    private List<Author> bookAuthors;
   
    public Book(){}

    public Book(String title) {
        super();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Library getBookLibrary() {
        return bookLibrary;
    }

    public void setBookLibrary(Library bookLibrary) {
        this.bookLibrary = bookLibrary;
    }

    public List<Author> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<Author> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }
}
