package com.baeldung.pattern.hexagonal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.pattern.hexagonal.domain.Employee;
import com.baeldung.pattern.hexagonal.dto.EmployeeDto;
import com.baeldung.pattern.hexagonal.mappers.EmployeeMapper;
import com.baeldung.pattern.hexagonal.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeConroller {

    @Autowired
    EmployeeService employeeService;
    
    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping(path = "/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable(name = "id") Integer id){
        Employee emp = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeMapper.toDto(emp));
    }
}
