package com.baeldung.cloud.openfeign;

import com.baeldung.cloud.openfeign.client.JSONPlaceHolderClient;
import com.baeldung.cloud.openfeign.model.Post;
import com.baeldung.cloud.openfeign.service.JSONPlaceHolderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenfeignUnitTest {

    @Autowired
    private JSONPlaceHolderService jsonPlaceHolderService;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }

    @Test
    public void whenGetPosts_thenListPostSizeGreaterThanZero() {

        List<Post> posts = jsonPlaceHolderService.getPosts();

        assert posts.size() > 0;
    }

    @Test
    public void whenGetPostWithId_thenPostExist() {

        Post post = jsonPlaceHolderService.getPostById(1l);

        assert post != null;
    }

    @Test
    public void whenGetPostsWithWrongUrl_thenListPostSizeEqualsZero() {

        List<Post> posts = jsonPlaceHolderService.getPostsWrong();

        assert posts.size() == 0;
    }

}

