package com.baeldung.eclipselink.springdata.pessimisticlocking;

import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Employee extends Individual {

    private BigDecimal salary;

    public Employee(Long id, String name, String lastName, BigDecimal salary) {
        super(id, name, lastName);
        this.salary = salary;
    }

    public Employee() {
        super();
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal average) {
        this.salary = average;
    }
}
