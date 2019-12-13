package com.baeldung.hexagon.articles.adapters.api;

import com.baeldung.hexagon.articles.domain.model.Article;
import com.fasterxml.jackson.annotation.JsonProperty;

class ArticleResponse {
    private final String id;
    private final String title;
    private final String content;
    private final String authorName;

    private ArticleResponse(final String id, final String title, final String content, final String authorName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
    }

    static ArticleResponse of(final Article article) {
        return new ArticleResponse(article.id(),
                article.title().value(),
                article.content().value(),
                article.author().name());
    }

    @JsonProperty("id")
    public String id() {
        return id;
    }

    @JsonProperty("title")
    public String title() {
        return title;
    }

    @JsonProperty("content")
    public String content() {
        return content;
    }

    @JsonProperty("authorName")
    public String authorName() {
        return authorName;
    }
}
