package com.baeldung.dependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextEditorConstructorInjected {

    private Dictionary dictionary;

    @Autowired
    public TextEditorConstructorInjected(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
}
