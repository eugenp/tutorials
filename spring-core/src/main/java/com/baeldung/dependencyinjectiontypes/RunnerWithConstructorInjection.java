package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class RunnerWithConstructorInjection {

	private Formatter formatter;

	@Autowired
    public RunnerWithConstructorInjection(Formatter formatter) {
        this.formatter = formatter;
    }

    public String format(String text) {
        return formatter.format(text);
    }
}
