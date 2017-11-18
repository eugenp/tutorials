package com.baeldung.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.baeldung.hibernate.pojo.Supplier;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            // NOTE: this is just for boostrap testing for multitenancy.
            System.out.println("Checking the system.");
            SessionFactory sessionFactory = HibernateMultiTenantUtil.getSessionFactory();
            Session currentSession = sessionFactory.withOptions().tenantIdentifier("h2db1").openSession();
            Transaction transaction = currentSession.getTransaction();
            transaction.begin();
            currentSession.createCriteria(Supplier.class).list().stream().forEach(System.out::println);
            transaction.commit();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
