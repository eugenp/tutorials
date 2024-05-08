package com.baeldung.mockmvcresponse;

public class Article {

    private Long id;

    private String title;

    public Article() {
    }

    public Article(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
