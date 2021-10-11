package com.baeldung.hexagonal4j.domain.article.usecase;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleRetrieve {
    private Long id;

    public static ArticleRetrieve from(Long id){
        return  ArticleRetrieve.builder().id(id).build();
    }
}
