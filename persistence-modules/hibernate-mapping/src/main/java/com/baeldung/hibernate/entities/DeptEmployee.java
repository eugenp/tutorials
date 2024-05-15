package com.baeldung.hibernate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@org.hibernate.annotations.NamedQueries({ @org.hibernate.annotations.NamedQuery(name = "DeptEmployee_FindByEmployeeNumber", query = "from DeptEmployee where employeeNumber = :employeeNo"),
        @org.hibernate.annotations.NamedQuery(name = "DeptEmployee_FindAllByDesgination", query = "from DeptEmployee where employeeNumber = :designation"),
        @org.hibernate.annotations.NamedQuery(name = "DeptEmployee_UpdateEmployeeDepartment", query = "Update DeptEmployee set department = :newDepartment where employeeNumber = :employeeNo"),
        @org.hibernate.annotations.NamedQuery(name = "DeptEmployee_FindAllByDepartment", query = "from DeptEmployee where department = :department", timeout = 1, fetchSize = 10) })
@org.hibernate.annotations.NamedNativeQueries({ @org.hibernate.annotations.NamedNativeQuery(name = "DeptEmployee_FindByEmployeeName", query = "select * from deptemployee emp where name=:name", resultClass = DeptEmployee.class),
        @org.hibernate.annotations.NamedNativeQuery(name = "DeptEmployee_UpdateEmployeeDesignation", query = "call UPDATE_EMPLOYEE_DESIGNATION(:employeeNumber, :newDesignation)", resultClass = DeptEmployee.class) })
@Entity
public class DeptEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String employeeNumber;

    private String title;

    private String name;

    @ManyToOne
    private Department department;

    public DeptEmployee(String name, String employeeNumber, Department department) {
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.department = department;
    }
    
    public DeptEmployee(String name, String employeeNumber, String title, Department department) {
        super();
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
