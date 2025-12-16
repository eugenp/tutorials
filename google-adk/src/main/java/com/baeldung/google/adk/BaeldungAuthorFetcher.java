package com.baeldung.google.adk;

import static com.google.adk.tools.Annotations.Schema;

public class BaeldungAuthorFetcher {

    @Schema(description = "Get Baeldung author details using an article title")
    public static Author fetch(String articleTitle) {
        return new Author("John Doe", "john.doe@baeldung.com");
    }

    record Author(String name, String emailId) {}
}