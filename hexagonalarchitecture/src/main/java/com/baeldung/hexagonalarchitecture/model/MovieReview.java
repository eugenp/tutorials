package com.baeldung.hexagonalarchitecture.model;

public class MovieReview {
    private String userName;
    private double rating;
    private String review;

    public MovieReview(String userName, double rating, String review) {
        this.userName = userName;
        this.rating = rating;
        this.review = review;
    }

    public String getUserName() {
        return userName;
    }

    public double getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    @Override
    public String toString() {
        return "MovieReview{" +
                "userName='" + userName + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
