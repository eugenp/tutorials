package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.model.Post;
import com.baeldung.hexagonal.architecture.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Optional<Post> createNewPost(String message) {
        Post post = new Post();
        post.setId(UUID.randomUUID().toString());
        post.setMessage(message);
        final Post savedPost = postRepository.save(post);
        return Optional.ofNullable(savedPost);
    }

    public Optional<Post> getPostById(String postId) {
        return postRepository.findById(postId);
    }
}
