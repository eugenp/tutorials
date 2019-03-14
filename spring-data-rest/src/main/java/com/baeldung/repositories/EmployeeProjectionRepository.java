package com.baeldung.repositories;

import com.baeldung.projections.EmployeeDto;
import com.baeldung.repositories.EmployeeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = EmployeeDto.class)
@Profile("expose-projections")
public interface EmployeeProjectionRepository extends EmployeeRepository {

}
