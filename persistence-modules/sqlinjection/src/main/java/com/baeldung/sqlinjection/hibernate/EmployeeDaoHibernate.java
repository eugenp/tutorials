package com.baeldung.sqlinjection.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.baeldung.sqlinjection.model.Employee;

import java.util.List;

public class EmployeeDaoHibernate {

    SessionFactory factory;

    public EmployeeDaoHibernate(SessionFactory factory) {
        this.factory = factory;
    }

    @SuppressWarnings("unchecked")
    public List<Employee> searchEmployeesInsecure(String name) {
        try (Session session = factory.openSession()) {
            return session.createQuery("From Employee emp where emp.name like '" + name + "'")
                .getResultList();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Employee> searchEmployeesSecure1(String name) {
        try (Session session = factory.openSession()) {
            // JPQL like positional parameter
            return session.createQuery("From Employee emp where emp.name like ?0")
                .setParameter(0, name)
                .getResultList();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Employee> searchEmployeesSecure2(String name) {
        try (Session session = factory.openSession()) {
            // JPQL like named parameter
            return session.createQuery("From Employee emp where emp.name like :empname")
                .setParameter("empname", name)
                .getResultList();
        }
    }
}
