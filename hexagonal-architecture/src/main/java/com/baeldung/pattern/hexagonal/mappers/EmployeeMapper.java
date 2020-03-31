package com.baeldung.pattern.hexagonal.mappers;

import org.mapstruct.Mapper;

import com.baeldung.pattern.hexagonal.domain.Employee;
import com.baeldung.pattern.hexagonal.dto.EmployeeDto;

@Mapper
public interface EmployeeMapper {
    
    EmployeeDto toDto(Employee emp);
    
    Employee toDomain(EmployeeDto eDto);

}
