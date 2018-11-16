package com.baeldung.hexagonal.domain;

import java.math.BigDecimal;

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String employeeId;
    private BigDecimal salary;

    public Employee(
        Long id, 
        String firstName, 
        String lastName, 
        String employeeId, 
        BigDecimal salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", employeeId=" + employeeId + ", salary=" + salary + "]";
    }

}
