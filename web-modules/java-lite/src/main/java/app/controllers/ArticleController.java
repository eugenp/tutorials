package app.controllers;

import javax.inject.Inject;

import org.javalite.activeweb.AppController;

import app.services.ArticleService;

public class ArticleController extends AppController {

    @Inject
    private ArticleService articleService;

    public void index() {
        view("articles", articleService.getArticles());
    }

    public void search() {

        String keyword = param("key");
        if (null != keyword) {
            assign("article", articleService.search(keyword));
        } else {
            render("/common/error");
        }

    }
}
