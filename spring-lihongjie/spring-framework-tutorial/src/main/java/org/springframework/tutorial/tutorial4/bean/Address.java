package org.springframework.tutorial.tutorial4.bean;

public class Address {

	private String city = "Varanasi";

	public Address(String city) {
		this.city = city;
		System.out.println("My city:" + city);
	}

	public String getCity() {
		return city;
	}

}
