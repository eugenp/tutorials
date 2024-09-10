package com.baeldung;

import java.io.Serial;
import java.io.Serializable;

public class Article implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private StringBuilder content;

    public Article() {
    }

    public Article(String title, StringBuilder content) {
        this.title = title;
        this.content = new StringBuilder(content.toString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }

    @Override
    public Article clone() {
        try {
            return (Article) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }

    @Override
    public String toString() {
        return "Article{" + "title='" + title + '\'' + ", content='" + content + '\'' + '}';
    }
}



