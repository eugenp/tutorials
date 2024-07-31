package com.baeldung.mockprotected;

public class Movies {

    private final String title;

    public Movies(String title) {
        this.title = title;
    }

    public String getPlaceHolder() {
        return "Movie: " + getTitle();
    }

    protected String getTitle() {
        return title;
    }
}
