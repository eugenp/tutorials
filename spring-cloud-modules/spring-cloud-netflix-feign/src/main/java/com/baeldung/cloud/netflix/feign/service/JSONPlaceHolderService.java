package com.baeldung.cloud.netflix.feign.service;

import com.baeldung.cloud.netflix.feign.model.Post;

import java.util.List;

public interface JSONPlaceHolderService {

    List<Post> getPosts();

    Post getPostById(Long id);
}
