package com.baeldung.mongoclient;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Article {

    @BsonId
    public ObjectId id;
    public String author;
    public String title;
    public String description;

    // getters and setters


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}