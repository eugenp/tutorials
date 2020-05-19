package com.baeldung.vertxspring.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.annotation.PersistenceConstructor;

@Entity
public class Article {

    @Id
    private Long id;
    private String article;

    private Article() {
    }

    @PersistenceConstructor
    public Article(Long id, String article) {
        super();
        this.id = id;
        this.article = article;
    }

    @Override
    public String toString() {
        return "Article [id=" + id + ", article=" + article + "]";
    }

    public Long getArticleId() {
        return id;
    }

    public void setArticleId(Long id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

}
