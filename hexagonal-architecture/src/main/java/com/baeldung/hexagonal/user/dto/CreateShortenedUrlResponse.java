package com.baeldung.hexagonal.user.dto;

public class CreateShortenedUrlResponse {
    private String code;

    public CreateShortenedUrlResponse() {
    }

    public CreateShortenedUrlResponse(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
