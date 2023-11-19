package com.baeldung.providermodule;

import com.baeldung.servicemodule.TextService;

public class LowercaseTextService implements TextService {
    @Override
    public String parseText(String text) {
        return text.toLowerCase();
    }
}
