package com.baeldung.hexagonal.architecture.port;

import com.baeldung.hexagonal.architecture.model.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface PostRestApiPort {
    @PostMapping("/new")
    Optional<Post> newPost(@RequestBody Post post);

    @GetMapping("/fetch/{postId}")
    Optional<Post> fetchPost(@PathVariable("postId") String postId);
}
