package org.baeldung.pojo;

import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("customer")
public class Customer {

	@XStreamOmitField
	private String firstName;

	private String lastName;

	private Date dob;

	@XStreamImplicit
	private List<ContactDetails> contactDetailsList;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public List<ContactDetails> getContactDetailsList() {
		return contactDetailsList;
	}

	public void setContactDetailsList(List<ContactDetails> contactDetailsList) {
		this.contactDetailsList = contactDetailsList;
	}

}
