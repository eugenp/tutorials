package com.baeldung.apiswagger.controller;

import com.baeldung.apiswagger.config.SwaggerConfiguration;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@Api(tags = {SwaggerConfiguration.BOOK_TAG})
public class BookController {

    @GetMapping("/")
    public List<String> getBooks() {
        return Arrays.asList("book1", "book2");
    }
}