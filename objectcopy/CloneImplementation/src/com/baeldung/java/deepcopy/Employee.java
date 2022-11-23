package com.baeldung.java.deepcopy;

public class Employee implements Cloneable {
	
	public int e_id;
	public String name;
	public Role role;
	
	Employee(int e_id, String name, Role role) {
		
		this.e_id = e_id;
		this.name = name;
		this.role = role;
	}
	
	protected Object clone() throws CloneNotSupportedException {
		
		Employee e1 = (Employee)super.clone();
		e1.role = (Role)role.clone();
		return e1;
	}
	
}
