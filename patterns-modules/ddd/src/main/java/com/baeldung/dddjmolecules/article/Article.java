package com.baeldung.dddjmolecules.article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import com.baeldung.dddjmolecules.author.Username;

@AggregateRoot
public class Article {
    @Identity
    private Slug slug;
    private Username author;
    private String title;
    private String content;
    private Status status;
    private List<Comment> comments;
    private List<Username> likedBy;

    enum Status {
        DRAFT, PUBLISHED, HIDDEN, ARCHIVED
    }

    public Article(Username author, String content, String title) {
        this.status = Status.DRAFT;
        this.author = author;
        this.title = title;
        this.content = content;
        this.slug = new Slug(title.toLowerCase()
            .replaceAll(" ", "-"));
        this.comments = new ArrayList<>();
        this.likedBy = new ArrayList<>();
    }

    void publish() {
        if (status == Status.DRAFT || status == Status.HIDDEN) {
            status = Status.PUBLISHED;
        }
        throw new IllegalStateException("we cannot publish an article with status=" + status);
    }

    void hide() {
        if (status == Status.PUBLISHED) {
            status = Status.HIDDEN;
        }
        throw new IllegalStateException("we cannot hide an article with status=" + status);
    }

    void archive() {
        if (status != Status.ARCHIVED) {
            status = Status.ARCHIVED;
        }
        throw new IllegalStateException("the article is already archived");
    }

    void comment(Username user, String message) {
        comments.add(new Comment(user, message));
    }

    void like(Username user) {
        likedBy.add(user);
    }

    void dislike(Username user) {
        likedBy.remove(user);
    }

    public Slug getSlug() {
        return slug;
    }

    public Username getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Status getStatus() {
        return status;
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public List<Username> getLikedBy() {
        return Collections.unmodifiableList(likedBy);
    }
}
