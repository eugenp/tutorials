package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class ArticleWithSetterInjection {

    private TextFormatter formatter;

    public ArticleWithSetterInjection(TextFormatter formatter) {
        this.formatter = formatter;
    }

    @Autowired
    public void setTextFormatter(TextFormatter formatter) {
        this.formatter = formatter;
    }
    
    public String format(String text) {
        return formatter.format(text);
    }
}
