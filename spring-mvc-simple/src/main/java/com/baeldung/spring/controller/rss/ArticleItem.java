package com.baeldung.spring.controller.rss;

import java.io.Serializable;

public class ArticleItem extends RssData implements Serializable {
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "ArticleItem{" +
                "author='" + author + '\'' +
                '}';
    }
}
