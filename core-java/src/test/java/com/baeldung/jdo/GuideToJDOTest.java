package com.baeldung.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.junit.Test;

public class GuideToJDOTest {
    @Test
    public void givenProduct_WhenNewThenPerformTransaction() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Tutorial");
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            for (int i = 0; i < 100; i++){
                String nam = "Product-" + i;
                double price = i;
                Product productx = new Product(nam, price);
                pm.makePersistent(productx);
            }
            tx.commit();
        } catch (Throwable thr) {
            fail("Failed test : " + thr.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        pmf.close();
    }

    @Test
    public void givenProduct_WhenQueryThenExist() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Tutorial");
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Product product = new Product("Tablet", 80.0);
            pm.makePersistent(product);
            Product product2 = new Product("Phone", 20.0);
            pm.makePersistent(product2);
            Product product3 = new Product("Laptop", 200.0);
            pm.makePersistent(product3);
            tx.commit();
        } catch (Throwable thr) {
            fail("Failed test : " + thr.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

        pmf.close();

        PersistenceManagerFactory pmf2 = JDOHelper.getPersistenceManagerFactory("Tutorial");
        PersistenceManager pm2 = pmf2.getPersistenceManager();
        Transaction tx2 = pm2.currentTransaction();
        try {
            tx2.begin();

            @SuppressWarnings("rawtypes")
            Query q = pm2.newQuery("SELECT FROM " + Product.class.getName() + " WHERE price == 200");
            @SuppressWarnings("unchecked")
            List<Product> products = (List<Product>) q.execute();
            Iterator<Product> iter = products.iterator();
            while (iter.hasNext()) {
                Product p = iter.next();
                assertEquals("Laptop", p.name);
            }

            tx2.commit();
        } finally {
            if (tx2.isActive()) {
                tx2.rollback();
            }

            pm2.close();
        }
    }


}
