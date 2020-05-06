package com.baeldung.hexagonal.architecture.core.domain;

import java.io.Serializable;

public class User implements Serializable {

	Long id;

	String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
