package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class ArticleWithConstructorInjection {

	private TextFormatter formatter;

	@Autowired
    public ArticleWithConstructorInjection(TextFormatter formatter) {
        this.formatter = formatter;
    }

    public String format(String text) {
        return formatter.format(text);
    }
}
