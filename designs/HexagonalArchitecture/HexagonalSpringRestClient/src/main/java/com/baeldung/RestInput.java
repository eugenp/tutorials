package com.baeldung;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.domain.Employee;
import com.baeldung.hexagonal.domain.Input;

@Component
@Scope(value = "prototype")
public class RestInput implements Input {

    private Employee employee;

    public RestInput(Employee employee) {
        this.employee = employee;
    }

    public Employee createEmployee() {
        return employee;
    }

}
