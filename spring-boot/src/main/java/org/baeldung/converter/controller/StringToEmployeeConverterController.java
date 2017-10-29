package org.baeldung.converter.controller;

import com.baeldung.toggle.Employee;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/string-to-employee")
public class StringToEmployeeConverterController {

    @GetMapping
    public ResponseEntity<Object> getStringToEmployee(@RequestParam("employee") Employee employee) {
        return ResponseEntity.ok(employee);
    }

    @PostMapping(consumes = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<Object> postStringToEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employee);
    }
}
