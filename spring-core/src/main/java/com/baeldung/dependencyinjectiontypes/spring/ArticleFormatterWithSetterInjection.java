package com.baeldung.dependencyinjectiontypes.spring;

import org.springframework.beans.factory.annotation.Autowired;
import com.baeldung.dependencyinjectiontypes.TextFormatter;

public class ArticleFormatterWithSetterInjection {
	
	private TextFormatter formatter;

    public ArticleFormatterWithSetterInjection(TextFormatter formatter) {
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
