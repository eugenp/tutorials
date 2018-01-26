package com.baeldung.di.types.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.di.types.Formatter;

@Component
public class CodeEditorAutowiredByConstructor {

    private Formatter formatter;

    @Autowired
    public CodeEditorAutowiredByConstructor(Formatter formatter) {
        this.formatter = formatter;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }
    
}
