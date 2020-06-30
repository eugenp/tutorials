package com.baeldung.hexagon.service;

import com.baeldung.hexagon.domain.Article;
import com.baeldung.hexagon.repository.ArticleRepository;
import com.baeldung.hexagon.service.exception.ArticleNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ArticleService implements IArticleService {

    private final ArticleRepository repository;

    private final Properties properties;

    @Override
    public List<Article> articles() {
        return repository.findAll();
    }

    @Override
    public Article article(Integer id) {
        return get(id);
    }

    @Override
    public boolean isPopular(Integer id) {
        Article article = get(id);
        return article.getVisits() > properties.getPopularityThreshold();
    }

    private Article get(Integer id) {
        Optional<Article> articleOptional = repository.findById(id);

        if (articleOptional.isPresent()) {
            return articleOptional.get();
        } else {
            throw new ArticleNotFoundException(String.format("Article with id %d not found", id));
        }
    }

    @Getter
    @Setter
    public static class Properties {
        private long popularityThreshold;
    }
}
