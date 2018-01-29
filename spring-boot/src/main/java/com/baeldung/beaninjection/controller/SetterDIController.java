package com.baeldung.beaninjection.controller;

import com.baeldung.beaninjection.model.Article;
import com.baeldung.beaninjection.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by ggallo on 23/01/2018.
 */
@Controller
public class SetterDIController {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public List<Article> getArticles() {
        return articleService.listArticles();
    }

}
