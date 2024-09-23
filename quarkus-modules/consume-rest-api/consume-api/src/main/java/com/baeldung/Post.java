package com.baeldung;

public class Post {

    public Long id;

    public String title;

    public String description;

    Post(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    Post() {
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }
}
