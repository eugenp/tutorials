package com.baeldung.ollamachatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelpDeskRequest {
    @JsonProperty("prompt_message")
    String promptMessage;

    @JsonProperty("history_id")
    String historyId;

    public HelpDeskRequest(String promptMessage, String historyId) {
        this.promptMessage = promptMessage;
        this.historyId = historyId;
    }

    public String getPromptMessage() {
        return promptMessage;
    }

    public String getHistoryId() {
        return historyId;
    }
}
