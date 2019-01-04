package com.baeldung.serialization;

public class Address {

	private int houseNumber;

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	@Override
	public String toString() {
		return "Address{" +
				"houseNumber=" + houseNumber +
				'}';
	}
}
