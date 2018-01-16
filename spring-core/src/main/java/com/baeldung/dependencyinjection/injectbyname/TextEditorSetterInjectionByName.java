package com.baeldung.dependencyinjection.injectbyname;

import com.baeldung.dependencyinjection.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TextEditorSetterInjectionByName {

    private Dictionary dictionary;

    public Dictionary getDictionary() {
        return dictionary;
    }

    @Autowired
    @Qualifier("frenchDictionary")
    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}
