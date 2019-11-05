package com.baeldung.hexagonalarchitecture.hexagon;

import com.baeldung.hexagonalarchitecture.hexagon.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    List<Article> findAll();

    Article addArticle(Article article);

    Optional<Article> findOne(long id);

}
