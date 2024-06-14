package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.persistence.repository.CommentRepository;

public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }
}
