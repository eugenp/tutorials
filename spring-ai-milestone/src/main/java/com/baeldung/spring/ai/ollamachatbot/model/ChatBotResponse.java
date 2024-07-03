package com.baeldung.spring.ai.ollamachatbot.model;

public class ChatBotResponse {

    String results;

    public ChatBotResponse(String results) {
        this.results = results;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
