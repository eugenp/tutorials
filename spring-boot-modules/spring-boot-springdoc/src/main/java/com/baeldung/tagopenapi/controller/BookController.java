package com.baeldung.tagopenapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@Tag(name = "book service", description = "the book API with description tag annotation")
public class BookController {

    @GetMapping("/")
    public List<String> getBooks() {
        return Arrays.asList("book1", "book2");
    }
}
