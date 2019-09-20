package com.baeldung.demo.hexagonal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.demo.hexagonal.controller.interfaces.IArticlePort;
import com.baeldung.demo.hexagonal.dataacess.service.ArticleService;
import com.baeldung.demo.hexagonal.dataccess.model.ArticleModel;

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
