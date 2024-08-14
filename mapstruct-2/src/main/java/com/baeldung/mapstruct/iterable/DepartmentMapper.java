package com.baeldung.mapstruct.iterable;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.baeldung.mapstruct.iterable.model.Department;
import com.baeldung.mapstruct.iterable.model.Employee;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    // This attempt of mapping causes error, preventing code from compiling
    // @Mapping(target = "employees", source = "employees")
    // Department map(List<Employee> employees);

    default Department map(List<Employee> employees) {
        Department department = new Department();
        department.setEmployees(employees);
        return department;
    }

    @Mapping(target = "employees", source = "employees")
    @Mapping(target = "manager", source = "departmentManager")
    Department mapWithManager(List<Employee> employees, Employee departmentManager);

}
