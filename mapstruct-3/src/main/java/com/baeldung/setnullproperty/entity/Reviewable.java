package com.baeldung.setnullproperty.entity;

import java.util.Objects;

public class Reviewable {
    protected String id;
    protected String reviewedBy;
    protected String title;

    public Reviewable(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public Reviewable() {
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reviewable that = (Reviewable) o;
        return Objects.equals(reviewedBy, that.reviewedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reviewedBy);
    }
}
