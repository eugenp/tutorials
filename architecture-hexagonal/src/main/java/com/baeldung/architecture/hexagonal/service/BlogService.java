package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.model.Blog;

import java.util.List;

public interface BlogService {

    void createBlog(Blog blog);
    Blog findByName(String name);
    List<Blog> findAll();
}
