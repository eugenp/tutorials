package com.baeldung.hexagonalarchitecture.model;

public class MovieSearchRequest {
    private String movieName;

    public MovieSearchRequest(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieName() {
        return movieName;
    }
}
