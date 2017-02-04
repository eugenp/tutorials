package com.baeldung.lombok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FarewellProducer {

    private final Translator translator;

    public FarewellProducer(Translator translator) {
        this.translator = translator;
    }

    public String produce() {
        return translator.translate("bye");
    }
}
