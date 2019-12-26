package com.baeldung.servicemodule.internal;

import com.baeldung.servicemodule.external.TextService;

public class LowercaseTextService implements TextService {

    @Override
    public String processText(String text) {
        return text.toLowerCase();
    }
    
}
