package org.baeldung.spring.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee {

	private long id;

	@NotNull
	@Size(min = 3)
	private String name;
	@NotNull
	@Size(min = 1)
	private String contactNumber;

	public Employee() {
		super();
	}

	public Employee(final long id, final String name, final String contactNumber) {
		this.id = id;
		this.name = name;
		this.contactNumber = contactNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(final String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", contactNumber=" + contactNumber + "]";
	}

}
