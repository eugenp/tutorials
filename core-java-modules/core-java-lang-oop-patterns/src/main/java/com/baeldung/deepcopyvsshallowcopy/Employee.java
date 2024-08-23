package com.baeldung.deepcopyvsshallowcopy;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable, Cloneable {

    private String firstName;
    private String lastName;
    private int employeeId;
    private Address address;

    public Employee(String firstName, String lastName, int employeeId, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.address = address;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // Overriding clone() method to create a deep copy of an object.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Employee employee;
        try {
            employee = (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            employee = new Employee(this.getFirstName(), this.getLastName(), this.getEmployeeId(), this.getAddress());
        }
        employee.address = (Address) this.address.clone();
        return employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) &&
            Objects.equals(address, employee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, employeeId, address);
    }

}
