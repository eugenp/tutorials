package com.baeldung.hexagon.articles.adapters.api;

import com.baeldung.hexagon.articles.domain.ArticleId;
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

    public static ArticleIdResponse of(final ArticleId articleId) {
        return new ArticleIdResponse(articleId.value());
    }

}
