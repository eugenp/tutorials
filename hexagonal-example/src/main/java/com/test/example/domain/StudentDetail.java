package com.test.example.domain;

public class StudentDetail {
	private final String studentName;
	private final String studentSpecialization;

	public StudentDetail(String studentName, String studentSpecialization) {
		super();
		this.studentName = studentName;
		this.studentSpecialization = studentSpecialization;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getStudentSpecialization() {
		return studentSpecialization;
	}

}
