package com.baeldung.orika;

public class Person {
	@Override
	public String toString() {
		return "Person [name=" + name + ", nickname=" + nickname + ", age="
				+ age + "]";
	}

	private String name;
	private String nickname;
	private int age;

	public Person() {

	}

	public Person(String name, String nickname, int age) {
		this.name = name;
		this.nickname = nickname;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
