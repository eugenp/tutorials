package com.baeldung.dependencyinjections;

public class TelephoneDirectory {

	private CellPhone cellPhone;

	public void setCellPhone(CellPhone cellPhone) {
		this.cellPhone = cellPhone;
	}

	public void displayPhoneDetails() {
		System.out.println(cellPhone);
	}
}
