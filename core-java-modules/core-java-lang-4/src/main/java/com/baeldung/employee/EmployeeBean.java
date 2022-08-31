package com.baeldung.employee;

import java.io.Serializable;
import java.time.LocalDate;

public class EmployeeBean implements Serializable {

    private static final long serialVersionUID = -3760445487636086034L;

    private String firstName;
    private String lastName;
    private LocalDate startDate;

    public EmployeeBean() {

    }

    public EmployeeBean(String firstName, String lastName, LocalDate startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

}
