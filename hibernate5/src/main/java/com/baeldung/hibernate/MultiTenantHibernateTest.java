package com.baeldung.hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

import com.baeldung.hibernate.pojo.Suppliers;


public class MultiTenantHibernateTest  {
    @Test
    public void givenDBMode_whenFetchingSuppliers_thenFetchingFirstElement () {
        SessionFactory sessionFactory;
        try {
            sessionFactory = HibernateMultiTenantUtil.getSessionFactory();
            
            Session db1Session = sessionFactory
                .withOptions().tenantIdentifier("db1").openSession();
            
            Transaction transaction = db1Session.getTransaction();
            transaction.begin();
            String supplierFromDB1 = (String) db1Session.createCriteria(Suppliers.class).list().get(0);
            transaction.commit();
            
            Session db2Session = sessionFactory
                .withOptions().tenantIdentifier("db2").openSession();
            
            db2Session.getTransaction().begin();
            String supplierFromDB2 = (String) db2Session.createCriteria(Suppliers.class).list().get(0);
            db2Session.getTransaction().commit();
            
            assertNotEquals(supplierFromDB1, supplierFromDB2);
            
            
        } catch (UnsupportedTenancyException e) {
            e.printStackTrace();
        }
        
        
    }
}
