package com.baeldung.hexagonal4j.domain.article.usecase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleCreate {
    private Long accountId;
    private String title;
    private String body;
}
