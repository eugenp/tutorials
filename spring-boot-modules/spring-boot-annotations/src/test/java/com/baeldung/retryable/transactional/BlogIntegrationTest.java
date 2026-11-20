package com.baeldung.retryable.transactional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = RetryableTransactionalApplication.class)
class BlogIntegrationTest {

    @Autowired
    private Blog articleService;

    @MockBean
    private ArticleSlugGenerator slugGenerator;

    @Test
    void whenPublishArticle_thenUpdateStausAndSlug() {
        Mockito.when(slugGenerator.randomSlug())
            .thenReturn("dummy-slug");
        Article article = articleService.create(new Article("Dummy Article"));

        Article publishedArticle = articleService.publishArticle(article.getId());

        assertThat(publishedArticle).hasFieldOrProperty("id")
            .hasFieldOrPropertyWithValue("status", Article.Status.PUBLISHED)
            .hasFieldOrPropertyWithValue("slug", "dummy-slug")
            .hasFieldOrPropertyWithValue("title", "Dummy Article");
    }

    @Test
    void whenPublishArticleFails_thenRetryAtLeastThreeTimes() {
        Mockito.when(slugGenerator.randomSlug())
            .thenThrow(new RuntimeException("dummy exception"))
            .thenThrow(new RuntimeException("dummy exception"))
            .thenReturn("dummy-slug");

        Article article = articleService.create(new Article("Dummy Article"));
        article = articleService.publishArticle(article.getId());

        assertThat(article).hasFieldOrProperty("id")
            .hasFieldOrPropertyWithValue("status", Article.Status.PUBLISHED)
            .hasFieldOrPropertyWithValue("slug", "dummy-slug")
            .hasFieldOrPropertyWithValue("title", "Dummy Article");
    }

    @Test
    void whenPublishArticleV2_thenUpdateStatusAndSlug() {
        Mockito.when(slugGenerator.randomSlug())
            .thenReturn("dummy-slug");
        Article article = articleService.create(new Article("Dummy Article"));

        Article publishedArticle = articleService.publishArticle_v2(article.getId());

        assertThat(publishedArticle).hasFieldOrProperty("id")
            .hasFieldOrPropertyWithValue("status", Article.Status.PUBLISHED)
            .hasFieldOrPropertyWithValue("slug", "dummy-slug")
            .hasFieldOrPropertyWithValue("title", "Dummy Article");
    }

    @Test
    void whenPublishArticleV2Fails_thenRetryFiveTimes() {
        Mockito.when(slugGenerator.randomSlug())
            .thenThrow(new RuntimeException("dummy exception"))
            .thenThrow(new RuntimeException("dummy exception"))
            .thenThrow(new RuntimeException("dummy exception"))
            .thenThrow(new RuntimeException("dummy exception"))
            .thenReturn("dummy-slug");

        Article article = articleService.create(new Article("Dummy Article"));
        article = articleService.publishArticle_v2(article.getId());

        assertThat(article).hasFieldOrProperty("id")
            .hasFieldOrPropertyWithValue("status", Article.Status.PUBLISHED)
            .hasFieldOrPropertyWithValue("slug", "dummy-slug")
            .hasFieldOrPropertyWithValue("title", "Dummy Article");
    }

}
