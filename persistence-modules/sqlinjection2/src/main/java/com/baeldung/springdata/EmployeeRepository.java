package com.baeldung.springdata;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baeldung.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    public List<Employee> findByName(String name);
    
    @Query("select emp from Employee emp where emp.name=:name")
    public List<Employee> findByNameQuery(@Param("name") String name);

}
