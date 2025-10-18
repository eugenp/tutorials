package com.baeldung.rollbackonly;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import com.baeldung.rollbackonly.article.Article;
import com.baeldung.rollbackonly.article.ArticleRepo;
import com.baeldung.rollbackonly.audit.AuditRepo;

@SpringBootTest(classes = { Application.class })
class BlogIntegrationTest {

    @Autowired
    private Blog articleService;

    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private AuditRepo auditRepo;

    @BeforeEach
    void afterEach() {
        articleRepo.deleteAll();
        auditRepo.deleteAll();
    }

    @Test
    void whenPublishingAnArticle_thenAlsoSaveSuccessAudit() {
        articleService.publishArticle(new Article("Test Article", "John Doe"));

        assertThat(articleRepo.findAll())
            .extracting("title")
            .containsExactly("Test Article");

        assertThat(auditRepo.findAll())
            .extracting("description")
            .containsExactly("saved: Test Article");
    }

    @Test
    void whenPublishingAnInvalidArticle_thenThrowsUnexpectedRollbackException() {
        assertThatThrownBy(() -> articleService.publishArticle(
                new Article("Test Article", null)))
            .isInstanceOf(UnexpectedRollbackException.class)
            .hasMessageContaining("Transaction silently rolled back because it has been marked as rollback-only");

        assertThat(auditRepo.findAll())
            .isEmpty();
    }

    @Test
    void whenPublishingAnInvalidArticle_thenSavesFailureToAudit() {
        assertThatThrownBy(() -> articleService.publishArticle_v2(
                new Article("Test Article", null)))
            .isInstanceOf(Exception.class);

        assertThat(auditRepo.findAll())
            .extracting("description")
            .containsExactly("failed to save: Test Article");
    }

    @Test
    void whenPublishingAnInvalidArticle_thenRecoverFromError_andSavesFailureToAudit() {
        Optional<Long> id = articleService.publishArticle_v3(
            new Article("Test Article", null));

        assertThat(id)
            .isEmpty();

        assertThat(auditRepo.findAll())
            .extracting("description")
            .containsExactly("failed to save: Test Article");
    }
}
