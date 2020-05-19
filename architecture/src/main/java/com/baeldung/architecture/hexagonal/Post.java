package com.baeldung.architecture.hexagonal;

import java.util.Objects;
import java.util.UUID;

public class Post {

    private UUID id;
    private String title;
    private String content;

    public Post() {}

    public Post(String title, String content) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Post)) {
            return false;
        }

        Post that = (Post) other;
        return Objects.equals(id, that.id);
    }
}
