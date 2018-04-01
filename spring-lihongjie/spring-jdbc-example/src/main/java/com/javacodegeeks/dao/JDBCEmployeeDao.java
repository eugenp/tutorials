package com.javacodegeeks.dao;

import com.javacodegeeks.domain.Employee;

public interface JDBCEmployeeDao {

    void insert(Employee employee);

    Employee findById(int id);

}
