package com.baeldung.hexagonal4j.infrastructure.adapter.article.cli;

import java.util.List;

import com.baeldung.hexagonal4j.domain.article.model.Article;
import com.baeldung.hexagonal4j.domain.article.port.ArticlePort;
import com.baeldung.hexagonal4j.domain.article.usecase.ArticleCreate;
import com.baeldung.hexagonal4j.domain.article.usecase.ArticleQuery;
import lombok.extern.slf4j.Slf4j;

public class ArticleCli {
    private final ArticlePort articlePort;

    public ArticleCli(ArticlePort articlePort) {
        this.articlePort = articlePort;
    }

    public Article create(Long accountId,String title,String body){
        ArticleCreate article = ArticleCreate.builder().accountId(accountId).title(title).body(body).build();
        return articlePort.create(article);
    }

    public Article retrieve(Long articleId){
        return articlePort.retrieve(articleId);
    }

    public List<Article> query(Long accountId){
        return articlePort.query(ArticleQuery.from(accountId));
    }
}
