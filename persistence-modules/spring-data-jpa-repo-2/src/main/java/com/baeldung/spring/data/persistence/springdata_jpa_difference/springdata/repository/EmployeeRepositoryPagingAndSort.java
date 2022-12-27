package com.baeldung.spring.data.persistence.springdata_jpa_difference.springdata.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.persistence.springdata_jpa_difference.model.Employee;

@Repository
public interface EmployeeRepositoryPagingAndSort extends PagingAndSortingRepository<Employee, Long> {

}