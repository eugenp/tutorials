package com.baeldung.core.domain;

import java.io.Serializable;

public class Coffee implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String type;

	public Coffee(String name) {
		super();
		this.name = name;
	}
	
	public Coffee(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
