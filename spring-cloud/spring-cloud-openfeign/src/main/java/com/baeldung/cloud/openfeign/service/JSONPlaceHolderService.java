package com.baeldung.cloud.openfeign.service;

import com.baeldung.cloud.openfeign.model.Post;

import java.util.List;

public interface JSONPlaceHolderService {

    List<Post> getPosts();

    Post getPostById(Long id);
}
