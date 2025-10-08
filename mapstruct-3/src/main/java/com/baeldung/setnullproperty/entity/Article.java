package com.baeldung.setnullproperty.entity;

import java.util.Objects;

public class Article {

    private String id;
    private String reviewedBy;
    private String title;

    public Article(String id, String reviewedBy) {
        this.id = id;
        this.reviewedBy = reviewedBy;
    }

    public Article() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        return Objects.equals(id, article.id) && Objects.equals(reviewedBy, article.reviewedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reviewedBy);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
