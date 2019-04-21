package com.baeldung.modelmapper.service;

import java.util.List;

import com.baeldung.modelmapper.model.Post;

public interface IPostService {

    List<Post> getPostsList(int page, int size, String sortDir, String sort);

    void updatePost(Post post);

    Post createPost(Post post);

    Post getPostById(Long id);

}
