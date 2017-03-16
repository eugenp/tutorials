/*
 * Person.java     06.06.2008
 *
 * Copyright 2008 Stefan J&auml;ger
 *
 */
package com.baeldung.xml.jibx;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Person extends Identity {
	private String firstName;
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
