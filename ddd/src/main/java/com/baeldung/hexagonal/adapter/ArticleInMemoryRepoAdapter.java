package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Article;
import com.baeldung.hexagonal.port.ArticleRepositoryPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ArticleInMemoryRepoAdapter implements ArticleRepositoryPort {

    private final Map<UUID, Article> inMemoryDb = new HashMap<>();

    public Article save(Article article) {
        return inMemoryDb.put(article.getId(), article);
    }

    public Optional<Article> findByName(String name) {
        return inMemoryDb.values().stream()
                .filter(article -> article.getName().equals(name))
                .findAny();
    }

    public List<Article> findAll() {
        return new ArrayList<>(inMemoryDb.values());
    }
}
