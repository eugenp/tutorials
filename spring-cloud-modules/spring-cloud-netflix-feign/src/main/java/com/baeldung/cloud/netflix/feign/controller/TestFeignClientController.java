package com.baeldung.cloud.netflix.feign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.cloud.netflix.feign.model.Post;
import com.baeldung.cloud.netflix.feign.service.JSONPlaceHolderService;

@RestController
@RequestMapping("/posts")
public class TestFeignClientController {
    @Autowired
    private JSONPlaceHolderService jsonPlaceHolderService;

    @GetMapping
    public List<Post> getAllPosts() { return jsonPlaceHolderService.getPosts(); }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable Long postId) { return jsonPlaceHolderService.getPostById(postId); }
}
