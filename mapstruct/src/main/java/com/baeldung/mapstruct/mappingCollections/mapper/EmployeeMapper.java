package com.baeldung.mapstruct.mappingCollections.mapper;

import com.baeldung.mapstruct.mappingCollections.dto.EmployeeDTO;
import com.baeldung.mapstruct.mappingCollections.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface EmployeeMapper {

    EmployeeDTO employeeToEmployeeDto(Employee employee);

    List<EmployeeDTO> listOfEmployeeToListOfEmployeeDto(List<Employee> employees);

    Set<EmployeeDTO> setOfEmployeeToSetOfEmployeeDto(Set<Employee> employees);

    Map<String, EmployeeDTO> mapOfEmployeeToMapOfEmployeeDto(Map<String, Employee> idEmployeeMap);
}
