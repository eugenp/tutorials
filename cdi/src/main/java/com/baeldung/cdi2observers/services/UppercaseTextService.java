package com.baeldung.cdi.cdi2observers.services;

public class UppercaseTextService implements TextService {

    @Override
    public String parseText(String text) {
        return text.toUpperCase();
    }
}
