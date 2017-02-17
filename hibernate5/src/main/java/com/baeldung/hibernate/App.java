package com.baeldung.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.baeldung.hibernate.pojo.Suppliers;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
//            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            SessionFactory sessionFactory = HibernateMultiTenantUtil.getSessionFactory();
//            Session currentSession = sessionFactory.openSession();
            Session currentSession = sessionFactory.withOptions().tenantIdentifier("db1").openSession();
            System.out.println("Count:"+currentSession.createCriteria(Suppliers.class).list().size());
            currentSession.createCriteria(Suppliers.class).list().stream().forEach(System.out::println);
            System.out.println("Done1");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
