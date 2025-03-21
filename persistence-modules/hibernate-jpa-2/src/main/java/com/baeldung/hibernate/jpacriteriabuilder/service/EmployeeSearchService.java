package com.baeldung.hibernate.jpacriteriabuilder.service;

import java.util.List;

import com.baeldung.hibernate.entities.DeptEmployee;

public interface EmployeeSearchService {

    List<DeptEmployee> filterbyTitleUsingCriteriaBuilder(List<String> titles);

    List<DeptEmployee> filterbyTitleUsingExpression(List<String> titles);

    List<DeptEmployee> searchByDepartmentQuery(String query);

}
