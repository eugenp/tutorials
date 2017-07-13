package com.baeldung.annotation.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.Date;

@XStreamAlias("customer")
public class CustomerOmitField {

    @XStreamOmitField
    private String firstName;

    private String lastName;

    private Date dob;

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

    @Override
    public String toString() {
        return "CustomerOmitAnnotation [firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + "]";
    }

}
