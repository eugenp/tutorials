package com.baeldung.di.types;

public class CodeEditor {

    private Formatter formatter;
    
    public CodeEditor() {
        
    }

    public CodeEditor(Formatter formatter) {
        this.formatter = formatter;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }
    
}
