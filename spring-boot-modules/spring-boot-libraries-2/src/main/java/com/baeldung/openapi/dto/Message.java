package com.baeldung.openapi.dto;

public class Message {

    private String role;
    private String content;

    Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    Message() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}