package org.baeldung.JPAAuditDemo;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.baeldung.JPAAuditDemo.listener.LogListener;
import org.baeldung.JPAAuditDemo.model.Post;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Illustrates the use of entity's lifecycle callbacks.
 */
public class JPAAuditTest {

    private static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("entityLifecyclePU");
    }

    @AfterClass
    public static void close() {
        entityManagerFactory.close();
    }

    /**
     * Persists entity {@link Post} generating pre-persist and post-persist events which are handled by our {@link LogListener}.<br>
     * Updates entity {@link Post} generating pre-update and post-update events which are handled by our {@link LogListener}.<br>
     */
    @Test
    public void testLifecycleCallbacks() {
        // test @PrePersist and @PostPersist methods
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Post post = new Post(1, "JPA auditing");
        entityManager.persist(post);

        entityManager.getTransaction().commit();
        entityManager.close();

        // test @PreUpdate and @PostUpdate methods
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        post = entityManager.find(Post.class, 1);
        post.setTitle("JPA auditing finally");
        Calendar createdDate = post.getCreatedDate();
        createdDate.add(Calendar.WEEK_OF_MONTH, 2);
        post.setCreatedDate(createdDate);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
