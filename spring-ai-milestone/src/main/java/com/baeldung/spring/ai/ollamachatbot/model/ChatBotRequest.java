package com.baeldung.spring.ai.ollamachatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChatBotRequest {

    @JsonProperty("prompt_message")
    String promptMessage;

    @JsonProperty("context_id")
    String contextId;

    public ChatBotRequest(String promptMessage) {
        this.promptMessage = promptMessage;
    }

    public String getPromptMessage() {
        return promptMessage;
    }

    public void setPromptMessage(String promptMessage) {
        this.promptMessage = promptMessage;
    }

    public String getContextId() {
        return contextId;
    }
}
