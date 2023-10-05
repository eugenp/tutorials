package com.baeldung.restclient;

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
    public Collection<Article> getArticles() {
        return database.values();
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Integer id) {
        return database.get(id);
    }

    @PostMapping
    public void createArticle(@RequestBody Article article) {
        database.put(article.getId(), article);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable Integer id, @RequestBody Article article) {
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
