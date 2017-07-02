package com.baeldung.setterdi.domain;

public class Genre {
    private String category;
    private String rating;
    
    public void setCategory(String category) {
        this.category = category;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("%s %s", category, rating);
    }
}
