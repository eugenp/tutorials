package com.baeldung.hexagonalarchitecture.domain;

public class StudentApplication implements Comparable<StudentApplication> {
	private Long id;

	private Major major;

	private String name;

	public StudentApplication() {

	}

	public StudentApplication(Major major, String name) {
		this.major = major;
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentApplication other = (StudentApplication) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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

	@Override
	public int compareTo(StudentApplication other) {
		return this.getId().compareTo(other.getId());
	}
}