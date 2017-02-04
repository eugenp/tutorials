package com.baeldung.lombok;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ThanksProducer {

    private final Translator translator;

    public String produce() {
        return translator.translate("produce you");
    }
}
