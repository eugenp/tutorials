package com.baeldung.hibernate.entitygraph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.hibernate.Hibernate;
import org.hibernate.graph.EntityGraphs;
import org.hibernate.graph.GraphParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hibernate.entitygraph.model.Author;
import com.baeldung.hibernate.entitygraph.model.Comment;
import com.baeldung.hibernate.entitygraph.model.Moderator;
import com.baeldung.hibernate.entitygraph.model.Post;

public class NamedEntityGraphIntegrationTest {

    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void beforeTests() {
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-entitygraph-pu");
    }

    @BeforeEach
    public void setup() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Author a1 = new Author("Author 1", "author@baeldung.com", "A Baeldung Author");
        entityManager.persist(a1);

        Moderator m1 = new Moderator("Moderator 1", "mod@baeldung.com", "A Baeldung Moderator");
        entityManager.persist(m1);

        Post firstPost = new Post("First Post", a1);
        entityManager.persist(firstPost);

        entityManager.persist(new Comment("Great Start", firstPost, m1));
        entityManager.persist(new Comment("Fingers Crossed", firstPost, a1));

        Post secondPost = new Post("Second Post", m1);
        entityManager.persist(secondPost);

        entityManager.persist(new Comment("Needs Review", secondPost, a1));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterEach
    void tearDown() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("Delete from Comment").executeUpdate();
        entityManager.createQuery("Delete from Post").executeUpdate();
        entityManager.createQuery("Delete from User").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterAll
    static void afterTests() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    void whenFindWithFetchGraph_thenAssociationsAreLoaded() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        EntityGraph<Post> graph = (EntityGraph<Post>) entityManager.getEntityGraph("post-with-comment-users");
        Post post = entityManager.createQuery("Select p from Post p where p.subject = :subject", Post.class)
            .setParameter("subject", "First Post")
            .setHint("jakarta.persistence.fetchgraph", graph)
            .getSingleResult();
        entityManager.close();

