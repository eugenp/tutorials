package com.baeldung.java_8_features.groupingby;

import java.util.Objects;

public class Tuple {
    private final BlogPostType type;
    private final String author;

    public Tuple(BlogPostType type, String author) {
        this.type = type;
        this.author = author;
    }

    public BlogPostType getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tuple tuple = (Tuple) o;
        return type == tuple.type && author.equals(tuple.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, author);
    }

    @Override
    public String toString() {
        return "Tuple{" + "type=" + type + ", author='" + author + '\'' + '}';
    }
}
