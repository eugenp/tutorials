package com.baeldung.dependencyinjectiontypes.spring;

import org.springframework.beans.factory.annotation.Autowired;
import com.baeldung.dependencyinjectiontypes.TextFormatter;

public class ArticleFormatterWithConstructorInjection {

	private TextFormatter formatter;

	@Autowired
    public ArticleFormatterWithConstructorInjection(TextFormatter formatter) {
        this.formatter = formatter;
    }

    public String format(String text) {
        return formatter.format(text);
    }
}
