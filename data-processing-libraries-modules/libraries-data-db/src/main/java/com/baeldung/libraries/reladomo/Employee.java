package com.baeldung.libraries.reladomo;
public class Employee extends EmployeeAbstract
{
	public Employee()
	{
		super();
		// You must not modify this constructor. Mithra calls this internally.
		// You can call this constructor. You can also add new constructors.
	}
	
	public Employee(long id, String name){
	    super();
	    this.setId(id);
	    this.setName(name);
	}
}
