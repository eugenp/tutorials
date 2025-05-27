package com.baeldung.spring.modulith.events.externalization;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String slug;
    private String title;
    private String author;
    private String content;

    public Article(String slug, String title, String author, String content) {
        this.slug = slug;
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public Article() {
    }

    public String author() {
        return author;
    }

    public String content() {
        return content;
    }

    public String slug() {
        return slug;
    }

    public String title() {
        return title;
    }
}