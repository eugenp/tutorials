package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.model.Blog;
import com.baeldung.architecture.hexagonal.port.BlogRepositoryPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogServiceAdapter implements BlogRepositoryPort {

    private final Map<String, Blog> blogs = new HashMap<>();

    @Override
    public void createBlog(Blog blog) {
        this.blogs.put(blog.getName(), blog);
    }

    @Override
    public Blog findByName(String name) {
        return this.blogs.get(name);
    }

    @Override
    public List<Blog> findAll() {
        return new ArrayList<>(this.blogs.values());
    }
}
