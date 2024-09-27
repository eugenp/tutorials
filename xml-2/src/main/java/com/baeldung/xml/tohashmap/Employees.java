package com.baeldung.xml.tohashmap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;


@XmlRootElement(name = "employees")
public class Employees {

    private List<Employee> employeeList;

    @XmlElement(name = "employee")
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}