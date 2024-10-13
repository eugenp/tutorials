package com.baeldung.jpa.subtypes.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PERM")
public class PermanentEmployee extends Employee {

    private int employeeId;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public PermanentEmployee() {
        super();
    }

    public PermanentEmployee(int employeeId,String name, int age) {
        super(name, age);
        this.employeeId = employeeId;
    }        
}
