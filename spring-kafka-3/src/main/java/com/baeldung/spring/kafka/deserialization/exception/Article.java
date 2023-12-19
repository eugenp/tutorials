package com.baeldung.spring.kafka.deserialization.exception;

import java.time.LocalDate;
import java.util.Objects;

public class Article {

    private String article;
    private String author;


    public Article() {
    }

    public Article(String article, String author) {
        this.article = article;
        this.author = author;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return article + " by " + author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article1 = (Article) o;
        if (!Objects.equals(article, article1.article)) {
            return false;
        }
        return Objects.equals(author, article1.author);
    }

    @Override
    public int hashCode() {
        int result = article != null ? article.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }
}