package com.baeldung.eventuate.tram.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String articleSlug;
    private String commentAuthor;

    public Comment(String text, String articleSlug, String commentAuthor) {
        this.text = text;
        this.articleSlug = articleSlug;
        this.commentAuthor = commentAuthor;
    }

    Comment() {
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getArticleSlug() {
        return articleSlug;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }
}
