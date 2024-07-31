package com.baeldung.hibernate.pojo;

import org.hibernate.annotations.*;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Where(clause = "deleted = false")
@FilterDef(name = "incomeLevelFilter", parameters = @ParamDef(name = "incomeLimit", type = Integer.class))
@Filter(name = "incomeLevelFilter", condition = "grossIncome > :incomeLimit")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private long grossIncome;

    private int taxInPercents;

    private boolean deleted;

    public long getTaxJavaWay() {
        return grossIncome * taxInPercents / 100;
    }

    @Formula("grossIncome * taxInPercents / 100")
    private long tax;

    @OneToMany
    @JoinColumn(name = "employee_id")
    @Where(clause = "deleted = false")
    private Set<Phone> phones = new HashSet<>(0);

    public Employee() {
    }

    public Employee(long grossIncome, int taxInPercents) {
        this.grossIncome = grossIncome;
        this.taxInPercents = taxInPercents;
    }

    public Integer getId() {
        return id;
    }

    public long getGrossIncome() {
        return grossIncome;
    }

    public int getTaxInPercents() {
        return taxInPercents;
    }

    public long getTax() {
        return tax;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGrossIncome(long grossIncome) {
        this.grossIncome = grossIncome;
    }

    public void setTaxInPercents(int taxInPercents) {
        this.taxInPercents = taxInPercents;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

}
