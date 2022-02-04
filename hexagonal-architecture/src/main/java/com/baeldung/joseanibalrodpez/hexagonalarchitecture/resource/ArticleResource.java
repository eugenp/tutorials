package com.baeldung.joseanibalrodpez.hexagonalarchitecture.resource;

import com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.model.Article;
import com.baeldung.joseanibalrodpez.hexagonalarchitecture.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "articles")
public class ArticleResource {

    private final ArticleService articleService;

    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<Article>> getArticles() {
        final List<Article> article = this.articleService.getArticles();
        return ResponseEntity.ok(article);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        final Article article = this.articleService.getArticle(id);
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article) {
        final Article created = this.articleService.createArticle(article);
        return ResponseEntity.ok(created);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @Valid @RequestBody Article article) {
        final Article updated = this.articleService.updateArticle(id, article);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        final Article article = this.articleService.deleteArticle(id);
        return ResponseEntity.ok(article);
    }
}
