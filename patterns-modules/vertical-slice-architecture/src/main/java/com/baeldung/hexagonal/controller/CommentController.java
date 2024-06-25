package com.baeldung.hexagonal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.service.ArticleService;
import com.baeldung.hexagonal.service.CommentService;

@RestController
@RequestMapping("comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }
}
