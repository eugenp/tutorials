package com.baeldung.hibernate.transaction;

import com.baeldung.hibernate.transaction.dao.PostRepository;
import com.baeldung.hibernate.transaction.models.Post;
import com.baeldung.hibernate.transaction.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = {TransactionApplication.class})
@RunWith(SpringRunner.class)
public class TransactionIntegrationTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;

    @Test
    public void givenTitleAndBody_whenRepositoryUpdatePost_thenUpdatePost() {
        Post post = new Post("This is a title", "This is a sample post");
        post = postRepository.save(post);
        Post fetchedPost = postRepository.findById(post.getId()).orElse(null);
        assertNotNull(fetchedPost);


        String title = "[UPDATE] Java HowTos";
        String body = "This is an updated posts on Java how-tos";
        postService.updatePost(post.getId(), title, body);

        Post fetchedPost2 = postRepository.findById(post.getId()).orElse(null);
        assertNotNull(fetchedPost);
        assertEquals(fetchedPost2.getTitle(), title);
        assertEquals(fetchedPost2.getBody(), body);
    }


    @Test
    public void givenTitleAndBody_whenUpdatePostWithTransactionTemplate_thenUpdatePost() {
        Post post = new Post("This is a title", "This is a sample post");
        post = postRepository.save(post);
        List<Post> posts = (List<Post>) postRepository.findAll();
        assertFalse(posts.isEmpty());

        String title = "[UPDATE] Java HowTos";
        String body = "This is an updated posts on Java how-tos";

        postService.updateWithTransactionTemplate(post.getId(), title, body);

        Post fetchedPost = postRepository.findById(post.getId()).orElse(null);
        assertNotNull(fetchedPost);
        assertEquals(fetchedPost.getTitle(), title);
        assertEquals(fetchedPost.getBody(), body);
    }


}
