package com.baeldung.services;

public class LowercaseTextService implements TextService {
    public String processText(String text) {
        return text.toLowerCase();
    }
}
