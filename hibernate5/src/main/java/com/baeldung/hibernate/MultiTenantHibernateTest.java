package com.baeldung.hibernate;
import static org.junit.Assert.assertNotEquals;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.pojo.Suppliers;


public class MultiTenantHibernateTest  {
    
    
    @Before
    public void init_DB1() {
        // setting data in DB1.
        SessionFactory sessionFactory;
        Session currentSession=null;
        try {
            sessionFactory = HibernateMultiTenantUtil.getSessionFactory();
            currentSession = sessionFactory.withOptions().tenantIdentifier("h2db1").openSession();
            Transaction transaction = currentSession.getTransaction();
            transaction.begin();
            currentSession.save(new Suppliers("John", "USA") );            
            //currentSession.createCriteria(Suppliers.class).list().stream().forEach(System.out::println);
            transaction.commit();
            System.out.println("Inserted row in DB1");
            
        } catch (UnsupportedTenancyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally  {
            currentSession.close();
        }
    }

    @Before
    public void init_DB2() {
        // setting data in DB2
        SessionFactory sessionFactory;
        Session currentSession=null;
        try {
            sessionFactory = HibernateMultiTenantUtil.getSessionFactory();
            currentSession = sessionFactory.withOptions().tenantIdentifier("h2db2").openSession();
            Transaction transaction = currentSession.getTransaction();
            transaction.begin();
            currentSession.save(new Suppliers("Miller", "UK") );            
            //currentSession.createCriteria(Suppliers.class).list().stream().forEach(System.out::println);
            transaction.commit();
            System.out.println("Inserted row in DB2");
            
        } catch (UnsupportedTenancyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally  {
            currentSession.close();
        }
    }

    
    @Test
    public void givenDBMode_whenFetchingSuppliers_thenComparingFromDbs () {
        SessionFactory sessionFactory;
        try {
            sessionFactory = HibernateMultiTenantUtil.getSessionFactory();
            
            Session db1Session = sessionFactory
                .withOptions().tenantIdentifier("h2db1").openSession();
            
            Transaction transaction = db1Session.getTransaction();
            transaction.begin();
            Suppliers supplierFromDB1 = (Suppliers)db1Session.createCriteria(Suppliers.class).list().get(0);
            transaction.commit();
            
            Session db2Session = sessionFactory
                .withOptions().tenantIdentifier("h2db2").openSession();
            
            db2Session.getTransaction().begin();
            Suppliers supplierFromDB2 = (Suppliers) db2Session.createCriteria(Suppliers.class).list().get(0);
            db2Session.getTransaction().commit();
            
            System.out.println(supplierFromDB1);
            System.out.println(supplierFromDB2);
            
            assertNotEquals(supplierFromDB1, supplierFromDB2);   
        } catch (UnsupportedTenancyException e) {
            e.printStackTrace();
        }
    }
}
