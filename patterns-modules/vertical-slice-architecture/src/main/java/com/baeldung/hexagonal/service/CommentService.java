package com.baeldung.hexagonal.service;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.persistence.repository.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }
}
