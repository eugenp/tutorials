package com.baeldung.dependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextEditorSetterInjected {

    private Dictionary dictionary;

    public Dictionary getDictionary() {
        return dictionary;
    }

    @Autowired
    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}
