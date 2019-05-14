package com.baeldung.hexagonalarchitecture.adapter.website.form;

import java.util.List;

import com.baeldung.hexagonalarchitecture.core.domain.Major;
import com.baeldung.hexagonalarchitecture.core.domain.StudentApplication;

public class ApplicationsForm {
	private List<StudentApplication> applications;

	private Long id;

	private Major major;

	private String name;

	public List<StudentApplication> getApplications() {
		return applications;
	}

	public Long getId() {
		return id;
	}

	public Major getMajor() {
		return major;
	}

	public String getName() {
		return name;
	}

	public void setApplications(List<StudentApplication> applications) {
		this.applications = applications;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public void setName(String name) {
		this.name = name;
	}
}