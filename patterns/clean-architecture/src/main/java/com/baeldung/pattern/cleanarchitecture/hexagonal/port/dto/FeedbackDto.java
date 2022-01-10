package com.baeldung.pattern.cleanarchitecture.hexagonal.port.dto;

import com.baeldung.pattern.cleanarchitecture.hexagonal.business.model.Feedback;

import java.time.LocalDateTime;

public class FeedbackDto {

    private String content;
    private LocalDateTime createdAt;

    public FeedbackDto(Feedback feedback) {
        setContent(feedback.getContent());
        setCreatedAt(feedback.getCreatedTs());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FeedbackDto{");
        sb.append("content='").append(content).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
