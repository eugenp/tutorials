package com.baeldung.pattern.hexagonal.boundary.mappers;

import com.baeldung.pattern.hexagonal.boundary.models.EmployeeDto;
import com.baeldung.pattern.hexagonal.domain.models.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeDtoMapper {

    EmployeeDto map(Employee e);

    Employee map(EmployeeDto e);

}
