package com.baeldung.hibernate.entities;

import javax.persistence.*;

import org.hibernate.annotations.FlushModeType;

@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "DeptEmployee_FindByEmployeeNumber", 
        query = "from DeptEmployee where employeeNumber = :employeeNo"),
        @org.hibernate.annotations.NamedQuery(name = "DeptEmployee_FindAllDesignation", 
        query = "from DeptEmployee where employeeNumber = :employeeNo"),
        @org.hibernate.annotations.NamedQuery(
            name = "DeptEmployee_UpdateEmployeeDesignation", 
            query = "Update DeptEmployee set designation = :newDesignation where employeeNumber = :employeeNo"
        ),
        @org.hibernate.annotations.NamedQuery(
            name = "DeptEmployee_FindAllByDepartment", 
            query = "from DeptEmployee where department = :department",
            timeout = 1,
            fetchSize = 10
        )
        })
@Entity
public class DeptEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String employeeNumber;

    private String designation;

    private String name;

    @ManyToOne
    private Department department;
    
    public DeptEmployee(String name, String employeeNumber, Department department) {
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
