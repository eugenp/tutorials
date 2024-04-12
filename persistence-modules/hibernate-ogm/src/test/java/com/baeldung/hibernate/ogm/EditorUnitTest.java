package com.baeldung.hibernate.ogm;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

import org.junit.Test;

public class EditorUnitTest {
    /*
    @Test
    public void givenMongoDB_WhenEntitiesCreated_thenCanBeRetrieved() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ogm-mongodb");
        Editor editor = generateTestData();
        persistTestData(entityManagerFactory, editor);
        loadAndVerifyTestData(entityManagerFactory, editor);
    }
    */

    @Test
    public void givenNeo4j_WhenEntitiesCreated_thenCanBeRetrieved() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ogm-neo4j");
        Editor editor = generateTestData();
        persistTestData(entityManagerFactory, editor);
        loadAndVerifyTestData(entityManagerFactory, editor);
    }

    private void persistTestData(EntityManagerFactory entityManagerFactory, Editor editor) throws Exception {
        TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
        EntityManager entityManager;

        transactionManager.begin();
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(editor);
        entityManager.close();
        transactionManager.commit();
    }

    private void loadAndVerifyTestData(EntityManagerFactory entityManagerFactory, Editor editor) throws Exception {
        TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
        EntityManager entityManager;

        transactionManager.begin();
        entityManager = entityManagerFactory.createEntityManager();
        Editor loadedEditor = entityManager.find(Editor.class, editor.getEditorId());
        assertThat(loadedEditor).isNotNull();
        assertThat(loadedEditor.getEditorName()).isEqualTo("Tom");
        assertThat(loadedEditor.getAssignedAuthors()).onProperty("authorName")
            .containsOnly("Maria", "Mike");
        Map<String, Author> loadedAuthors = loadedEditor.getAssignedAuthors()
            .stream()
            .collect(Collectors.toMap(Author::getAuthorName, e -> e));
        assertThat(loadedAuthors.get("Maria")
            .getAuthoredArticles()).onProperty("articleTitle")
                .containsOnly("Basic of Hibernate OGM");
        assertThat(loadedAuthors.get("Mike")
            .getAuthoredArticles()).onProperty("articleTitle")
                .containsOnly("Intermediate of Hibernate OGM", "Advanced of Hibernate OGM");
        entityManager.close();
        transactionManager.commit();
    }

    private Editor generateTestData() {
        Editor tom = new Editor("Tom");
        Author maria = new Author("Maria");
        Author mike = new Author("Mike");
        Article basic = new Article("Basic of Hibernate OGM");
        Article intermediate = new Article("Intermediate of Hibernate OGM");
        Article advanced = new Article("Advanced of Hibernate OGM");
        maria.getAuthoredArticles()
            .add(basic);
        basic.setAuthor(maria);
        mike.getAuthoredArticles()
            .add(intermediate);
        intermediate.setAuthor(mike);
        mike.getAuthoredArticles()
            .add(advanced);
        advanced.setAuthor(mike);
        tom.getAssignedAuthors()
            .add(maria);
        maria.setEditor(tom);
        tom.getAssignedAuthors()
            .add(mike);
        mike.setEditor(tom);
        return tom;
    }
}