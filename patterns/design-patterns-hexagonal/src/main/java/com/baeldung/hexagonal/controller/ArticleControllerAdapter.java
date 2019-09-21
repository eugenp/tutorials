package com.baeldung.hexagonal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.controller.interfaces.IArticlePort;
import com.baeldung.hexagonal.dataacess.service.ArticleService;
import com.baeldung.hexagonal.dataccess.entities.ArticleModel;

@RestController
@RequestMapping("/article/")
public class ArticleControllerAdapter implements IArticlePort {
    
    @Autowired
    private ArticleService articleService;
    
    @Override
    @GetMapping("/{id}")
    public ArticleModel getArticle(@PathVariable Integer articleId) {
        ArticleModel article = articleService.getArticle(articleId);
        return article;
    }
}