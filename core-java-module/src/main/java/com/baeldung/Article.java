package com.baeldung;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Article implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private String content;

    // Constructors
    public Article() {
    }

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Clone method for shallow copy
    @Override
    public Article clone() {
        try {
            return (Article) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }

    public static Article createDeepCopy(Article article) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(article), Article.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String toString() {
        return "Article{" + "title='" + title + '\'' + ", content='" + content + '\'' + '}';
    }
}



