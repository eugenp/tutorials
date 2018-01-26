package com.baeldung.di.types.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.di.types.Formatter;

@Component
public class CodeEditorAutowiredBySetter {

    private Formatter formatter;

    public Formatter getFormatter() {
        return formatter;
    }

    @Autowired
    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }
    
}
