package com.baeldung.hexagon.articles.domain;

import com.baeldung.hexagon.articles.domain.model.Article;
import com.baeldung.hexagon.articles.domain.model.Author;
import com.baeldung.hexagon.articles.domain.model.Content;
import com.baeldung.hexagon.articles.domain.model.Title;
import com.baeldung.hexagon.articles.domain.ports.ArticleRepository;
import com.baeldung.hexagon.articles.domain.ports.AuthorRepository;

public class ArticleFacade {
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;


    public ArticleFacade(final ArticleRepository articleRepository, final AuthorRepository authorRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
    }

    public String create(final String authorId, final Title title, final Content content) {
        final Author author = authorRepository.get(authorId);
        final Article article = articleRepository.save(author, title, content);
        return article.id();
    }

    public Article get(final String id) {
        final Article article = articleRepository.get(id);
        return article;
    }
}
