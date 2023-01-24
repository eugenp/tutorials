package com.baeldung.spring.data.persistence.springdatajpadifference;

import com.baeldung.spring.data.persistence.springdatajpadifference.model.Employee;

public class TestUtils {

    public static Employee employee(String firstName, String lastname) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastname);
        employee.setEmail(firstName + lastname + "@baeldung.com");

        return employee;
    }
}
