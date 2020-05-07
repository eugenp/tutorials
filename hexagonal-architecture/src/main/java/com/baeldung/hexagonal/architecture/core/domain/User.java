package com.baeldung.hexagonal.architecture.core.domain;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -8047226258524627280L;

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
