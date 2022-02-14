package com.baeldung.joseanibalrodpez.hexagonalarchitecture;

import com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.model.Article;
import com.baeldung.joseanibalrodpez.hexagonalarchitecture.domain.repository.DefaultArticleRepository;
import com.baeldung.joseanibalrodpez.hexagonalarchitecture.service.DefaultArticleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.mockito.Mockito.when;

@SpringBootTest
public class FindByIdTest {

    @Mock
    private DefaultArticleRepository articleRepository;

    @InjectMocks
    private DefaultArticleService articleService;

    @Test
    void get_article_with_null_id() {
        when(articleRepository.getArticle(null)).thenReturn(null);
        final Article article = this.articleService.getArticle(null);
        Assert.isNull(article, () -> "Article must be null");
    }

    @Test
    void get_article_with_existing_id() {
        final long article_id = 10L;
        when(articleRepository.getArticle(article_id)).thenReturn(Article.builder().id(article_id).build());
        final Article article = this.articleService.getArticle(article_id);
        Assert.isTrue(article.getId().equals(article_id), () -> "Article does not match");
    }
}
