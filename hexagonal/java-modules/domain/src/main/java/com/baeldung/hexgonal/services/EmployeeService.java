package com.baeldung.hexgonal.services;



public interface EmployeeService {

    Long createEmployee(Long id, String firstName, String lastName, String code);

    void deleteEmployee(Long id);

}