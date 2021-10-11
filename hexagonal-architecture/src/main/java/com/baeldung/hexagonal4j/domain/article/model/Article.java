package com.baeldung.hexagonal4j.domain.article.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Article {

    private Long id;
    private Long accountId;
    private String title;
    private String body;
}
