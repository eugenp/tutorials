package org.baeldung.resttemplate.lists.dto;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListDTO
{
    public List<Employee> employees;

    public EmployeeListDTO()
    {
        employees = new ArrayList<>();
    }

    public EmployeeListDTO(List<Employee> employees)
    {
        this.employees = employees;
    }

    public void setEmployees(List<Employee> employees)
    {
        this.employees = employees;
    }

    public List<Employee> getEmployees()
    {
        return employees;
    }
}
