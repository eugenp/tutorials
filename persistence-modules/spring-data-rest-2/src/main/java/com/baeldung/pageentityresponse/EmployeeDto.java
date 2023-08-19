package com.baeldung.pageentityresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("dept")
    private String dept;

    @JsonProperty("salary")
    private long salary;

    public EmployeeDto() {
    }

    public EmployeeDto(String name, String dept, long salary) {
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }
}

