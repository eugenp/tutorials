package com.baeldung.jpa.subtypes.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CNTR")
public class ContractEmployee extends Employee {

    private int contractPeriod;

    public int getContractPeriod() {
        return contractPeriod;
    }

    public void setContractPeriod(int contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    public ContractEmployee() {
        super();
    }

    public ContractEmployee(int contractPeriod, String name, int age) {
        super(name, age);
        this.contractPeriod = contractPeriod;        
    }    
}
