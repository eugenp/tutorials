package com.baeldung.hexagon.web.controller.dto;

import com.baeldung.hexagon.domain.Article;
import lombok.Value;

import java.util.Date;

@Value
public class ArticleDto {
    private int id;
    private String name;
    private Date lastModifiedDate;
    private long visits;

    public static ArticleDto of(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getName(),
                article.getLastModifiedDate(),
                article.getVisits()
        );
    }
}
