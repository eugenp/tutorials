package com.baeldung.hexagonalarchitecture.hexagon;

import com.baeldung.hexagonalarchitecture.hexagon.domain.Article;
import com.baeldung.hexagonalarchitecture.port.driven.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> findAll() {
        // Only return publishable articles and put the last articles at the top
        return articleRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Article::getCreationTime).reversed())
                .filter(Article::isPublishable)
                .collect(Collectors.toList());
    }

    @Override
    public Article addArticle(Article article) {
        // A very small business logic to check if title and content are not empty
        if (!StringUtils.isEmpty(article.getTitle())
                && !StringUtils.isEmpty(article.getContent())) {

            article.setPublishable(true);
        }
        article.setCreationTime(Date.from(Instant.now()));
        return articleRepository.addArticle(article);
    }

    @Override
    public Optional<Article> findOne(long id) {
        return articleRepository.findOne(id);
    }
}
