package com.baeldung.spring.insertableonly.unpadatable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UnapdatableBook {

    @Id
    @GeneratedValue
    private Long id;
    @Column(updatable = false)
    private String title;

    private String author;

    public UnapdatableBook() {
    }

    public UnapdatableBook(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
