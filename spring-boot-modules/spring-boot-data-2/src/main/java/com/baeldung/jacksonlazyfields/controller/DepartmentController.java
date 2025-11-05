package com.baeldung.jacksonlazyfields.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.jacksonlazyfields.dao.DepartmentRepository;
import com.baeldung.jacksonlazyfields.dto.DepartmentDto;
import com.baeldung.jacksonlazyfields.model.Department;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private DepartmentRepository repository;

    public DepartmentController(DepartmentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Department post(@RequestBody Department department) {
        return repository.save(department);
    }

    @GetMapping("/{id}")
    Optional<DepartmentDto> get(@PathVariable("id") Long id) {
        return repository.findById(id)
            .map(d -> new DepartmentDto(id, d.getName()));
    }
}
