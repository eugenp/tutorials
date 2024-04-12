package com.baeldung.javaxval.hibernate.validator.ap;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.util.List;
import java.util.Optional;



public class Message {

    @NotNull(message = "Content cannot be null")
    private String content;

    private boolean isDelivered;

    private List<@NotBlank String> recipients;

    // uncomment in order to trigger AP annotation detection
    // The annotation @Past is disallowed for this data type.
    // @Past
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

    // uncomment in order to trigger AP annotation detection
    // The annotation @Min is disallowed for the return type of this method.
    // @Min(3)
    public boolean broadcast() {
        // setup a logic
        // to send to recipients
        return true;
    }

    // uncomment in order to trigger AP annotation detection
    // Void methods may not be annotated with constraint annotations.
    // @NotNull
    public void archive() {
        // archive the message
    }

    // uncomment in order to trigger AP annotation detection
    // Constraint annotations must not be specified at methods, which are no valid JavaBeans getter methods.
    // NOTE: add <arg>-AmethodConstraintsSupported=false</arg> to compiler args before
    // @AssertTrue
    public boolean delete() {
        // delete the message
        return false;
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
