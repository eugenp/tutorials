package com.baeldung.dipractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Suyambu
 *
 */
@Component
public class Processor {

    private Employee employee;
    @Autowired
    private Unit unit;

    // getter & setter methods

    public Employee getEmployee() {
        return employee;
    }
    
    @Autowired
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void process() {
        employee.getDetails();
        unit.getDetails();
    }

}
