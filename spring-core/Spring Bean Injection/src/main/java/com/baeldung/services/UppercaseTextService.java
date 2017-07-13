package com.baeldung.services;

public class UppercaseTextService implements TextService {
    public String processText(String text) {
        return text.toUpperCase();
    }
}
