package com.baeldung;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Baeldung implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Article> articles;

    // Constructors
    public Baeldung() {
        this.articles = new ArrayList<>();
    }

    public Baeldung(List<Article> articles) {
        List<Article> newArticle = new ArrayList<>();
        articles.forEach(article -> newArticle.add(new Article(article.getTitle(), article.getContent())));
        this.articles = newArticle;
    }

    // Getters and Setters

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    // Method to add an article
    public void addArticle(Article article) {
        this.articles.add(article);
    }

    // Clone method for deep copy

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static Baeldung createDeepCopy(Baeldung baeldung) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(baeldung), Baeldung.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String toString() {
        return "Baeldung{" + ", articles=" + articles + '}';
    }
}
