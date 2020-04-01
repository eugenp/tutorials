package com.baeldung.employee.service;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.baeldung.employee.domain.Employee;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService empService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {

        return "This is employee app page";
    }

    @PostMapping(path = "/employees", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@RequestBody Map<String, String> empBody) {

        String id = empBody.get("id");
        String name = empBody.get("name");

        Employee emp = new Employee(Integer.valueOf(id), name);
        empService.addEmployee(emp);

        // Create resource uri
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .buildAndExpand(emp.getId())
            .toUri();

        // Send uri in response
        return ResponseEntity.created(uri)
            .build();
    }
}
