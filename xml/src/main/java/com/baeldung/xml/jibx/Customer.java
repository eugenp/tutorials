/*
 * Customer.java     06.06.2008
 *
 * Copyright 2008 Stefan J&auml;ger
 *
 */
package com.baeldung.xml.jibx;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Customer {
	private Person person;
	private String city;

	private Phone homePhone;
	private Phone officePhone;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Phone getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(Phone homePhone) {
		this.homePhone = homePhone;
	}

	public Phone getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(Phone officePhone) {
		this.officePhone = officePhone;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
