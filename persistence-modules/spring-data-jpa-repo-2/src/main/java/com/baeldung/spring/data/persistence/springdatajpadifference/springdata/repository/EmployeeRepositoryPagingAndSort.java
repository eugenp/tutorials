package com.baeldung.spring.data.persistence.springdatajpadifference.springdata.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.persistence.springdatajpadifference.model.Employee;

@Repository
public interface EmployeeRepositoryPagingAndSort extends PagingAndSortingRepository<Employee, Long> {

}