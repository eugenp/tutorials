package com.baeldung.db2database.controller;

import com.baeldung.db2database.entity.Article;
import com.baeldung.db2database.repository.ArticleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class ArticleController {

    private final ArticleRepository articleRepository ;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @PostMapping("/create-article")
    private ResponseEntity<Article> createArticle(@RequestBody Article article, UriComponentsBuilder ucb) {
        Article newArticle = new Article();
        newArticle.setAuthor(article.getAuthor());
        newArticle.setBody(article.getBody());
        newArticle.setTitle(article.getTitle());
        Article savedArticle = articleRepository.save(newArticle);
        URI location = ucb.path("/articles/{id}").buildAndExpand(savedArticle.getId()).toUri();

        return ResponseEntity.created(location).body(savedArticle);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return articleRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
