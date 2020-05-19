package com.baeldung.architecture.hexagonal.port;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CreatePostRequest {
    private final String title;
    private final String content;

    @JsonCreator
    public CreatePostRequest(
      @JsonProperty("title") @NotNull String title,
      @JsonProperty("content") @NotNull String content
    ) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
