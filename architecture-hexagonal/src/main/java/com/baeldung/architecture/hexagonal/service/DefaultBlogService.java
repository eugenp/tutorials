package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.model.Blog;
import com.baeldung.architecture.hexagonal.port.BlogRepositoryPort;

import java.util.List;

public class DefaultBlogService implements BlogService {

    private final BlogRepositoryPort blogRepository;

    public DefaultBlogService(BlogRepositoryPort blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void createBlog(Blog blog) {
        this.blogRepository.createBlog(blog);
    }

    @Override
    public Blog findByName(String name) {
        return this.blogRepository.findByName(name);
    }

    @Override
    public List<Blog> findAll() {
        return this.blogRepository.findAll();
    }
}
