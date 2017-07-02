package com.baeldung.lombok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class GreetingService {

    @Autowired
    private Translator translator;

    public String greet() {
        return translator.translate("hello");
    }
}
