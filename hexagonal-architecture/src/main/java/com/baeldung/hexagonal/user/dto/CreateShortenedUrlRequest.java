package com.baeldung.hexagonal.user.dto;

public class CreateShortenedUrlRequest {

    private String url;

    public CreateShortenedUrlRequest() {
    }

    public CreateShortenedUrlRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
