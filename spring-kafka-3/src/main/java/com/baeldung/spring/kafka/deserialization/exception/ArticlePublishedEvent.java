package com.baeldung.spring.kafka.deserialization.exception;

public class ArticlePublishedEvent {
    private final String article;

    public ArticlePublishedEvent(String article) {
        this.article = article;
    }

    public String getArticle() {
        return article;
    }

    @Override
    public String toString() {
        return article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticlePublishedEvent that = (ArticlePublishedEvent) o;
        return article.equals(that.article);
    }

    @Override
    public int hashCode() {
        return article.hashCode();
    }
}