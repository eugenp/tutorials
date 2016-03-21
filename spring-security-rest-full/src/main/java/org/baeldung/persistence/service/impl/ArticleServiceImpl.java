package org.baeldung.persistence.service.impl;

import org.baeldung.persistence.dao.ArticleRepository;
import org.baeldung.persistence.model.Article;
import org.baeldung.persistence.model.Tag;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.Collections.disjoint;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    protected final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    @PostFilter("hasRole('ADMIN') or (hasRole('USER') and " +
        "filterObject.author.username == principal.username)")
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article save(Article comment) {
        return articleRepository.save(comment);
    }

    @Override
    @PostAuthorize("returnObject.author.username == principal.username")
    public Article find(User user, String title) {
        return articleRepository.findByAuthorAndTitle(user, title);
    }

    @Override
    @PreFilter(filterTarget = "articles",
        value = "filterObject.author.username == principal.username")
    public List<Article> filterByTags(List<Article> articles, Set<Tag> tags) {
        return articles.stream()
            .filter(a -> !disjoint(tags, a.getTags()))
            .collect(toList());
    }
}
