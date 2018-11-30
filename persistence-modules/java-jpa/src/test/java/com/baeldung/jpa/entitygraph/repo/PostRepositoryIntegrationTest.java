package com.baeldung.jpa.entitygraph.repo;

import com.baeldung.jpa.entitygraph.model.Comment;
import com.baeldung.jpa.entitygraph.model.Post;
import com.baeldung.jpa.entitygraph.model.User;
import org.hibernate.LazyInitializationException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostRepositoryIntegrationTest {

    private static PostRepository postRepository = null;

    @BeforeClass
    public static void once() {
        postRepository = new PostRepository();
    }

    @Test(expected = LazyInitializationException.class)
    public void find() {
        Post post = postRepository.find(1L);
        assertNotNull(post.getUser());
        String email = post.getUser().getEmail();
        assertNull(email);
    }

    @Test
    public void findWithEntityGraph() {
        Post post = postRepository.findWithEntityGraph(1L);
        assertNotNull(post.getUser());
        String email = post.getUser().getEmail();
        assertNotNull(email);
    }

    @Test(expected = LazyInitializationException.class)
    public void findWithEntityGraph_Comment_Without_User() {
        Post post = postRepository.findWithEntityGraph(1L);
        assertNotNull(post.getUser());
        String email = post.getUser().getEmail();
        assertNotNull(email);
        assertNotNull(post.getComments());
        assertEquals(post.getComments().size(), 2);
        Comment comment = post.getComments().get(0);
        assertNotNull(comment);
        User user = comment.getUser();
        user.getEmail();
    }

    @Test
    public void findWithEntityGraph2_Comment_With_User() {
        Post post = postRepository.findWithEntityGraph2(1L);
        assertNotNull(post.getComments());
        assertEquals(post.getComments().size(), 2);
        Comment comment = post.getComments().get(0);
        assertNotNull(comment);
        User user = comment.getUser();
        assertNotNull(user);
        assertEquals(user.getEmail(), "user2@test.com");
    }

    @AfterClass
    public static void destroy() {
        postRepository.clean();
    }
}
