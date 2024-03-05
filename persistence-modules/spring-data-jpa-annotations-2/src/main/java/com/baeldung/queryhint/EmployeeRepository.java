package com.baeldung.queryhint;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @QueryHints(value = { @QueryHint(name = "org.hibernate.fetchSize", value = "50") })
    List<Employee> findByGender(String gender);

    @QueryHints(value = { @QueryHint(name = "javax.persistence.query.timeout", value = "5000") })
    List<Employee> findActiveEmployees(long inactiveDaysThreshold);

    @QueryHints(value = { @QueryHint(name = "jakarta.persistence.cache.retrieveMode", value = "USE"),
        @QueryHint(name = "jakarta.persistence.cache.storeMode", value = "USE") })
    List<Employee> findEmployeesByName(String name);

    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Employee findByUsername(String username);

    @QueryHints(value = { @QueryHint(name = "org.hibernate.comment", value = "Retrieve employee older than specified age\"") })
    List<Employee> findByAgeGreaterThan(int age);
}
