package com.baeldung.outside.driven.Repository.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


@Entity(name = "quotes")
public class QuoteDBModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String message;


    @Column(nullable = false)
    private String author;

    public QuoteDBModel(String message, String author) {
        this.message = message;
        this.author = author;
    }

    protected QuoteDBModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "QuoteDBModel{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}