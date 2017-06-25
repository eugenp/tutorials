package com.baeldung.commons.beanutils;

import java.util.List;

public class CourseEntity {
	private String name;
    private List<String> codes;
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getCodes() {
		return codes;
	}
	
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}
}
