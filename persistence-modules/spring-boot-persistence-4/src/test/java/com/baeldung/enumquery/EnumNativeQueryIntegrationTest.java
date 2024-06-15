package com.baeldung.enumquery;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.baeldung.enumquery.entity.Article;
import com.baeldung.enumquery.entity.ArticleStage;
import com.baeldung.enumquery.repository.ArticleRepository;

@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class EnumNativeQueryIntegrationTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void whenArticleIsSaved_thenArticleCanBeRetrievedByStage() {
        Article article = Instancio.create(Article.class);
        articleRepository.save(article);

        List<Article> retrievedArticles = articleRepository.getByStage(article.getStage());

        assertThat(retrievedArticles).element(0).usingRecursiveComparison().isEqualTo(article);
    }

    @Test
    void whenArticlesAreSavedWithDifferentStages_thenArticlesCanBeRetrievedByStageList() {
        List<Article> articles = Instancio.of(Article.class).stream().limit(100).toList();
        articleRepository.saveAll(articles);

        List<ArticleStage> stagesToQuery = List.of(ArticleStage.TODO, ArticleStage.IN_PROGRESS);
        List<Article> retrievedArticles = articleRepository.getByStageIn(stagesToQuery);

        assertThat(retrievedArticles).isNotEmpty().extracting(Article::getStage).doesNotContain(ArticleStage.PUBLISHED)
                .hasSameElementsAs(stagesToQuery);
    }

}
