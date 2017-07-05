package com.baeldung.services;

public class TextPrinter {

    private TextService textService;

    public TextPrinter(TextService textService) {
        this.textService = textService;
    }

    public void printText(String text) {
        System.out.println(textService.processText(text));
    }
}

