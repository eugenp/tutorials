package com.baeldung.javaxval.hibernate.validator.ap;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.List;
import java.util.Optional;

public class Message {

    @NotNull(message = "Content cannot be null")
    private String content;

    private boolean isDelivered;

    private List<@NotBlank String> recipients;

    @Past
    private String createdAt;

    public String getContent() {
        return content;
    }

    public Message(String content, boolean isDelivered, List<@NotBlank String> recipients, String createdAt) {
        this.content = content;
        this.isDelivered = isDelivered;
        this.recipients = recipients;
        this.createdAt = createdAt;
    }

    @AssertTrue
    public boolean broadcast() {
        // setup a logic
        // to send to recipients
        return true;
    }

    @Validated
    public void archive() {
        // archive the message
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return content;
    }

    public void setName(String content) {
        this.content = content;
    }

    public Optional<@Past String> getCreatedAt() {
        return Optional.ofNullable(createdAt);
    }

}
