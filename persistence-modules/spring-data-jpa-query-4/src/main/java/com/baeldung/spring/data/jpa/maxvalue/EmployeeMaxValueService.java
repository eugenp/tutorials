package com.baeldung.spring.data.jpa.maxvalue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Service
public class EmployeeMaxValueService {
    private final EntityManager entityManager;

    @Autowired
    public EmployeeMaxValueService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Long> findMaxSalaryCriteriaAPI() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<Employee> root = query.from(Employee.class);
        query.select(cb.max(root.get("salary")));

        TypedQuery<Long> typedQuery = entityManager.createQuery(query);
        return Optional.ofNullable(typedQuery.getSingleResult());
    }
}
