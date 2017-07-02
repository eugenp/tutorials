package com.baeldung.lombok;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ThankingService {

    private final Translator translator;

    public String thank() {
        return translator.translate("thank you");
    }
}
