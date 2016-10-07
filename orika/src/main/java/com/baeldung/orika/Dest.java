package com.baeldung.orika;

public class Dest {
	@Override
	public String toString() {
		return "Dest [name=" + name + ", age=" + age + "]";
	}

	private String name;
	private int age;

	public Dest() {

	}

	public Dest(String name, int age) {
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

}
