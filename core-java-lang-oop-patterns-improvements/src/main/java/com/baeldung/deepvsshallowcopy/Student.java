package com.baeldung.deepvsshallowcopy;

public class Student {
	
	
	private String firstName;
	private String lastName;
	private int age;
	private Program program;
	
	
	public Student() {
		// TODO Auto-generated constructor stub
	}


	public Student(String firstName, String lastName,int age, Program program) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.program = program;
		this.age = age;
	}
	
	public Student(Student student) {
	    this(student.getFirstName(),student.getLastName(), student.getAge(),new Program(student.getProgram()));
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Program getProgram() {
		return program;
	}


	public void setProgram(Program program) {
		this.program = program;
	}
	
	




	
}
