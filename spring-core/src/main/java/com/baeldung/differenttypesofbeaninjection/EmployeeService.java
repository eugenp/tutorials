package com.baeldung.differenttypesofbeaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {

    private Salary salary;

    @Autowired
    public EmployeeService(Salary salary) {
        this.salary = salary;
    }

    public String process() {
        return salary.process("100");
    }
}
