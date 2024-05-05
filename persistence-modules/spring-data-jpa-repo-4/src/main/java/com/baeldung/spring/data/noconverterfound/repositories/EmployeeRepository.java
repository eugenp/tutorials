package com.baeldung.spring.data.noconverterfound.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.noconverterfound.models.Employee;
import com.baeldung.spring.data.noconverterfound.models.EmployeeFullName;
import com.baeldung.spring.data.noconverterfound.models.IEmployeeFullName;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    EmployeeFullName findEmployeeFullNameById(int id);

    IEmployeeFullName findIEmployeeFullNameById(int id);

}
