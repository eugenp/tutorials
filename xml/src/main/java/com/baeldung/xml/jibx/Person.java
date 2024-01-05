/*
 * Person.java     06.06.2008
 *
 * Copyright 2008 Stefan J&auml;ger
 *
 */
package com.baeldung.xml.jibx;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Person extends Identity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
