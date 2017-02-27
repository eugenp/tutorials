package com.baeldung.lombok;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ThankingService {

    private final Translator translator;

    public String thank() {
        return translator.translate("thank you");
    }
}
