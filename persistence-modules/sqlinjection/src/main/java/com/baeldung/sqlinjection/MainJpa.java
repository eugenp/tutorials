package com.baeldung.sqlinjection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.baeldung.sqlinjection.jpa.EmployeeDaoJpa;
import com.baeldung.sqlinjection.model.Employee;

public class MainJpa {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-db");
        EntityManager manager = factory.createEntityManager();
        Employee emp1 = new Employee(1L, "Trudy");
        Employee emp2 = new Employee(2L, "Bob");
        manager.getTransaction()
            .begin();
        manager.persist(emp1);
        manager.persist(emp2);
        manager.getTransaction()
            .commit();

        EmployeeDaoJpa empDao = new EmployeeDaoJpa();
        empDao.searchEmployeesInsecure("Trudy' or 1=1 or '1'='1")
            .forEach(e -> System.out.println("1. JPA: " + e));
        empDao.searchEmployeesInsecure("Trudy")
            .forEach(e -> System.out.println("1. JPA: " + e));

        empDao.searchEmployeesSecure1("Tru%")
            .forEach(e -> System.out.println("2. JPA: " + e));

        empDao.searchEmployeesSecure2("%")
            .forEach(e -> System.out.println("3. JPA: " + e));
        empDao.close();

    }

}
