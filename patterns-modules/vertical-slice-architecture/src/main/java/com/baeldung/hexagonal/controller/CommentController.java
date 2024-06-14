package com.baeldung.hexagonal.controller;

import com.baeldung.hexagonal.service.ArticleService;
import com.baeldung.hexagonal.service.CommentService;

public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }
}
