package com.baeldung.architecture.hexagonal.sprigboot.side.server;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BookEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String author;

    public BookEntity() {
    }

    public BookEntity(Long id, String author, String name) {
        super();
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
