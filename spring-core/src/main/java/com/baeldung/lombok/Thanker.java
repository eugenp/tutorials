package com.baeldung.lombok;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class Thanker {

    private final Translator translator;

    public String thank() {
        return translator.translate("thank you");
    }
}
