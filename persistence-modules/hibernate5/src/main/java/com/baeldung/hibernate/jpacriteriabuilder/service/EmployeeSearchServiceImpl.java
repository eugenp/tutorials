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

    public EmployeeSearchServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<DeptEmployee> filterbyDesignationUsingCriteriaBuilder(List<String> designaitons) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DeptEmployee> criteriaQuery = criteriaBuilder.createQuery(DeptEmployee.class);
        Root<DeptEmployee> root = criteriaQuery.from(DeptEmployee.class);
        In<Object> inClause = criteriaBuilder.in(root.get("designation"));
        for (String designaiton : designaitons) {
            inClause.value(designaiton);
        }
        criteriaQuery.select(root)
            .where(inClause);
        TypedQuery<DeptEmployee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<DeptEmployee> filterbyDesignationUsingExpression(List<String> designaitons) {
        CriteriaQuery<DeptEmployee> criteriaQuery = entityManager.getCriteriaBuilder()
            .createQuery(DeptEmployee.class);
        Root<DeptEmployee> root = criteriaQuery.from(DeptEmployee.class);
        criteriaQuery.select(root)
            .where(root.get("designation")
                .in(designaitons));
        TypedQuery<DeptEmployee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<DeptEmployee> searchByDepartmentQuery(String searchKey) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DeptEmployee> criteriaQuery = criteriaBuilder.createQuery(DeptEmployee.class);
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

}
