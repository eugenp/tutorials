package com.baeldung.arangodb.model;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;

@Edge
public class ArticleLink {

    @From
    private Article article;

    @To
    private Author author;

    public ArticleLink() {
    }

    public ArticleLink(Article article, Author author) {
        this.article = article;
        this.author = author;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
