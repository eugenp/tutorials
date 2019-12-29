package com.baeldung.elementcollection.repository;

import com.baeldung.elementcollection.model.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Employee employee) {
        em.persist(employee);
    }

    @Transactional
    public void remove(int id) {
        Employee employee = findById(id);
        em.remove(employee);
    }

    public Employee findById(int id) {
        return em.find(Employee.class, id);
    }

    public Employee findByJPQL(int id) {
        return em.createQuery("SELECT u FROM Employee AS u JOIN FETCH u.phones WHERE u.id=:id", Employee.class).setParameter("id", id).getSingleResult();
    }

    public Employee findByEntityGraph(int id) {
        EntityGraph<Employee> entityGraph = em.createEntityGraph(Employee.class);
        entityGraph.addAttributeNodes("name", "phones");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        return em.find(Employee.class, id, properties);
    }
}
