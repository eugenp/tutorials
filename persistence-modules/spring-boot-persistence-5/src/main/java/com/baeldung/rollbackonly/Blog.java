package com.baeldung.rollbackonly;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.baeldung.rollbackonly.article.Article;
import com.baeldung.rollbackonly.article.ArticleRepo;
import com.baeldung.rollbackonly.audit.Audit;
import com.baeldung.rollbackonly.audit.AuditRepo;
import com.baeldung.rollbackonly.audit.AuditService;

@Component
public class Blog {

    private final ArticleRepo articleRepo;
    private final AuditRepo auditRepo;
    private final AuditService auditService;
    private final TransactionTemplate transactionTemplate;

    Blog(ArticleRepo articleRepo, AuditRepo auditRepo, AuditService auditService, TransactionTemplate transactionTemplate) {
        this.articleRepo = articleRepo;
        this.auditRepo = auditRepo;
        this.auditService = auditService;
        this.transactionTemplate = transactionTemplate;
    }

    @Transactional
    public Optional<Long> publishArticle(Article article) {
        try {
            article = articleRepo.save(article);
            auditRepo.save(new Audit("SAVE_ARTICLE", "SUCCESS", "saved: " + article.getTitle()));
            return Optional.of(article.getId());

        } catch (Exception e) {
            String errMsg = "failed to save: %s, err: %s".formatted(article.getTitle(), e.getMessage());
            auditRepo.save(new Audit("SAVE_ARTICLE", "FAILURE", errMsg));
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Long> publishArticle_v2(Article article) {
        try {
            article = articleRepo.save(article);
            auditService.saveAudit("SAVE_ARTICLE", "SUCCESS", "saved: " + article.getTitle());
            return Optional.of(article.getId());

        } catch (Exception e) {
            auditService.saveAudit("SAVE_ARTICLE", "FAILURE", "failed to save: " + article.getTitle());
            return Optional.empty();
        }
    }

    public Optional<Long> publishArticle_v3(final Article article) {
        try {
            Article savedArticle = transactionTemplate.execute(txStatus -> {
                Article saved = articleRepo.save(article);
                auditRepo.save(new Audit("SAVE_ARTICLE", "SUCCESS", "saved: " + article.getTitle()));
                return saved;
            });
            return Optional.of(savedArticle.getId());

        } catch (Exception e) {
            auditRepo.save(
                new Audit("SAVE_ARTICLE", "FAILURE", "failed to save: " + article.getTitle()));
            return Optional.empty();
        }
    }

}
