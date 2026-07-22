package com.baeldung.hibernate.cte;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private Long id;

    private String name;
    private String title;
    private String department;
    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    public Long getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public String getTitle() {

        return title;
    }

    public String getDepartment() {

        return department;
    }

    public BigDecimal getSalary() {

        return salary;
    }

    public Employee getManager() {

        return manager;
    }
}