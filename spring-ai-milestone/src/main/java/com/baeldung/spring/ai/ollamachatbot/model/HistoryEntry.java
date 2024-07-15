package com.baeldung.spring.ai.ollamachatbot.model;

import java.util.Objects;

public class HistoryEntry {

    private String prompt;

    private String response;

    public HistoryEntry(String prompt, String response) {
        this.prompt = prompt;
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistoryEntry that = (HistoryEntry) o;
        return Objects.equals(prompt, that.prompt) && Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prompt, response);
    }

    @Override
    public String toString() {
        return String.format("""
                        `history_entry`:
                            `prompt`: %s
                        
                            `response`: %s
                        -----------------
                       
            """, prompt, response);
    }
}
