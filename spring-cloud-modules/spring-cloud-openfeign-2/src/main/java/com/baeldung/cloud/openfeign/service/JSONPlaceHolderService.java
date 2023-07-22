package com.baeldung.cloud.openfeign.service;

import java.util.List;

import com.baeldung.cloud.openfeign.model.Post;

public interface JSONPlaceHolderService {

    List<Post> getPosts();

    Post getPostById(Long id);
}
