package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Article;

public interface ArticleServicePort {

    void createArticle(Article article);

    Article findArticleByName(String name);

}
