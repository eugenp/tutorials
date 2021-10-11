package com.baeldung.hexagonal4j.infrastructure.adapter.article.persistence;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.baeldung.hexagonal4j.domain.article.model.Article;
import com.baeldung.hexagonal4j.domain.article.port.ArticlePort;
import com.baeldung.hexagonal4j.domain.article.usecase.ArticleCreate;
import com.baeldung.hexagonal4j.domain.article.usecase.ArticleQuery;

public class ArticleImMemoryDataAdapter implements ArticlePort {

    private final ConcurrentHashMap<Long,Article> articles = new ConcurrentHashMap<>();

    @Override
    public Article create(ArticleCreate articleCreate) {
        long id = (articles.size() + 1);
        Article article = Article.builder()
            .id(id)
            .accountId(articleCreate.getAccountId())
            .title(articleCreate.getTitle())
            .body(articleCreate.getBody())
            .build();
        articles.put(id,article);
        return article;
    }

    @Override
    public Article retrieve(Long articleId) {
        return articles.get(articleId);
    }

    @Override
    public List<Article> query(ArticleQuery articleQuery) {
        return articles.values().stream()
            .filter(a-> a.getAccountId().equals(articleQuery.getAccountId()))
            .collect(Collectors.toList());
    }
}
