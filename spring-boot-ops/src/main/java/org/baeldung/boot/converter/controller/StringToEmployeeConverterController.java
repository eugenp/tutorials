package org.baeldung.boot.converter.controller;

import org.baeldung.boot.domain.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/string-to-employee")
public class StringToEmployeeConverterController {

    @GetMapping
    public ResponseEntity<Object> getStringToEmployee(@RequestParam("employee") Employee employee) {
        return ResponseEntity.ok(employee);
    }
}
