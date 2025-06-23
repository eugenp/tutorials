package com.baeldung.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ArticleRepository implements PanacheRepository<Article> {

    public Article findByTitle(String title) {
        return find("title", title).firstResult();
    }

    public List<Article> findPublished() {
        return list("status", "Published");
    }

    public void deleteDrafts() {
        delete("status", "Draft");
    }
}
