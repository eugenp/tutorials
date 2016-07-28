package com.baeldung.beans;

public class Employee implements Comparable{
	private String name;
	private int age;
	private double salary;
	
	public Employee() {
		
	}
	
	public Employee(String name, int age, double salary) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "("+name+","+age+","+salary+")";
		
	}

	@Override
	public int compareTo(Object o) {
		Employee e = (Employee) o;
		return getAge()  - e.getAge() ;
	}
}
