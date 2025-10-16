package com.baeldung.setnullproperty.entity;

import java.util.Objects;

public class Article extends Reviewable {

    private String title;

    public Article(String id, String reviewedBy) {
        this.id = id;
        this.reviewedBy = reviewedBy;
    }

    public Article() {
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
