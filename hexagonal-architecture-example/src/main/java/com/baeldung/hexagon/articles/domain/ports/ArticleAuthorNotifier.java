package com.baeldung.hexagon.articles.domain.ports;

import com.baeldung.hexagon.articles.domain.model.Article;

public interface ArticleAuthorNotifier {

    void notifyAboutArticleCreation(Article article);

}
