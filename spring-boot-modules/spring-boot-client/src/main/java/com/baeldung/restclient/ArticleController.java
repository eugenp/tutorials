package com.baeldung.restclient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
class ArticleController {

    Map<Integer, Article> database = new HashMap<>();

    @GetMapping
    ResponseEntity<Collection<Article>> getArticles() {
        Collection<Article> values = database.values();
        if (values.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(values);
    }

    @GetMapping("/{id}")
    ResponseEntity<Article> getArticle(@PathVariable("id") Integer id) {
        Article article = database.get(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @GetMapping(value = "/{id}", headers = "API-Version=2")
    ResponseEntity<Article> getArticleV2(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(new Article(100, "SECRET ARTICLE"));
    }

    @GetMapping("/search")
    ResponseEntity<Article> searchArticleByTitle(@RequestParam(name = "title") String title) {
        Optional<Article> article = database.values().stream()
            .filter(a -> a.getTitle().contains(title))
            .findFirst();
        if (article.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article.get());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    void createArticle(@RequestBody Article article) {
        database.put(article.getId(), article);
    }

    @PutMapping("/{id}")
    void updateArticle(@PathVariable("id") Integer id, @RequestBody Article article) {
        assert Objects.equals(id, article.getId());
        database.remove(id);
        database.put(id, article);
    }

    @DeleteMapping("/{id}")
    void deleteArticle(@PathVariable("id") Integer id) {
        database.remove(id);
    }

    @DeleteMapping
    void deleteAllArticles() {
        database.clear();
    }
}