        assertNotNull(post);
        assertEquals("First Post", post.getSubject());
        assertTrue(Hibernate.isInitialized(post.getUser()));
        assertTrue(Hibernate.isInitialized(post.getComments()));
        assertEquals(2, post.getComments().size());
        assertTrue(Hibernate.isInitialized(post.getComments().get(0).getUser()));
        assertTrue(Hibernate.isInitialized(post.getComments().get(1).getUser()));
    }

    @Test
    void whenFindingByIdWithEntityManagerHints_thenAssociationsAreLoaded() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Long postId = entityManager.createQuery("Select p.id from Post p where p.subject = :subject", Long.class)
            .setParameter("subject", "First Post")
            .getSingleResult();
        EntityGraph<Post> graph = (EntityGraph<Post>) entityManager.getEntityGraph("post-with-comment-users");
        Post post = entityManager.find(Post.class, postId, Map.of("jakarta.persistence.fetchgraph", graph));
        entityManager.close();

        assertNotNull(post);
        assertEquals("First Post", post.getSubject());
        assertTrue(Hibernate.isInitialized(post.getUser()));
        assertTrue(Hibernate.isInitialized(post.getComments()));
        assertEquals(2, post.getComments().size());
        assertTrue(Hibernate.isInitialized(post.getComments().get(0).getUser()));
        assertTrue(Hibernate.isInitialized(post.getComments().get(1).getUser()));
    }

    @Test
    void whenUsingPostBasicGraph_thenCommentUsersRemainLazy() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityGraph<Post> graph = (EntityGraph<Post>) entityManager.getEntityGraph("post-basic");
        Post post = entityManager.createQuery("Select p from Post p where p.subject = :subject", Post.class)
            .setParameter("subject", "First Post")
            .setHint("jakarta.persistence.fetchgraph", graph)
            .getSingleResult();
        entityManager.close();

        assertNotNull(post);
        assertEquals("First Post", post.getSubject());
        assertTrue(Hibernate.isInitialized(post.getUser()));
        assertTrue(Hibernate.isInitialized(post.getComments()));
        assertFalse(Hibernate.isInitialized(post.getComments().get(0).getUser()));
    }

    @Test
    void whenUsingTypedUserGraph_thenSubtypeAttributesAreLoaded() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityGraph<Post> graph = (EntityGraph<Post>) entityManager.getEntityGraph("post-with-typed-user");
        Post authorPost = entityManager.createQuery("Select p from Post p where p.subject = :subject", Post.class)
            .setParameter("subject", "First Post")
            .setHint("jakarta.persistence.fetchgraph", graph)
            .getSingleResult();
        Post moderatorPost = entityManager.createQuery("Select p from Post p where p.subject = :subject", Post.class)
            .setParameter("subject", "Second Post")
            .setHint("jakarta.persistence.fetchgraph", graph)
            .getSingleResult();
        entityManager.close();

        assertNotNull(authorPost);
        assertTrue(Hibernate.isInitialized(authorPost.getUser()));
        assertEquals("Author 1", authorPost.getUser().getName());
        assertInstanceOf(Author.class, authorPost.getUser());
        assertTrue(Hibernate.isPropertyInitialized(authorPost.getUser(), "bio"));
        assertEquals("A Baeldung Author", ((Author) authorPost.getUser()).getBio());

        assertNotNull(moderatorPost);
        assertTrue(Hibernate.isInitialized(moderatorPost.getUser()));
        assertEquals("Moderator 1", moderatorPost.getUser().getName());
        assertInstanceOf(Moderator.class, moderatorPost.getUser());
        assertTrue(Hibernate.isPropertyInitialized(moderatorPost.getUser(), "department"));
        assertEquals("A Baeldung Moderator", ((Moderator) moderatorPost.getUser()).getDepartment());
    }

    @Test
    void whenParsingGraphsAtRuntime_thenAssociationsAreLoaded() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityGraph<Post> parsedGraph = GraphParser.parse(Post.class, "subject, user, comments(user)", entityManager);
        EntityGraph<Post> parsedIntoGraph = entityManager.createEntityGraph(Post.class);
        GraphParser.parseInto(parsedIntoGraph, "subject, user", entityManager);
        GraphParser.parseInto(parsedIntoGraph, "comments(user)", entityManager);

        Post parsedPost = entityManager.createQuery("Select p from Post p where p.subject = :subject", Post.class)
            .setParameter("subject", "First Post")
            .setHint("jakarta.persistence.fetchgraph", parsedGraph)
            .getSingleResult();
        Post parsedIntoPost = entityManager.createQuery("Select p from Post p where p.subject = :subject", Post.class)
            .setParameter("subject", "First Post")
            .setHint("jakarta.persistence.fetchgraph", parsedIntoGraph)
            .getSingleResult();
        entityManager.close();

        assertNotNull(parsedPost);
        assertEquals("First Post", parsedPost.getSubject());
        assertTrue(Hibernate.isInitialized(parsedPost.getUser()));
        assertTrue(Hibernate.isInitialized(parsedPost.getComments()));
        assertEquals(2, parsedPost.getComments().size());
        assertTrue(Hibernate.isInitialized(parsedPost.getComments().get(0).getUser()));
        assertTrue(Hibernate.isInitialized(parsedPost.getComments().get(1).getUser()));

        assertNotNull(parsedIntoPost);
        assertEquals("First Post", parsedIntoPost.getSubject());
        assertTrue(Hibernate.isInitialized(parsedIntoPost.getUser()));
        assertTrue(Hibernate.isInitialized(parsedIntoPost.getComments()));
        assertEquals(2, parsedIntoPost.getComments().size());
        assertTrue(Hibernate.isInitialized(parsedIntoPost.getComments().get(0).getUser()));
        assertTrue(Hibernate.isInitialized(parsedIntoPost.getComments().get(1).getUser()));
    }

    @Test
    void whenMergingGraphs_thenUnionOfAttributesIsLoaded() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityGraph<Post> basicGraph = (EntityGraph<Post>) entityManager.getEntityGraph("post-basic");
        EntityGraph<Post> postWithCommentUsersGraph = (EntityGraph<Post>) entityManager.getEntityGraph("post-with-comment-users");
        EntityGraph<Post> mergedGraph = EntityGraphs.merge(entityManager, Post.class,
            basicGraph,
            postWithCommentUsersGraph);
        Post post = entityManager.createQuery("Select p from Post p where p.subject = :subject", Post.class)
            .setParameter("subject", "First Post")
            .setHint("jakarta.persistence.fetchgraph", mergedGraph)
            .getSingleResult();
        entityManager.close();

        assertNotNull(post);
        assertEquals("First Post", post.getSubject());
        assertTrue(Hibernate.isInitialized(post.getUser()));
        assertTrue(Hibernate.isInitialized(post.getComments()));
        assertEquals(2, post.getComments().size());
        assertTrue(Hibernate.isInitialized(post.getComments().get(0).getUser()));
        assertTrue(Hibernate.isInitialized(post.getComments().get(1).getUser()));
    }
}
