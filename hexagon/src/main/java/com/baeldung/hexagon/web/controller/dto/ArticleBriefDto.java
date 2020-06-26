package com.baeldung.hexagon.web.controller.dto;

import com.baeldung.hexagon.domain.Article;
import lombok.Value;

@Value
public class ArticleBriefDto {
    private int id;
    private String name;

    public static ArticleBriefDto of(Article article) {
        return new ArticleBriefDto(
                article.getId(),
                article.getName()
        );
    }
}
