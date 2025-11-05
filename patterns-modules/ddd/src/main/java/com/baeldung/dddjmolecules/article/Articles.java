package com.baeldung.dddjmolecules.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jmolecules.ddd.annotation.Repository;

import com.baeldung.dddjmolecules.article.Article.Status;

@Repository
public class Articles {
    Slug save(Article draft) {
        // save to DB
        return draft.getSlug();
    }

    Optional<Article> find(Slug slug) {
        // query DB
        return Optional.empty();
    }

    List<Article> filterByStatus(Status status) {
        // query DB
        return new ArrayList<>();
    }

    void remove(Slug article) {
        // update DB and mark as removed
    }

}
