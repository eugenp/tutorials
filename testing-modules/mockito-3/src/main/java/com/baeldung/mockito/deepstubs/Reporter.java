package com.baeldung.mockito.deepstubs;

public class Reporter {
    String name;

    NewsArticle latestArticle;

    public NewsArticle getLatestArticle() {
        return latestArticle;
    }

    public void setLatestArticle(NewsArticle latestArticle) {
        this.latestArticle = latestArticle;
    }

    public Reporter(String name, NewsArticle latestArticle) {
        this.name = name;
        this.latestArticle = latestArticle;
    }



}
