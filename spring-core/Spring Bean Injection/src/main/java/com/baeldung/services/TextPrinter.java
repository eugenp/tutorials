package com.baeldung.services;

import org.springframework.beans.factory.annotation.Autowired;

public class TextPrinter {

    private TextService lowercaseTextService;

    @Autowired
    public TextPrinter(TextService lowercaseTextService) {
        this.lowercaseTextService = lowercaseTextService;
    }

    public void printText(String text) {
        System.out.print(lowercaseTextService.processText(text));
    }
}
