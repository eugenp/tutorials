package com.baeldung.lombok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GreetingProducer {

    @Autowired
    private Translator translator;

    public String produce() {
        return translator.translate("hello");
    }
}
