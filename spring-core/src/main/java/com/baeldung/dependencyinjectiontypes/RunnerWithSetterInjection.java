package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class RunnerWithSetterInjection {

	private Formatter formatter;
	
	@Autowired
	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}

	public String format(String text) {
		return formatter.format(text);
	}
}
