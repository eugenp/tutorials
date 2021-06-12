package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmployeeControllerPort {
    @PostMapping("create")
    boolean create(@RequestBody Employee request);

    @GetMapping("view/{id}")
    Employee view(@PathVariable Integer userId);
}