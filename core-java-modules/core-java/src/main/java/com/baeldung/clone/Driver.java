package com.baeldung.clone;

public class Driver implements Cloneable {
	private int id;
	private Name name;
	private String licenseId;
	
	public Driver(int id, Name name, String licenseId) {
		this.id = id;
		this.name = name;
		this.licenseId = licenseId;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public String getLicenseId() {
		return licenseId;
	}
	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}
}
