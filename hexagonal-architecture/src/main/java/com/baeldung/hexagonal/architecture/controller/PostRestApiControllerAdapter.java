package com.baeldung.hexagonal.architecture.controller;

import com.baeldung.hexagonal.architecture.model.Post;
import com.baeldung.hexagonal.architecture.port.PostRestApiPort;
import com.baeldung.hexagonal.architecture.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostRestApiControllerAdapter implements PostRestApiPort {
    @Autowired
    private PostService postService;

    @Override
    public Optional<Post> newPost(Post post) {
        final Optional<Post> postOptional = postService.createNewPost(post.getMessage());
        return postOptional;
    }

    @Override
    public Optional<Post> fetchPost(String postId) {
        return postService.getPostById(postId);
    }
}
