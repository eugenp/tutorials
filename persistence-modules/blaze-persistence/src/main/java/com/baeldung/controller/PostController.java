package com.baeldung.controller;

import com.baeldung.repository.PostRepository;
import com.baeldung.repository.PostViewRepository;
import com.baeldung.view.PostWithAuthorView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostViewRepository postViewRepository;

    private final PostRepository postRepository;

    public PostController(PostViewRepository postViewRepository, PostRepository postRepository) {
        this.postViewRepository = postViewRepository;
        this.postRepository = postRepository;
    }

    @GetMapping
    Iterable<PostWithAuthorView> findAll() {
        return postViewRepository.findAll();
    }

    @GetMapping("/post")
    Iterable<PostWithAuthorView> find(@RequestParam("title") final String title,
                                      @RequestParam("authorName") final String authorName) {
        return postRepository.findBy(title, authorName);
    }

}
