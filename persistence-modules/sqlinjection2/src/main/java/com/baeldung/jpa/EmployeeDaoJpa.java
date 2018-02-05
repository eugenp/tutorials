package com.baeldung.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.baeldung.model.Employee;

public class EmployeeDaoJpa {

    private static EntityManagerFactory factory = null;

    public EmployeeDaoJpa() {
        factory = Persistence.createEntityManagerFactory("jpa-db");        
    }
    
    public void close(){
        factory.close();
    }

    @SuppressWarnings("unchecked")
    public List<Employee> searchEmployeesInsecure(String name) {
        EntityManager entityManager = factory.createEntityManager();

        try {
            return entityManager.createQuery("select emp from Employee emp where emp.name like '" + name + "'")
                .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Employee> searchEmployeesSecure1(String name) {

        EntityManager entityManager = factory.createEntityManager();

        try {
            // with a positional parameter in JPQL
            Query jpqlQuery = entityManager.createQuery("select emp from Employee emp where emp.name like ?1");
            return jpqlQuery.setParameter(1, name)
                .getResultList();

        } finally {
            entityManager.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Employee> searchEmployeesSecure2(String name) {

        EntityManager entityManager = factory.createEntityManager();

        try {
            // with a named parameter in JPQL
            Query jpqlQuery = entityManager.createQuery("select emp from Employee emp where emp.name like :empname");
            return jpqlQuery.setParameter("empname", name)
                .getResultList();
        } finally {
            entityManager.close();
        }

    }

}
