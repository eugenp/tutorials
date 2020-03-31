package com.baeldung.pattern.hexagonal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.pattern.hexagonal.domain.Employee;
import com.baeldung.pattern.hexagonal.dto.EmployeeDto;
import com.baeldung.pattern.hexagonal.mappers.EmployeeMapper;
import com.baeldung.pattern.hexagonal.service.EmployeeService;

@RestController @RequestMapping("/employees") public class EmployeeConroller {

        @Autowired EmployeeService employeeService;

        @Autowired EmployeeMapper employeeMapper;

        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto emp) {
                Employee newEmp = employeeService.createEmployee(employeeMapper.toDomain(emp));
                return ResponseEntity.ok(employeeMapper.toDto(newEmp));
        }

        @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<EmployeeDto> getEmployee(@PathVariable(name = "id") Integer id) {
                Employee emp = employeeService.getEmployeeById(id);
                return ResponseEntity.ok(employeeMapper.toDto(emp));
        }

        @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?> removeEmployee(@PathVariable(name = "id") Integer id) {
                employeeService.removeEmployeeById(id);
                return ResponseEntity.noContent().build();
        }
}
