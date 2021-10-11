package com.baeldung.hexagonal4j.domain.article.port;

import java.util.List;

import com.baeldung.hexagonal4j.domain.article.model.Article;
import com.baeldung.hexagonal4j.domain.article.usecase.ArticleCreate;
import com.baeldung.hexagonal4j.domain.article.usecase.ArticleQuery;
import lombok.Builder;
import lombok.Data;

public interface ArticlePort {

    Article create(ArticleCreate articleCreate);

    Article retrieve(Long articleId);

    List<Article> query(ArticleQuery articleQuery);
}
