package com.baeldung.jsonignore.absentfields;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_ABSENT)
public class Employee {

    private String lastName;
    private String firstName;
    private long id;
    private Optional<Salary> salary;


    public Employee() {
    }

    public Employee(final long id, final String lastName, final String firstName, final Optional<Salary> salary) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.salary = salary;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public long getId() {
        return id;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
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

    @Override
    public String toString() {
        return "Employee{" +
               "lastName='" + lastName + '\'' +
               ", firstName='" + firstName + '\'' +
               ", id=" + id +
               ", salary=" + salary +
               '}';
    }
}
