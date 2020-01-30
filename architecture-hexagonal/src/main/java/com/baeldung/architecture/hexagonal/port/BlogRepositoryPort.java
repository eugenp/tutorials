package com.baeldung.architecture.hexagonal.port;

import com.baeldung.architecture.hexagonal.model.Blog;

import java.util.List;

public interface BlogRepositoryPort {

    void createBlog(Blog blog);
    Blog findByName(String name);
    List<Blog> findAll();
}
