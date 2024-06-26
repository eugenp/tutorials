package com.baeldung.mockmvcresponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ArticleController {

    @GetMapping("/article")
    public Article getArticle() {
        return new Article(1L, "Learn Spring Boot");
    }

    @GetMapping("/articles")
    public List<Article> getArticles() {
        return List.of(new Article(1L, "Guide to JUnit"), new Article(2L, "Working with Hibernate"));
    }

}
