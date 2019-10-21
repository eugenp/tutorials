package org.baeldung.javaxval.messageinterpolator;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Person {

	@Size(min = 10, max = 200, message = "Name should be in-between {min} and {max} characters")
	private String name;

	@Min(value = 18, message = "Age should not be less than {value} but it is ${validatedValue}")
	private int age;

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
