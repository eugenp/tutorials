package com.baeldung.restclient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    Map<Integer, Article> database = new HashMap<>();

    @GetMapping
    public ResponseEntity<Collection<Article>> getArticles() {
        Collection<Article> values = database.values();
        if (values.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(values);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable("id") Integer id) {
        Article article = database.get(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public void createArticle(@RequestBody Article article) {
        database.put(article.getId(), article);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable("id") Integer id, @RequestBody Article article) {
        assert Objects.equals(id, article.getId());
        database.remove(id);
        database.put(id, article);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Integer id) {
        database.remove(id);
    }
    @DeleteMapping()
    public void deleteArticles() {
        database.clear();
    }
}
