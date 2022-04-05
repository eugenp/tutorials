package com.baeldung.lombok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FarewellService {

    private final Translator translator;

    public FarewellService(Translator translator) {
        this.translator = translator;
    }

    public String farewell() {
        return translator.translate("bye");
    }
}
