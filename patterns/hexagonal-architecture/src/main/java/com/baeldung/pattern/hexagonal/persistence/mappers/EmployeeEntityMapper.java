package com.baeldung.pattern.hexagonal.persistence.mappers;

import com.baeldung.pattern.hexagonal.domain.models.Employee;
import com.baeldung.pattern.hexagonal.persistence.entities.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEntityMapper {

    EmployeeEntity map(Employee e);

    Employee map(EmployeeEntity e);

}

