package com.javacodegeeks.dao;

import com.javacodegeeks.domain.Employee;

public interface EmployeeDao {

    void insert(Employee employee);

    Employee findById(int id);
}
