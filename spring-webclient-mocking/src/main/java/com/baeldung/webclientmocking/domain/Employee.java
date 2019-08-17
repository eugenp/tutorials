package com.baeldung.webclientmocking.domain;


import com.baeldung.webclientmocking.enums.Role;

public class Employee {
    private Integer employeeId;
    private String firsName;
    private String lastName;
    private Integer age;
    private Role role;

    public Employee(){
    }

    public Employee(Integer _employeeId, String _firsName, String _lastName, Integer _age, Role _role) {
        this.employeeId = _employeeId;
        this.firsName = _firsName;
        this.lastName = _lastName;
        this.age = _age;
        this.role = _role;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

