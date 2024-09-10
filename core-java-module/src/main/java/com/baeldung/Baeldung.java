package com.baeldung;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Baeldung implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Article> articles;

    public Baeldung() {
        this.articles = new ArrayList<>();
    }

    public Baeldung(List<Article> articles) {
        this.articles = articles;
    }

    public Baeldung(Baeldung baeldung) {
        List<Article> newArticle = new ArrayList<>();

        baeldung.getArticles()
            .forEach(article -> newArticle.add(new Article(article.getTitle(), article.getContent())));
        this.articles = newArticle;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    protected Object clone() {
        try {
            return (Baeldung) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Baeldung{" + ", articles=" + articles + '}';
    }
}
