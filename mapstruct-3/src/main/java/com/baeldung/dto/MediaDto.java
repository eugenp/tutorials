package com.baeldung.dto;

public class MediaDto {

    private Long id;

    private String title;

    public MediaDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public MediaDto() {
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
        return "MediaDto{" + "id=" + id + ", title='" + title + '\'' + '}';
    }
}
