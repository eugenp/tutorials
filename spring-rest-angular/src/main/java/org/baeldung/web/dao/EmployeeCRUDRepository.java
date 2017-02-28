package org.baeldung.web.dao;

import java.util.List;

import org.baeldung.web.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "employee", path = "employees")
public interface EmployeeCRUDRepository extends CrudRepository<Employee, Long> {
    List<Employee> findByName(@Param("name") String name);
}
