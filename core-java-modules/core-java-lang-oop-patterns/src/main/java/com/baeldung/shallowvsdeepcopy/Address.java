package com.baeldung.shallowvsdeepcopy;

public class Address {
	private String houseNo;
	private String street;
	private String city;
	private String country;
	
	
	public Address(String houseNo, String street, String city, String country) {
		super();
		this.houseNo = houseNo;
		this.street = street;
		this.city = city;
		this.country = country;
	}
	
	public Address(Address that) {
	    this(that.getHouseNo(), that.getStreet(), that.getCity(), that.getCountry());
	}
	
	public String getHouseNo() {
		return houseNo;
	}
	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
