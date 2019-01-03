package com.baeldung.hexagonal.api;

import com.baeldung.hexagonal.model.Employee;

import java.util.List;

public interface EmployeeApi {

    Employee create(String nameFirst, String nameLast) ;

    List<Employee> findAll() ;
}
