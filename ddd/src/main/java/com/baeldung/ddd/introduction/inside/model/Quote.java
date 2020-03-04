package com.baeldung.ddd.introduction.inside.model;

import javax.validation.constraints.NotNull;

public class Quote {
	@NotNull
	private String text;
	@NotNull
	private String name;

	public Quote(String text, String name) {
		this.text = text;
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public String getName() {
		return name;
	}
}
