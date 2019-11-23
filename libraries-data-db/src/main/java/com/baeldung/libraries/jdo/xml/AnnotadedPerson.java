package com.baeldung.libraries.jdo.xml;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

@PersistenceCapable(schema = "/myproduct/people", table = "person")
public class AnnotadedPerson {
    @XmlAttribute
    private long personNum;

    @PrimaryKey
    private String firstName;
    private String lastName;

    @XmlElementWrapper(name = "phone-numbers")
    @XmlElement(name = "phone-number")
    @Element(types = String.class)
    private List phoneNumbers = new ArrayList();

    public AnnotadedPerson(long personNum, String firstName, String lastName) {
        super();
        this.personNum = personNum;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getPersonNum() {
        return personNum;
    }

    public void setPersonNum(long personNum) {
        this.personNum = personNum;
    }

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

    public List getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

}
