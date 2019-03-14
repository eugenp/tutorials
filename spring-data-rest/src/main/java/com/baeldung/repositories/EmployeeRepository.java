package com.baeldung.repositories;

import com.baeldung.models.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
@Profile("!expose-projections")
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
