package com.baeldung.spring.ai.ollamachatbot.model;

public class ChatBotRequest {

    String promptMessage;

    public ChatBotRequest(String promptMessage) {
        this.promptMessage = promptMessage;
    }

    public String getPromptMessage() {
        return promptMessage;
    }

    public void setPromptMessage(String promptMessage) {
        this.promptMessage = promptMessage;
    }
}
