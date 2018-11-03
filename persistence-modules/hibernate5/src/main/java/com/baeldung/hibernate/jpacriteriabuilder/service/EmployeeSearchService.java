package com.baeldung.hibernate.jpacriteriabuilder.service;

import java.util.List;

import com.baeldung.hibernate.entities.DeptEmployee;

public interface EmployeeSearchService {

    List<DeptEmployee> filterbyDesignationUsingCriteriaBuilder(List<String> designaitons);

    List<DeptEmployee> filterbyDesignationUsingExpression(List<String> aurhors);

    List<DeptEmployee> searchByDepartmentQuery(String query);

}
