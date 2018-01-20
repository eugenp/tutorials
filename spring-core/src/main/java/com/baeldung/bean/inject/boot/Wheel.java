package com.baeldung.bean.inject.boot;

public class Wheel {
	private String type;
	private String manufacturer;

	public Wheel(String type, String manufacturer) {
		this.type = type;
		this.manufacturer = manufacturer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}