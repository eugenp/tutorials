package com.baeldung.cloud.openfeign;

import com.baeldung.cloud.openfeign.model.Post;
import com.baeldung.cloud.openfeign.service.JSONPlaceHolderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenfeignManualTest {

    @Autowired
    private JSONPlaceHolderService jsonPlaceHolderService;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }

    @Test
    public void whenGetPosts_thenListPostSizeGreaterThanZero() {

        List<Post> posts = jsonPlaceHolderService.getPosts();

        assertFalse(posts.isEmpty());
    }

    @Test
    public void whenGetPostWithId_thenPostExist() {

        Post post = jsonPlaceHolderService.getPostById(1L);

        assertNotNull(post);
    }

}
