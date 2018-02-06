package com.baeldung.sqlinjection.springdata;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.baeldung.sqlinjection.model.Employee;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    public List<Employee> findByName(String name);

    @Query("select emp from Employee emp where emp.name like :name")
    public List<Employee> findByNameQuery(@Param("name") String name, Sort sort);

}
