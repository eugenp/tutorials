package com.baeldung.jsonignore.emptyfields;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Employee {

    private String lastName;
    private String firstName;
    private long id;
    private Optional<Salary> salary;

    private String phoneticName = "";

    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    public Employee() {
    }

    public Employee(final long id, final String lastName, final String firstName,
      final Optional<Salary> salary, final String phoneticName, final List<PhoneNumber> phoneNumbers) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.salary = salary;
        this.phoneticName = phoneticName != null ? phoneticName : "";
        this.phoneNumbers.addAll(phoneNumbers);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Optional<Salary> getSalary() {
        return salary;
    }

    public void setSalary(final Optional<Salary> salary) {
        this.salary = salary;
    }

    public String getPhoneticName() {
        return phoneticName;
    }

    public void setPhoneticName(final String phoneticName) {
        this.phoneticName = phoneticName;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(final List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return "Employee{" +
               "lastName='" + lastName + '\'' +
               ", firstName='" + firstName + '\'' +
               ", id=" + id +
               ", salary=" + salary +
               ", phoneticName='" + phoneticName + '\'' +
               ", phoneNumbers=" + phoneNumbers +
               '}';
    }
}
