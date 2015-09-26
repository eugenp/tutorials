package sample.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {

	@NotNull(message = "First name cannot be null")
	@NotEmpty(message = "First name cannot be empty")
	private String fname;

	@NotNull(message = "Last name cannot be null")
	@NotEmpty(message = "Last name cannot be empty")
	private String lname;

	@Min(value = 18, message = "Age must be greater than or equal to 18")
	@Max(value = 150, message = "Age must be less than or equal to 150")
	private int age;

	@NotNull(message = "Last name cannot be null")
	@NotEmpty(message = "Last name cannot be empty")
	private String gender;

	@NotNull(message = "Email Address is compulsory")
	@NotEmpty(message = "Email Address is compulsory")
	@Email(message = "Email Address is not a valid format")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
