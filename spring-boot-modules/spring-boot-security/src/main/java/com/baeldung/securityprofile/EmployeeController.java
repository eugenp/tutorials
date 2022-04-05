package com.baeldung.securityprofile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class EmployeeController {

    @GetMapping("/employees")
    public List<String> getEmployees() {
        return Collections.singletonList("Adam Johnson");
    }
}
