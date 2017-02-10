package com.baeldung.hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import com.baeldung.hibernate.pojo.Suppliers;


public class MultiTenantHibernateTest  {
    @Test
    public void givenDBMode_whenFetchingSuppliers_thenFetchingFirstElement () {
        SessionFactory sessionFactory;
        try {
            sessionFactory = HibernateMultiTenantUtil.getSessionFactory();
            
            Session db1Session = sessionFactory
                .withOptions().tenantIdentifier("db1").openSession();
            
            String supplier = (String) db1Session.createCriteria(Suppliers.class).list().stream().findFirst().get();
            System.out.println(supplier);
            
            
            
        } catch (UnsupportedTenancyException e) {
            e.printStackTrace();
        }
        
        
    }
}
