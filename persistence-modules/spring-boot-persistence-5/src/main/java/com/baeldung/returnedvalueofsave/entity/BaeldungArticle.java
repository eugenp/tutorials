package com.baeldung.returnedvalueofsave.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "baeldung_articles")
public class BaeldungArticle {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private String author;

    @Transient
    private boolean alreadySaved = false;

    @PostPersist
    @PostUpdate
    private void markAsSaved() {
        this.alreadySaved = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAlreadySaved() {
        return alreadySaved;
    }

    public void setAlreadySaved(boolean alreadySaved) {
        this.alreadySaved = alreadySaved;
    }
}