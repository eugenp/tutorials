package com.baeldung.entity;

public class Media {

    private Long id;

    private String title;

    public Media(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Media() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Media{" + "id=" + id + ", title='" + title + '\'' + '}';
    }
}
