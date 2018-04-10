package com.springWebClient.SpringWebClienteventStream;


public class Student {
	
	private static int autoIncrementID;
	private int id;
	private String name;
	private Long rollNo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getRollNo() {
		return rollNo;
	}
	public void setRollNo(Long rollNo) {
		this.rollNo = rollNo;
	}
	public Student(String name, Long rollNo) {
		super();
		autoIncrementID++;
		this.id = autoIncrementID;
		this.name = name;
		this.rollNo = rollNo;
	}
	public Student() {
		
	}
	

}