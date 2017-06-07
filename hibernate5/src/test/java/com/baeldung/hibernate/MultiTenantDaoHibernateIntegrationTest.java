package com.baeldung.hibernate;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.junit.Test;

import com.baeldung.hibernate.dao.SupplierDao;
import com.baeldung.hibernate.pojo.Supplier;

import static org.junit.Assert.assertNull;;

public class MultiTenantDaoHibernateIntegrationTest {
    @Test
    public void givenDBMode_whenFetchingSuppliersByName_thenChecking() throws UnsupportedTenancyException, IOException {
        SessionFactory sessionFactory = HibernateMultiTenantUtil.getSessionFactory();
        
        SupplierDao myDb1Dao = new SupplierDao(sessionFactory, "mydb1");
        Supplier db1SupplierName = myDb1Dao.findByName("John");
        
        // finding the same supplier name in another tenant
        // and we should not be able to find in there and both dbs are different.
        SupplierDao myDb2Dao = new SupplierDao(sessionFactory, "mydb2");
        Supplier db2SupplierName = myDb2Dao.findByName(db1SupplierName.getName());
        
        assertNull(db2SupplierName);
        
    }

}
