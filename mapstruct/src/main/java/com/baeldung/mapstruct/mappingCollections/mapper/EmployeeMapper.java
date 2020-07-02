package com.baeldung.mapstruct.mappingCollections.mapper;

import com.baeldung.mapstruct.mappingCollections.dto.EmployeeDTO;
import com.baeldung.mapstruct.mappingCollections.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface EmployeeMapper {

    List<EmployeeDTO> map(List<Employee> employees);

    Set<EmployeeDTO> map(Set<Employee> employees);

    Map<String, EmployeeDTO> map(Map<String, Employee> idEmployeeMap);
}
