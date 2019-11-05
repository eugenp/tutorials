package com.baeldung.hexagonalarchitecture.hexagon.domain;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Article")
@Table(name = "ARTICLE")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "CREATION_TIME")
    private Date creationTime;

    @Column(name = "IS_PUBLISHABLE")
    private boolean isPublishable;


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isPublishable() {
        return isPublishable;
    }

    public void setPublishable(boolean publishable) {
        isPublishable = publishable;
    }
}
