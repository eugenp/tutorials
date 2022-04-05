package com.baeldung.orika;

import java.util.List;

public class PersonListContainer {
	private List<Name> names;

	public PersonListContainer(List<Name> names) {
		this.names = names;
	}

	public List<Name> getNames() {
		return names;
	}

	public void setNames(List<Name> names) {
		this.names = names;
	}

}
