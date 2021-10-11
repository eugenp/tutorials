package com.baeldung.hexagonal4j.domain.article.usecase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleQuery {

    private Long accountId;

    public static ArticleQuery from(Long accountId){
        return ArticleQuery.builder().accountId(accountId).build();
    }
}
