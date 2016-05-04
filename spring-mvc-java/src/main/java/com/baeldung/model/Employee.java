package com.baeldung.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Employee {

    private long id;
    private String name;
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
