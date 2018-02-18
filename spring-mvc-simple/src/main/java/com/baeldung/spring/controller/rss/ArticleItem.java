package com.baeldung.spring.controller.rss;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

@JacksonXmlRootElement(localName="article")
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
