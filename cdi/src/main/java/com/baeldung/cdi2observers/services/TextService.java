package com.baeldung.cdi.cdi2observers.services;

public class TextService {

    @Override
    public String parseText(String text) {
        return text.toUpperCase();
    }
}
