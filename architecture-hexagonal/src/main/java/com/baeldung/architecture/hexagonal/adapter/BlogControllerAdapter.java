package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.model.Blog;
import com.baeldung.architecture.hexagonal.service.BlogService;

import java.util.List;

public class BlogControllerAdapter {

    private final BlogService blogService;

    public BlogControllerAdapter(BlogService blogService) {
        this.blogService = blogService;
    }

    public void createBlog(Blog blog) {
        this.blogService.createBlog(blog);
    }

    public Blog findByName(String name) {
        return this.blogService.findByName(name);
    }

    public List<Blog> findAll() {
        return this.blogService.findAll();
    }
}
