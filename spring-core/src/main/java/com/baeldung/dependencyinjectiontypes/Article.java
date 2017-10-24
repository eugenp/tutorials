package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class Article {

    private TextFormatter formatter;

    public Article(TextFormatter formatter) {
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
