package com.baeldung.hexagonal.port;


import com.baeldung.hexagonal.domain.Employee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmployeeUIPort {
    @PostMapping("create")
    public void create(@RequestBody Employee request);

    @GetMapping("view/{id}")
    public Employee view(@PathVariable Integer userId);
}