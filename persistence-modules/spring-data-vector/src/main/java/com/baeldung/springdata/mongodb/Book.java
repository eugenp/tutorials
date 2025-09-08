package com.baeldung.springdata.mongodb;


import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Vector;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String name;

    private String yearPublished;

    private Vector embedding;

    public Book() {}

    public Book(String id, String name, String yearPublished, Vector theEmbedding) {
        this.id = id;
        this.name = name;
        this.yearPublished = yearPublished;
        this.embedding = theEmbedding;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(String yearPublished) {
        this.yearPublished = yearPublished;
    }

    public Vector getEmbedding() {
        return embedding;
    }

    public void setEmbedding(Vector embedding) {
        this.embedding = embedding;
    }
}

