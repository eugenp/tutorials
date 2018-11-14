package com.baeldung.cdi.cdi2observers.services;

import javax.enterprise.inject.Alternative;

@Alternative
public class LowercaseTextService implements TextService {
    
    @Override
    public String parseText(String text) {
        return text.toLowerCase();
    }
}
