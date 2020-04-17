package com.baeldung.hexagonal.domain;

public class MovieData {
    String title;
    float rating;

    public MovieData(String title, float rating) {
        this.title = title;
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return title + ":" + rating;
    }
}
