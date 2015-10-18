package org.baeldung;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

	@NotNull(message = "Name cannot be null")
	private String name;

	@AssertTrue
	private boolean working;

	@Size(min = 10, max = 200, message = "Number of characters should be in between 10 and 200 inclusive")
	private String aboutMe;

	@Min(value = 18, message = "Age should not be less than 18")
	@Max(value = 150, message = "Age should not be more than 150")
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
