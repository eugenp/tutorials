package com.baeldung.collection.filtering;

/**
 * Java 8 Collection Filtering by List of Values base class.
 *
 * @author Rodolfo Felipe
 */
public class Employee {

    private Integer employeeNumber;
    private String name;
    private Integer departmentId;

    public Employee(Integer employeeNumber, String name, Integer departmentId) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.departmentId = departmentId;
    }

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

}
