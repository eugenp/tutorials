package com.baeldung.injectbean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {

    private Employee employee;

    @Autowired
    public EmployeeService(Employee employee) {
        this.employee = employee;
    }

    // stupid example metod
    public String getEmploteeName() {

        return employee.getName();
    }

    public Address getEmploteeAddress() {

        return employee.getAddress();
    }

}
