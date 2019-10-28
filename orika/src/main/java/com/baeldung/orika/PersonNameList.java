package com.baeldung.orika;

import java.util.List;

public class PersonNameList {
	private List<String> nameList;

	public PersonNameList(List<String> nameList) {
		this.nameList = nameList;
	}

	public List<String> getNameList() {
		return nameList;
	}

	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

}
