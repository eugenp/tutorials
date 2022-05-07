package com.baeldung.employee;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeeVO {
    private String firstName;
    private String lastName;
    private LocalDate startDate;

    public EmployeeVO(String firstName, String lastName, LocalDate startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(firstName, obj.firstName)
            && Objects.equals(lastName, obj.lastName)
            && Objects.equals(startDate, obj.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, startDate);
    }
}
