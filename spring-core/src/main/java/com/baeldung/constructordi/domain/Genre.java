package com.baeldung.constructordi.domain;

public class Genre {
    private String category;
    private String rating;
    
    public Genre(String category, String rating) {
        this.category = category;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("%s %s", category, rating);
    }
}
