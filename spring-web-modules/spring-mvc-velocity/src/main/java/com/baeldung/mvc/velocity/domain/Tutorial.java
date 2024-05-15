package com.baeldung.mvc.velocity.domain;

public class Tutorial {

    private final Integer tutId;
    private final String title;
    private final String description;
    private final String author;

    public Tutorial(Integer tutId, String title, String description, String author) {
        this.tutId = tutId;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public Integer getTutId() {
        return tutId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

}
