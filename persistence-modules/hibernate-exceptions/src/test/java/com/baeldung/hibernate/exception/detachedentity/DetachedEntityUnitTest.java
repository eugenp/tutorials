package com.baeldung.hibernate.exception.detachedentity;

import com.baeldung.hibernate.exception.detachedentity.entity.Comment;
import com.baeldung.hibernate.exception.detachedentity.entity.Post;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.PersistenceException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DetachedEntityUnitTest {

    private static Session session;
    private Post detachedPost;

    @Before
    public void beforeEach() {
        session = HibernateUtil.getSessionFactory()
            .openSession();
        session.beginTransaction();
        this.detachedPost = new Post("Hibernate Tutorial");
        session.persist(detachedPost);
        session.evict(detachedPost);
    }

    @After
    public void afterEach() {
        clearDatabase();
        session.close();
    }

    @Test
    public void givenDetachedPost_whenTryingToPersist_thenThrowException() {
        detachedPost.setTitle("Hibernate Tutorial for Absolute Beginners");

        assertThatThrownBy(() -> session.persist(detachedPost))
            .isInstanceOf(PersistenceException.class)
            .hasMessageContaining("detached entity passed to persist: com.baeldung.hibernate.exception.detachedentity.entity.Post");
    }

    @Test
    public void givenDetachedPost_whenTryingToMerge_thenNoExceptionIsThrown() {
        detachedPost.setTitle("Hibernate Tutorial for Beginners");

        session.merge(detachedPost);
        session.getTransaction()
            .commit();

        List<Post> posts = session.createQuery("Select p from Post p", Post.class)
            .list();
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0)
            .getTitle()).isEqualTo("Hibernate Tutorial for Beginners");
    }

    @Test
    public void givenDetachedPost_whenPersistingNewCommentWithIt_thenThrowException() {
        Comment comment = new Comment("nice article!");
        comment.setPost(detachedPost);

        session.persist(comment);
        session.getTransaction()
            .commit();

        assertThatThrownBy(() -> session.persist(detachedPost))
            .isInstanceOf(PersistenceException.class)
            .hasMessageContaining("detached entity passed to persist: com.baeldung.hibernate.exception.detachedentity.entity.Post");
    }

    @Test
    public void givenDetachedPost_whenMergeAndPersistComment_thenNoExceptionIsThrown() {
        Comment comment = new Comment("nice article!");
        Post mergedPost = session.merge(detachedPost);
        comment.setPost(mergedPost);

        session.persist(comment);
        session.getTransaction()
            .commit();

        List<Comment> comments = session.createQuery("Select c from Comment c", Comment.class)
            .list();
        Comment savedComment = comments.get(0);
        assertThat(savedComment.getText()).isEqualTo("nice article!");
        assertThat(savedComment.getPost()
            .getTitle()).isEqualTo("Hibernate Tutorial");
    }

    private void clearDatabase() {
        if (!session.getTransaction()
            .isActive()) {
            session.beginTransaction();
        }
        session.createQuery("DELETE FROM Comment")
            .executeUpdate();
        session.createQuery("DELETE FROM Post")
            .executeUpdate();
    }
}
