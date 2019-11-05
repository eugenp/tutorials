package com.baeldung.hexagonalarchitecture.port.driven;

import com.baeldung.hexagonalarchitecture.hexagon.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    List<Article> findAll();

    Article addArticle(Article article);

    Optional<Article> findOne(long id);

}
