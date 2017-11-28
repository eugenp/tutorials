package com.baeldung.lombok;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApologizeService {

    private Translator translator;
    private String message;

    @Autowired
    public ApologizeService(Translator translator, String message) {
        this.translator = translator;
        this.message = message;
    }

    public String apologize() {
        return translator.translate(message);
    }
}
