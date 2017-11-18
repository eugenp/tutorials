package com.baeldung.model;

public class Article {
    private String id;
    private String content;
    private String author;
    private String datePublished;
    private int wordCount;

    public Article(String id, String content, String author, String datePublished, int wordCount) {
        super();
        this.id = id;
        this.content = content;
        this.author = author;
        this.datePublished = datePublished;
        this.wordCount = wordCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

}
