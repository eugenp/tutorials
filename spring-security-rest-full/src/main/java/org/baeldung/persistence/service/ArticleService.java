package org.baeldung.persistence.service;

import org.baeldung.persistence.model.Article;
import org.baeldung.persistence.model.Tag;
import org.baeldung.persistence.model.User;

import java.util.List;
import java.util.Set;

public interface ArticleService {
    List<Article> findAll();
    Article save(Article article);
    Article find(User user, String title);
    List<Article> filterByTags(List<Article> articles, Set<Tag> tags);
}
