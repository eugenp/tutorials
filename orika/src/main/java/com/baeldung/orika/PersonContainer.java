package com.baeldung.orika;

public class PersonContainer {
	private Name name;

	public PersonContainer(Name name) {
		this.name = name;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

}
