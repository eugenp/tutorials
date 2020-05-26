package com.baeldung.hexagonal.domain;

import java.util.UUID;

public class Article {

    private UUID id;
    private String name;
    private String text;

    public Article(String name, String text) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
