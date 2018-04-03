package com.stackify.stream;

import java.util.List;

public class EmployeeRepository {
    private List<Employee> empList;
    
    public EmployeeRepository(List<Employee> empList) {
        this.empList = empList;
    
    }
    public Employee findById(Integer id) {
        for (Employee emp : empList) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        
        return null;
    }
}
