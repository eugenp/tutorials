package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Article;
import com.baeldung.hexagonal.port.ArticleServicePort;
import com.baeldung.hexagonal.port.ArticleWebUIPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public class ArticleWebAdapter implements ArticleWebUIPort {

    @Autowired
    private ArticleServicePort articleServicePort;

    @Override
    public void createArticle(@RequestBody Article article) {
        articleServicePort.createArticle(article);
    }

    @Override
    public Article findArticleByName(@PathVariable String name) {
        return articleServicePort.findArticleByName(name);
    }

}
