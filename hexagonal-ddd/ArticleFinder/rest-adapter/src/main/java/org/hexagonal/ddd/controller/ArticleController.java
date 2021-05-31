package org.hexagonal.ddd.controller;

import org.hexagonal.ddd.domain.Article;
import org.hexagonal.ddd.domain.ports.ext.IArticleAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleAPI articleAPI;

    @PostMapping("/add")
    public String addArticle(@RequestBody Article article) {
        return articleAPI.addArticle(article);
    }

    @PutMapping("/update")
    public String updateArticle(@RequestBody Article article) {
        return articleAPI.updateArticle(article);
    }

    @GetMapping("/all")
    public List<Article> getTopNArticles() {
        return articleAPI.getArticles();
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable String id) {
        return articleAPI.getArticleById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable String id) {
        return articleAPI.deleteArticle(id);
    }
}
