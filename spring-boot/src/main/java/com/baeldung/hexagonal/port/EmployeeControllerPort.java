package com.baeldung.port;


import com.baeldung.domain.Employee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmployeeControllerPort {
    @PostMapping("create")
    public void create(@RequestBody Employee request);

    @GetMapping("view/{id}")
    public Employee view(@PathVariable Integer userId);
}