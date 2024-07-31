package com.baeldung.prototypebean.dynamicarguments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

@Component
public class EmployeeBeanUsingFunction {

    @Autowired
    private Function<String, Employee> beanFactory;

    public Employee getEmployee(String name) {
        Employee employee = beanFactory.apply(name);
        return employee;
    }
}
