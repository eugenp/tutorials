package com.baeldung.bean.inject.boot;

public class VideoPlayer {

	private String brandName;
	private String manufacturer;

	public VideoPlayer(String brandName, String manufacturer) {
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
