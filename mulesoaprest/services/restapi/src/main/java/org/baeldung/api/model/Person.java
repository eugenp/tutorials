package org.baeldung.api.model;

public class Person {

	private String name;
	private Integer age;
	private String country;
	private String city;
	
	public Person(String name, Integer age, String country, String city){
		this.name = name;
		this.age = age;
		this.country = country;
		this.city = city;	
	}
	
	public String getName() {
		return name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}
