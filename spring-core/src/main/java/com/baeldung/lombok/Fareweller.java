package com.baeldung.lombok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Fareweller {

    private final Translator translator;

    public Fareweller(Translator translator) {
        this.translator = translator;
    }

    public String farewell() {
        return translator.translate("bye");
    }
}
