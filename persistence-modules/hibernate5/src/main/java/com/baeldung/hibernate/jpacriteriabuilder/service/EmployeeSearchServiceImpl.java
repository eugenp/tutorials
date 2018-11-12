package com.baeldung.hibernate.jpacriteriabuilder.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.baeldung.hibernate.entities.Department;
import com.baeldung.hibernate.entities.DeptEmployee;

public class EmployeeSearchServiceImpl implements EmployeeSearchService {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public EmployeeSearchServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        
    }

    @Override
    public List<DeptEmployee> filterbyDesignationUsingCriteriaBuilder(List<String> designations) {
        CriteriaQuery<DeptEmployee> criteriaQuery = createCriteriaQuery(DeptEmployee.class);
        Root<DeptEmployee> root = criteriaQuery.from(DeptEmployee.class);
        In<String> inClause = criteriaBuilder.in(root.get("designation"));
        for (String designaiton : designations) {
            inClause.value(designaiton);
        }
        criteriaQuery.select(root)
            .where(inClause);
        TypedQuery<DeptEmployee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<DeptEmployee> filterbyDesignationUsingExpression(List<String> designations) {
        CriteriaQuery<DeptEmployee> criteriaQuery = createCriteriaQuery(DeptEmployee.class);
        Root<DeptEmployee> root = criteriaQuery.from(DeptEmployee.class);
        criteriaQuery.select(root)
            .where(root.get("designation")
                .in(designations));
        TypedQuery<DeptEmployee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<DeptEmployee> searchByDepartmentQuery(String searchKey) {
        CriteriaQuery<DeptEmployee> criteriaQuery = createCriteriaQuery(DeptEmployee.class);
        Root<DeptEmployee> emp = criteriaQuery.from(DeptEmployee.class);

        Subquery<Department> subquery = criteriaQuery.subquery(Department.class);
        Root<Department> dept = subquery.from(Department.class);
        subquery.select(dept)
            .distinct(true)
            .where(criteriaBuilder.like(dept.get("name"), new StringBuffer("%").append(searchKey)
                .append("%")
                .toString()));

        criteriaQuery.select(emp)
            .where(criteriaBuilder.in(emp.get("department"))
                .value(subquery));

        TypedQuery<DeptEmployee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
    
    private <T> CriteriaQuery<T> createCriteriaQuery(Class<T> klass) {
        return criteriaBuilder.createQuery(klass);
    }

}
