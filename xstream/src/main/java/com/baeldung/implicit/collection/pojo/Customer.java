package com.baeldung.implicit.collection.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.Date;
import java.util.List;

@XStreamAlias("customer")
public class Customer {

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

    @Override
    public String toString() {
        return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", contactDetailsList=" + contactDetailsList + "]";
    }
}
