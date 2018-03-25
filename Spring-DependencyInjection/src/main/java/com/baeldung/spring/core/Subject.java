package com.baeldung.spring.core;

public class Subject {

	private String subjectName;

	private String subjectId;

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public String toString() {
		return "Subject [subjectName=" + subjectName + ", subjectId=" + subjectId + "]";
	}

}
