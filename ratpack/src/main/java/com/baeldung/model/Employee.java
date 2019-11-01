package com.baeldung.model;

import java.io.Serializable;

public class Employee implements Serializable {

	private static final long serialVersionUID = 3077867088762010705L;
	
	private Long id;
	private String title;
	private String name;

	public Employee(Long id, String title, String name) {
		super();
		this.id = id;
		this.title = title;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
