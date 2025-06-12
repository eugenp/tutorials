package com.baeldung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArticleUnitTest {

    @BeforeEach
    @Transactional
    public void setup() {
        Article.deleteAll();
    }

    @Test
    @Transactional
    @Order(1)
    public void givenNewArticle_whenPersisted_thenItShouldHaveIdAndBeCounted() {
        Article article = new Article("Quarkus Panache", "Content of the article", "Published");
        article.persist();

        assertNotNull(article.id);
        assertEquals(1, Article.count());
    }

    @Test
    @Transactional
    @Order(2)
    public void givenMultipleArticles_whenSearchedByTitle_thenMatchingArticlesShouldBeReturned() {
        Article.persist(new Article("Quarkus Panache", "Quarkus Panache is an extension for Hibernate", "Draft"));
        Article.persist(new Article("Postgresql with Quarkus ", "Integrate Quarkus with Postgresql", "Draft"));

        List<Article> articles = Article.list("title", "Quarkus Panache");

        assertEquals(2, articles.size());
    }

    @Test
    @Order(3)
    public void givenPersistedArticle_whenDeleted_thenItShouldBeRemovedFromCount() {
        Article article = new Article("Delete Me", "Soon gone", "Draft");
        article.persist();

        assertEquals(1, Article.count());

        article.delete();

        assertEquals(0, Article.count());
    }
}
