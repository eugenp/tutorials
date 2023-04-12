package com.baeldung.springboot.swagger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baeldung.springboot.swagger.model.Article;
import com.baeldung.springboot.swagger.service.ArticleService;
import com.baeldung.springboot.swagger.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    private ArticleService articleService;

    @JsonView(Views.Public.class)
    @GetMapping("")
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PostMapping("")
    public void addArticle(@ModelAttribute Article article) {
        articleService.addArticle(article);
    }

    @PostMapping("/jsonView")
    public void addArticleJsonView(@RequestBody @JsonView(Views.Public.class) Article article) {
        articleService.addArticle(article);
    }

}
