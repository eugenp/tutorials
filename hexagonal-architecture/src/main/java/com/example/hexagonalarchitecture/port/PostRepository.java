package com.example.hexagonalarchitecture.port;

import com.example.hexagonalarchitecture.domain.Post;

import java.util.List;

public interface PostRepository {
    List<Post> getAll();
    Post getById(Integer id);
    Post create(Post post);
    Post update(Post post);
    void deleteById(Integer id);
}
