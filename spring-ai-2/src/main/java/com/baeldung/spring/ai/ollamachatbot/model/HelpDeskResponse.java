package com.baeldung.spring.ai.ollamachatbot.model;

public class HelpDeskResponse {
    String result;
    public HelpDeskResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
