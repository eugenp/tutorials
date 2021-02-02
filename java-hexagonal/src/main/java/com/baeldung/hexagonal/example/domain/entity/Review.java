package com.baeldung.hexagonal.example.domain.entity;

public class Review {
    private String author;
    private Rating rating;
    private String review;

    public enum Rating {
        ONE_STAR,
        TWO_STAR,
        THREE_STAR,
        FOUR_STAR,
        FIVE_STAR
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
