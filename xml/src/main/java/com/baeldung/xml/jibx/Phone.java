package com.baeldung.xml.jibx;

public class Phone {

	private String countryCode;
	private String networkPrefix;
	private String number;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getNetworkPrefix() {
		return networkPrefix;
	}

	public void setNetworkPrefix(String networkPrefix) {
		this.networkPrefix = networkPrefix;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
