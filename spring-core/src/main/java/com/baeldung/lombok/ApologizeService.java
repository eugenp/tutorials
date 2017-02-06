package com.baeldung.lombok;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApologizeService {

    private final Translator translator;
    private final String message;

    @Autowired
    public ApologizeService(Translator translator) {
        this(translator, "sorry");
    }

    public String apologize() {
        return translator.translate(message);
    }
}
