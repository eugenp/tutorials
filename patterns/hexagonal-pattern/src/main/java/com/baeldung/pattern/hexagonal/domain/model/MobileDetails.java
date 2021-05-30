package com.baeldung.pattern.hexagonal.domain.model;

public class MobileDetails {

	private int modelNumber;
	private String brandName;
	private String description;
	private double price;
	
	public int getModelNumber() {
		return modelNumber;
	}
	
	public void setModelNumber(int modelNumber) {
		this.modelNumber = modelNumber;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
}
