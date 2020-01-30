package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.model.Blog;
import com.baeldung.architecture.hexagonal.port.BlogRepositoryPort;

import java.util.List;

public class BlogService {

    private final BlogRepositoryPort blogRepository;

    public BlogService(BlogRepositoryPort blogRepository) {
        this.blogRepository = blogRepository;
    }

    public void createBlog(Blog blog) {
        this.blogRepository.createBlog(blog);
    }

    public Blog findByName(String name) {
        return this.blogRepository.findByName(name);
    }

    public List<Blog> findAll() {
        return this.blogRepository.findAll();
    }
}
