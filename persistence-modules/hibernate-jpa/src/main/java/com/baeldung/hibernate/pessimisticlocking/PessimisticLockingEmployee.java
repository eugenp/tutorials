package com.baeldung.hibernate.pessimisticlocking;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class PessimisticLockingEmployee extends Individual {

    private BigDecimal salary;

    public PessimisticLockingEmployee(Long id, String name, String lastName, BigDecimal salary) {
        super(id, name, lastName);
        this.salary = salary;
    }

    public PessimisticLockingEmployee() {
        super();
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal average) {
        this.salary = average;
    }
}
