package com.baeldung.di.constructor.model.autowire;

import org.springframework.stereotype.Component;

@Component
public class Memory {

	private Integer sizeInGb = 16;
	private String format = "DDR3";
	
	public Integer getSizeInGb() {
		return sizeInGb;
	}

	public String getFormat() {
		return format;
	}

}
