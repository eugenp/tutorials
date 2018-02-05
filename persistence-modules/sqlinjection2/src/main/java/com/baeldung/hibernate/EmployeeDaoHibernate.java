package com.baeldung.hibernate;

import com.baeldung.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EmployeeDaoHibernate {

        private static SessionFactory factory;

        public EmployeeDaoHibernate() {
                factory = new Configuration().configure("employee.cfg.xml").buildSessionFactory();
        }

        public void close() {
                factory.close();
        }

        @SuppressWarnings("unchecked")
        public List<Employee> searchEmployeesInsecure(String name) {
                try (Session session = factory.openSession()) {
                        return session.createQuery("From Employee emp where emp.name like '" + name + "'").getResultList();
                }
        }

        @SuppressWarnings("unchecked")
        public List<Employee> searchEmployeesSecure1(String name) {
                try (Session session = factory.openSession()) {
                        // JPQL like positional parameter
                        return session.createQuery("From Employee emp where emp.name like ?0").setParameter(0, name).getResultList();
                }
        }

        @SuppressWarnings("unchecked")
        public List<Employee> searchEmployeesSecure2(String name) {
                try (Session session = factory.openSession()) {
                        // JPQL like named parameter
                        return session.createQuery("From Employee emp where emp.name like :empname").setParameter("empname", name).getResultList();
                }
        }
}
