package com.baeldung.statelesssession;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManagerFactory;
import net.bytebuddy.utility.RandomString;
import org.hibernate.LazyInitializationException;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.TransientObjectException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Application.class)
@TestPropertySource(properties = {
    "spring.sql.init.mode=never"
})
class StatelessSessionIntegrationTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void whenInsertingEntityWithUnsavedReference_thenTransientObjectExceptionThrown() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            Author author = new Author();
            author.setName(RandomString.make());

            Article article = new Article();
            article.setTitle(RandomString.make());
            article.setAuthor(author);

            Exception exception = assertThrows(TransientObjectException.class, () -> {
                statelessSession.insert(article);
            });
            assertThat(exception.getMessage())
                .contains("object references an unsaved transient instance - save the transient instance before flushing");
        }
    }

    @Test
    void whenInsertingEntitiesInsideTransaction_thenEntitiesSavedSuccessfully() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            statelessSession.getTransaction().begin();

            Author author = new Author();
            author.setName(RandomString.make());
            statelessSession.insert(author);

            Article article = new Article();
            article.setTitle(RandomString.make());
            article.setAuthor(author);
            statelessSession.insert(article);

            statelessSession.getTransaction().commit();

            assertThat(author.getId())
                .isNotNull();
            assertThat(article.getId())
                .isNotNull();
            assertThat(article.getAuthor())
                .isEqualTo(author);
        }
    }

    @Test
    void whenCollectionAccessedLazily_thenLazyInitializationExceptionThrown() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Long authorId;
        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            statelessSession.getTransaction().begin();

            Author author = new Author();
            author.setName(RandomString.make());
            statelessSession.insert(author);

            Article article = new Article();
            article.setTitle(RandomString.make());
            article.setAuthor(author);
            statelessSession.insert(article);

            statelessSession.getTransaction().commit();
            authorId = author.getId();
        }

        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            Author author = statelessSession.get(Author.class, authorId);
            assertThat(author)
                .hasNoNullFieldsOrProperties();
            assertThrows(LazyInitializationException.class, () -> {
                author.getArticles().size();
            });
        }
    }

    @Test
    void whenRelatedEntityFetchedUsingJoin_thenCollectionLoadedEagerly() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Long authorId;

        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            statelessSession.getTransaction().begin();

            Author author = new Author();
            author.setName(RandomString.make());
            statelessSession.insert(author);

            Article article = new Article();
            article.setTitle(RandomString.make());
            article.setAuthor(author);
            statelessSession.insert(article);

            statelessSession.getTransaction().commit();
            authorId = author.getId();
        }

        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            Author author = statelessSession
                .createQuery(
                    "SELECT a FROM Author a LEFT JOIN FETCH a.articles WHERE a.id = :id",
                    Author.class)
                .setParameter("id", authorId)
                .getSingleResult();

            assertThat(author)
                .hasNoNullFieldsOrProperties();
            assertThat(author.getArticles())
                .isNotNull()
                .hasSize(1);
        }
    }

    @Test
    void whenRelatedEntityFetchedUsingEntityGraph_thenCollectionLoadedEagerly() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Long authorId;

        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            statelessSession.getTransaction().begin();

            Author author = new Author();
            author.setName(RandomString.make());
            statelessSession.insert(author);

            Article article = new Article();
            article.setTitle(RandomString.make());
            article.setAuthor(author);
            statelessSession.insert(article);

            statelessSession.getTransaction().commit();
            authorId = author.getId();
        }

        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            EntityGraph<Author> authorWithArticlesGraph = statelessSession.createEntityGraph(Author.class);
            authorWithArticlesGraph.addAttributeNodes("articles");

            Author author = statelessSession
                .createQuery("SELECT a FROM Author a WHERE a.id = :id", Author.class)
                .setParameter("id", authorId)
                .setHint("jakarta.persistence.fetchgraph", authorWithArticlesGraph)
                .getSingleResult();

            assertThat(author)
                .hasNoNullFieldsOrProperties();
            assertThat(author.getArticles())
                .isNotNull()
                .hasSize(1);
        }
    }

    @Test
    void whenSameEntityRetrievedMultipleTimes_thenDifferentInstancesReturned() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Long authorId;
        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            statelessSession.getTransaction().begin();

            Author author = new Author();
            author.setName(RandomString.make());
            statelessSession.insert(author);

            statelessSession.getTransaction().commit();
            authorId = author.getId();
        }

        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            Author author1 = statelessSession.get(Author.class, authorId);
            Author author2 = statelessSession.get(Author.class, authorId);

            assertThat(author1)
                .isNotSameAs(author2);
        }
    }

    @Test
    void whenEntityModifiedInStatelessSession_thenChangesNotAutomaticallyPersisted() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Long authorId;
        String originalName = RandomString.make();
        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            statelessSession.getTransaction().begin();

            Author author = new Author();
            author.setName(originalName);
            statelessSession.insert(author);

            author.setName(RandomString.make());

            statelessSession.getTransaction().commit();
            authorId = author.getId();
        }

        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            Author author = statelessSession.get(Author.class, authorId);
            assertThat(author.getName())
                .isEqualTo(originalName);
        }
    }

    @Test
    void whenEntityExplicitlyUpdated_thenChangesPersisted() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Long authorId;
        String newName = RandomString.make();
        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            statelessSession.getTransaction().begin();

            Author author = new Author();
            author.setName(RandomString.make());
            statelessSession.insert(author);

            author.setName(newName);
            statelessSession.update(author);

            statelessSession.getTransaction().commit();
            authorId = author.getId();
        }

        try (StatelessSession statelessSession = sessionFactory.openStatelessSession()) {
            Author author = statelessSession.get(Author.class, authorId);
            assertThat(author.getName())
                .isEqualTo(newName);
        }
    }

}