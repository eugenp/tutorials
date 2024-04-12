package com.baeldung.arangodb.model;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Relations;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;
import java.util.Collection;

@Document("articles")
public class Article {

    @Id
    private String id;

    @ArangoId
    private String arangoId;

    private String name;
    private String author;
    private ZonedDateTime publishDate;
    private String htmlContent;

    @Relations(edges = ArticleLink.class, lazy = true)
    private Collection<Author> authors;

    public Article() {
        super();
    }

    public Article(String name, String author, ZonedDateTime publishDate, String htmlContent) {
        this.name = name;
        this.author = author;
        this.publishDate = publishDate;
        this.htmlContent = htmlContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArangoId() {
        return arangoId;
    }

    public void setArangoId(String arangoId) {
        this.arangoId = arangoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ZonedDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(ZonedDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
