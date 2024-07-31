package com.baeldung.spring.data.jpa.spel.controller;

import com.baeldung.spring.data.jpa.spel.entity.Article;
import com.baeldung.spring.data.jpa.spel.repository.ArticleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    List<Article> getAllArticlesWithNativeQuery() {
        return articleRepository.findAllArticlesUsingLocaleWithNativeQuery();
    }
}
