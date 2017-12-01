package com.baeldung.dependencyinjections;

public class CellPhone {

	private String number;
	private String provider;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "CellPhone Details "
				+ "[number=" + number + 
				", provider=" + provider + 
				"]";
	}

}
