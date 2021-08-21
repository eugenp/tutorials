package com.baeldung.arangodb;

import com.baeldung.arangodb.model.Article;
import com.baeldung.arangodb.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ArangoDB server should be up and running for this test case to run successfully
 */
@SpringBootTest
public class ArticleRepositoryLiveTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void givenNewArticle_whenSaveInArangoDb_thenDataIsCorrect() {
        Article newArticle = new Article(
                "ArangoDb with Spring Data",
                "Baeldung Writer",
                ZonedDateTime.now(),
                "<html>Some HTML content</html>"
        );

        Article savedArticle = articleRepository.save(newArticle);

        assertNotNull(savedArticle.getId());
        assertNotNull(savedArticle.getArangoId());

        assertEquals(savedArticle.getName(), newArticle.getName());
        assertEquals(savedArticle.getAuthor(), newArticle.getAuthor());
        assertEquals(savedArticle.getPublishDate(), newArticle.getPublishDate());
        assertEquals(savedArticle.getHtmlContent(), newArticle.getHtmlContent());
    }

    @Test
    public void givenArticleId_whenReadFromArangoDb_thenDataIsCorrect() {
        Article newArticle = new Article(
                "ArangoDb with Spring Data",
                "Baeldung Writer",
                ZonedDateTime.now(),
                "<html>Some HTML content</html>"
        );

        Article savedArticle = articleRepository.save(newArticle);

        String articleId = savedArticle.getId();

        Optional<Article> article = articleRepository.findById(articleId);
        assertTrue(article.isPresent());

        Article foundArticle = article.get();

        assertEquals(foundArticle.getId(), articleId);
        assertEquals(foundArticle.getArangoId(), savedArticle.getArangoId());
        assertEquals(foundArticle.getName(), savedArticle.getName());
        assertEquals(foundArticle.getAuthor(), savedArticle.getAuthor());
        assertEquals(foundArticle.getPublishDate(), savedArticle.getPublishDate());
        assertEquals(foundArticle.getHtmlContent(), savedArticle.getHtmlContent());
    }

    @Test
    public void givenArticleId_whenDeleteFromArangoDb_thenDataIsGone() {
        Article newArticle = new Article(
                "ArangoDb with Spring Data",
                "Baeldung Writer",
                ZonedDateTime.now(),
                "<html>Some HTML content</html>"
        );

        Article savedArticle = articleRepository.save(newArticle);

        String articleId = savedArticle.getId();

        articleRepository.deleteById(articleId);

        Optional<Article> article = articleRepository.findById(articleId);
        assertFalse(article.isPresent());
    }

    @Test
    public void givenAuthorName_whenGetByAuthor_thenListOfArticles() {
        Article newArticle = new Article(
                "ArangoDb with Spring Data",
                "Baeldung Writer",
                ZonedDateTime.now(),
                "<html>Some HTML content</html>"
        );
        articleRepository.save(newArticle);

        Iterable<Article> articlesByAuthor = articleRepository.findByAuthor(newArticle.getAuthor());
        List<Article> articlesByAuthorList = new ArrayList<>();
        articlesByAuthor.forEach(articlesByAuthorList::add);

        assertEquals(1, articlesByAuthorList.size());

        Article foundArticle = articlesByAuthorList.get(0);
        assertEquals(foundArticle.getName(), newArticle.getName());
        assertEquals(foundArticle.getAuthor(), newArticle.getAuthor());
        assertEquals(foundArticle.getPublishDate(), newArticle.getPublishDate());
        assertEquals(foundArticle.getHtmlContent(), newArticle.getHtmlContent());
    }

}
