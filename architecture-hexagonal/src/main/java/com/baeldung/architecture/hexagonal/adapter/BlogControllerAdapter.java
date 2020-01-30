package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.model.Blog;
import com.baeldung.architecture.hexagonal.service.BlogService;
import com.baeldung.architecture.hexagonal.port.BlogUIPort;

import java.util.List;

public class BlogControllerAdapter implements BlogUIPort {

    private final BlogService blogService;

    public BlogControllerAdapter(BlogService blogService) {
        this.blogService = blogService;
    }

    @Override
    public void createBlog(Blog blog) {
        this.blogService.createBlog(blog);
    }

    @Override
    public Blog findByName(String name) {
        return this.blogService.findByName(name);
    }

    @Override
    public List<Blog> findAll() {
        return this.blogService.findAll();
    }
}
