package com.baeldung.spring.data.keyvalue.services;

import com.baeldung.spring.data.keyvalue.vo.Employee;

public interface EmployeeService {
	
	void save(Employee employee);

    Employee get(Integer id);

    Iterable<Employee> fetchAll();

    void update(Employee employee);

    void delete(Integer id);

    Iterable<Employee> getSortedListOfEmployeesBySalary();

}
