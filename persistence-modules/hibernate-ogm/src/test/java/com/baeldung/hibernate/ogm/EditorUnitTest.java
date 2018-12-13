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
            persistAndVerify(entityManagerFactory, editor);
    }
    */
    @Test
    public void givenNeo4j_WhenEntitiesCreated_thenCanBeRetrieved() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ogm-neo4j");
        Editor editor = generateTestData();
        persistAndVerify(entityManagerFactory, editor);
    }

    @Test
    public void givenInfinispan_WhenEntitiesCreated_thenCanBeRetrieved() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ogm-infinispan");
        Editor editor = generateTestData();
        persistAndVerify(entityManagerFactory, editor);
    }

    private void persistAndVerify(EntityManagerFactory entityManagerFactory, Editor editor) throws Exception {
        TransactionManager transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
        EntityManager entityManager;

        // Persist Entities & Relations
        transactionManager.begin();
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(editor);
        entityManager.close();
        transactionManager.commit();

        // Retrieve Entities & Relations and verify
        transactionManager.begin();
        entityManager = entityManagerFactory.createEntityManager();
        Editor loadedEditor = entityManager.find(Editor.class, editor.getEditorId());
        assertThat(loadedEditor).isNotNull();
        assertThat(loadedEditor.getEditorName()).isEqualTo("Tom");
        assertThat(loadedEditor.getAssignedAuthors()).onProperty("authorName")
            .containsOnly("Maria", "Mike");
        Map<String, Author> authors = loadedEditor.getAssignedAuthors()
            .stream()
            .collect(Collectors.toMap(Author::getAuthorName, e -> e));
        assertThat(authors.get("Maria")
            .getAuthoredArticles()).onProperty("articleTitle")
                .containsOnly("Basic of Hibernate OGM");
        assertThat(authors.get("Mike")
            .getAuthoredArticles()).onProperty("articleTitle")
                .containsOnly("Intermediate of Hibernate OGM", "Advanced of Hibernate OGM");
        entityManager.close();
        transactionManager.commit();
    }

    private Editor generateTestData() {
        // Create Entities & Relations
        Editor tom = new Editor("Tom", "tom@publishing.com");
        Author maria = new Author("Maria", "maria@publishing.com");
        Author mike = new Author("Mike", "mike@publishing.com");
        Article basic = new Article("Basic of Hibernate OGM", "https://publishing.com/hibernate/ogm/basic");
        Article intermediate = new Article("Intermediate of Hibernate OGM", "https://publishing.com/hibernate/ogm/intermediate");
        Article advanced = new Article("Advanced of Hibernate OGM", "https://publishing.com/hibernate/ogm/advanced");
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