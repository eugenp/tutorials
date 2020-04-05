package com.baeldung.streamreduce.entities;

public class Review {

    int points;
    String review;

    public Review(int points, String review) {
        this.points = points;
        this.review = review;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
