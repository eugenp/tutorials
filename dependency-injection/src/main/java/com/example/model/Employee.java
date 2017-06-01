package com.example.model;

public class Employee {
	private String fname; 
	private String lname;
	private Address address;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(String fname, String lname) {
		super();
		this.fname = fname;
		this.lname = lname;
	}
	public Employee(String fname, String lname, Address address) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.address = address;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Employee [fname=" + fname + ", lname=" + lname + ", address=" + address + "]";
	}
	
	
	
}
