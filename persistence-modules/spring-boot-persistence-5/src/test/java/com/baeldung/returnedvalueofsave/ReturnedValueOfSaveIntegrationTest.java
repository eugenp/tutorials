package com.baeldung.returnedvalueofsave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.returnedvalueofsave.entity.BaeldungArticle;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@SpringBootTest(classes = UseReturnedValueOfSaveApp.class)
@ActiveProfiles("returned-value-of-save")
public class ReturnedValueOfSaveIntegrationTest {

    @Autowired
    private BaeldungArticleRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void whenNewArticleIsSaved_thenOriginalAndSavedResultsAreTheSame() {
        BaeldungArticle article = new BaeldungArticle();
        article.setTitle("Learning about Spring Data JPA");
        article.setContent(" ... the content ...");
        article.setAuthor("Kai Yuan");

        assertNull(article.getId());

        BaeldungArticle savedArticle = repo.save(article);
        assertNotNull(article.getId());

        assertSame(article, savedArticle);
    }

    @Test
    @Transactional
    void whenArticleIsMerged_thenOriginalAndSavedResultsAreNotTheSame() {
        // prepare an existing theArticle
        BaeldungArticle theArticle = new BaeldungArticle();
        theArticle.setTitle("Learning about Spring Boot");
        theArticle.setContent(" ... the content ...");
        theArticle.setAuthor("Kai Yuan");
        BaeldungArticle existingOne = repo.save(theArticle);
        Long id = existingOne.getId();

        // create a detached theArticle with the same id
        BaeldungArticle articleWithId = new BaeldungArticle();
        articleWithId.setTitle("Learning Kotlin");
        articleWithId.setContent(" ... the content ...");
        articleWithId.setAuthor("Eric");
        articleWithId.setId(id); //set the same id

        BaeldungArticle savedArticle = repo.save(articleWithId);
        assertEquals("Learning Kotlin", savedArticle.getTitle());
        assertEquals("Eric", savedArticle.getAuthor());
        assertEquals(id, savedArticle.getId());

        assertNotSame(articleWithId, savedArticle);
        assertFalse(entityManager.contains(articleWithId));
        assertTrue(entityManager.contains(savedArticle));
    }

    @Test
    void whenArticleIsMerged_thenDetachedObjectCanHaveDifferentValuesFromTheManagedOne() {
        // prepare an existing theArticle
        BaeldungArticle theArticle = new BaeldungArticle();
        theArticle.setTitle("Learning about Java Classes");
        theArticle.setContent(" ... the content ...");
        theArticle.setAuthor("Kai Yuan");
        BaeldungArticle existingOne = repo.save(theArticle);
        Long id = existingOne.getId();

        // create a detached theArticle with the same id
        BaeldungArticle articleWithId = new BaeldungArticle();
        theArticle.setTitle("Learning Kotlin Classes");
        theArticle.setContent(" ... the content ...");
        theArticle.setAuthor("Eric");
        articleWithId.setId(id); //set the same id

        BaeldungArticle savedArticle = repo.save(articleWithId);
        assertNotSame(articleWithId, savedArticle);

        assertFalse(articleWithId.isAlreadySaved());
        assertTrue(savedArticle.isAlreadySaved());
    }
}