package com.baeldung.retryable.transactional;

import java.time.Duration;
import java.util.Optional;

import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class Blog {

    private final ArticleRepository articles;
    private final ArticleSlugGenerator slugGenerator;
    private final TransactionTemplate transactionTemplate;

    private final RetryTemplate retryTemplate = new RetryTemplateBuilder().maxAttempts(5)
        .fixedBackoff(Duration.ofMillis(100))
        .build();

    public Blog(ArticleRepository articleRepository, ArticleSlugGenerator articleSlugGenerator, TransactionTemplate transactionTemplate) {
        this.articles = articleRepository;
        this.slugGenerator = articleSlugGenerator;
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    @Retryable(maxAttempts = 3)
    /**
     * Publishes an article using a transactional operation that is automatically retried
     * in case of transient failures.
     *
     * When {@link Retryable} is used together with {@link Transactional}, each retry
     * triggers a new transaction. Therefore, the operation should be idempotent to
     * avoid unintended side effects such as duplicate updates.
     */
    public Article publishArticle(Long draftId) {
        Article article = articles.findById(draftId)
            .orElseThrow();

        article.setStatus(Article.Status.PUBLISHED);
        article.setSlug(slugGenerator.randomSlug());

        return articles.save(article);
    }

    public Article publishArticle_v2(Long draftId) {
        return retryTemplate.execute(retryCtx -> transactionTemplate.execute(txCtx -> {

            Article article = articles.findById(draftId)
                .orElseThrow();

            article.setStatus(Article.Status.PUBLISHED);
            article.setSlug(slugGenerator.randomSlug());

            return articles.save(article);

        }));
    }

    public Optional<Article> findById(Long id) {
        return articles.findById(id);
    }

    public Article create(Article article) {
        return articles.save(article);
    }

}
