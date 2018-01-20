package com.baeldung.bean.inject.boot;

public class AudioSystem {

	private String brandName;
	private String manufacturer;

	public AudioSystem(String brandName, String manufacturer) {
		this.brandName = brandName;
		this.manufacturer = manufacturer;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

}
