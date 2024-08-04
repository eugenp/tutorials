package com.baeldung.mongoclient;

import java.util.List;

import com.baeldung.MongoResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.common.QuarkusTestResource;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(MongoResource.class)
public class ArticleRepositoryLiveTest {

    @Inject
    ArticleRepository articleRepository;

    @BeforeEach
    public void setup() {
        articleRepository.deleteAll();
    }

    @Test
    public void whenCreate_thenArticleIsPersisted() {
        Article article = new Article();
        article.setId(new ObjectId());
        article.setAuthor("Author");
        article.setTitle("Title");
        article.setDescription("Description");

        articleRepository.create(article);
        assertNotNull(article.getId());
    }

    @Test
    public void whenListAll_thenAllArticlesAreRetrieved() {
        Article article = new Article();
        article.setAuthor("Author");
        article.setTitle("Title");
        article.setDescription("Description");
        articleRepository.create(article);

        List<Article> articles = articleRepository.listAll();
        assertEquals(1, articles.size());
    }

    @Test
    public void whenUpdate_thenArticleIsUpdated() {
        Article article = new Article();
        article.setAuthor("Author");
        article.setTitle("Title");
        article.setDescription("Description");
        articleRepository.create(article);

        article.setAuthor("Updated Author");
        article.setTitle("Updated Title");
        article.setDescription("Updated Description");
        articleRepository.update(article);

        Article updatedArticle = articleRepository.listAll().getFirst();
        assertEquals("Updated Author", updatedArticle.getAuthor());
        assertEquals("Updated Title", updatedArticle.getTitle());
        assertEquals("Updated Description", updatedArticle.getDescription());
    }

    @Test
    public void whenDelete_thenArticleIsDeleted() {
        Article article = new Article();
        article.setAuthor("Author");
        article.setTitle("Title");
        article.setDescription("Description");
        articleRepository.create(article);

        articleRepository.delete(article.getId().toHexString());
        List<Article> articles = articleRepository.listAll();
        assertEquals(0, articles.size());
    }
}
