package com.baeldung.spring_security.dto.request;

public class PostRequestDto {
    private String title;
    private String content;

    public PostRequestDto() {
    }

    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
