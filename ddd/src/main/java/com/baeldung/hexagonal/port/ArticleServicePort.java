package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Article;
import java.util.List;

public interface ArticleServicePort {

    void createArticle(String name, String text);

    Article findArticleByName(String name);

    List<Article> getAllArticles();
}
