package com.baeldung.hexagonal4j.domain.article;

import java.util.List;

import com.baeldung.hexagonal4j.domain.article.model.Article;
import com.baeldung.hexagonal4j.domain.article.port.ArticlePort;
import com.baeldung.hexagonal4j.domain.article.usecase.ArticleQuery;

public class ArticleQueryUseCase {

    final ArticlePort articlePort;

    public ArticleQueryUseCase(ArticlePort articlePort) {
        this.articlePort = articlePort;
    }

    List<Article> query(ArticleQuery useCase){
        return articlePort.query(useCase);
    }
}
