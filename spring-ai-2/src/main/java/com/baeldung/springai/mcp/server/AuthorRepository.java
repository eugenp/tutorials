package com.baeldung.springai.mcp.server;

import org.springframework.ai.tool.annotation.Tool;

import java.util.List;

class AuthorRepository {

    @Tool(description = "Get Baeldung author details using an article title")
    Author getAuthorByArticleTitle(String articleTitle) {
        return new Author("John Doe", "john.doe@baeldung.com");
    }

    @Tool(description = "Get highest rated Baeldung authors")
    List<Author> getTopAuthors() {
        return List.of(
            new Author("John Doe", "john.doe@baeldung.com"),
            new Author("Jane Doe", "jane.doe@baeldung.com")
        );
    }

    record Author(String name, String email) {
    }

}