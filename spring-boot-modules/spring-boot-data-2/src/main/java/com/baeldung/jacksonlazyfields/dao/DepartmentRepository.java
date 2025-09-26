package com.baeldung.jacksonlazyfields.dao;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.jacksonlazyfields.model.Department;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
