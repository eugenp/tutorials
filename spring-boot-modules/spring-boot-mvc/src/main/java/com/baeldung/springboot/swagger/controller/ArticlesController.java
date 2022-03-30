package com.baeldung.springboot.swagger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baeldung.springboot.swagger.model.Article;
import com.baeldung.springboot.swagger.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("")
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PostMapping("")
    public void addArticle(@RequestBody Article article) {
        articleService.addArticle(article);
    }

}
