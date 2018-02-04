package com.baeldung.springpractice;

/**
 * 
 * @author Suyambu
 *
 */
public class Processor {

    private Employee employee;

    // getter & setter methods

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void process() {
        employee.getDetails();
    }

}
