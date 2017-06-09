package org.baeldung.dependency.injection;

public class Employee {
	private String name;
	private int age;
	private String designation;
	private Address address;
	
	public Employee(){
		
	}
	
	public Employee(Address address){
		this.address = address;
	}
	
	public Employee(String name, String designation){
		this.name = name;
		this.designation = designation;
	}
	
	public Employee(String name, int age){
		this.name = name;
		this.age = age;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
