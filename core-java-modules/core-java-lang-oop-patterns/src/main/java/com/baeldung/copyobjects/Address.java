package com.baeldung.copyobjects;

public class Address implements Cloneable{
	String houseNum;
	String street;
	String city;
	String state;

	public Address(String houseNum, String street, String city, String state) {
		super();
		this.houseNum = houseNum;
		this.street = street;
		this.city = city;
		this.state = state;
	}

	public String getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// standard getters and setters
	
	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
