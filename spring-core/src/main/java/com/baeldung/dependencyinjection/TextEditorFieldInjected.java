package com.baeldung.dependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextEditorFieldInjected {

    @Autowired
    private Dictionary dictionary;

    public Dictionary getDictionary() {
        return dictionary;
    }
}
