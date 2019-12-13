package com.baeldung.hexagon.articles.adapters.api;

import com.fasterxml.jackson.annotation.JsonProperty;

class ArticleIdResponse {
    private final String id;

    private ArticleIdResponse(final String id) {
        this.id = id;
    }

    @JsonProperty("id")
    public String id() {
        return id;
    }

    public static ArticleIdResponse of(final String articleId) {
        return new ArticleIdResponse(articleId);
    }

}
