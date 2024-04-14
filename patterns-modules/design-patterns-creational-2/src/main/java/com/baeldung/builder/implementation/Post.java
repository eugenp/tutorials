package com.baeldung.builder.implementation;

public class Post {

    private String title;

    private String text;

    private String category;

    Post(Builder builder) {
        this.title = builder.title;
        this.text = builder.text;
        this.category = builder.category;
    }

    Post() {}

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getCategory() {
        return category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Post{" + "title='" + title + '\'' + ", text='" + text + '\'' + ", category='" + category + '\'' + '}';
    }

    public static class Builder {
        private String title;
        private String text;
        private String category;

        public Builder() {}

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
