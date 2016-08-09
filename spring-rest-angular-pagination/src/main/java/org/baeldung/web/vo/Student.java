package org.baeldung.web.vo;

import java.io.Serializable;

public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Student() {
	}

	public Student(String studentId, String name, String gender, Integer age) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

	private String studentId;
	private String name;
	private String gender;
	private Integer age;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
