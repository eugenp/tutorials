package org.baeldung.boot.converter.controller;

import org.baeldung.boot.domain.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StringToEmployeeConverterController {
 
    @GetMapping("/string-to-employee")
    public ResponseEntity<Object> getStringToEmployee(@RequestParam("employee") Employee employee) {
        return ResponseEntity.ok(employee);
    }
}
