package com.baeldung.sqlinjection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.baeldung.sqlinjection.hibernate.EmployeeDaoHibernate;
import com.baeldung.sqlinjection.model.Employee;

public class MainHibernate {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration().configure("employee.cfg.xml")
            .buildSessionFactory();
        try (Session session = factory.openSession()) {
            Employee emp1 = new Employee(1L, "Trudy");
            Employee emp2 = new Employee(2L, "Bob");

            session.beginTransaction();
            session.save(emp1);
            session.save(emp2);
            session.getTransaction()
                .commit();
        }

        EmployeeDaoHibernate empDaoHibernate = new EmployeeDaoHibernate(factory);
        empDaoHibernate.searchEmployeesInsecure("Trudy")
            .forEach(e -> System.out.println("1. Hibernate: " + e));

        empDaoHibernate.searchEmployeesSecure1("Tru%")
            .forEach(e -> System.out.println("2. Hibernate: " + e));

        empDaoHibernate.searchEmployeesSecure2("%")
            .forEach(e -> System.out.println("3. Hibernate: " + e));

        factory.close();
    }

}
