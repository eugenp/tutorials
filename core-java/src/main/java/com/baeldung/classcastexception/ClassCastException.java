package com.baeldung.classcastexception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassCastException {

	public static void main(String[] args) {

		List<ArrayList<Person>> personList = new ArrayList<ArrayList<Person>>();
		// To correct the Exception at line #18, modify the line #11 code as:
		// List<List<Person>> personList = new ArrayList <List<Person>>();
		// Line #18 code as personList.add(Arrays.asList(personArray));
		Person p1 = new Person(1, "John");
		Person p2 = new Person(2, "Snow");
		Person[] personArray = new Person[] { p1, p2 };
		personList.add((ArrayList<Person>) Arrays.asList(personArray));
		System.out.println("Personlist: " + personList);

	}

}

class Person {
	int id;
	String name;

	Person(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}

}
