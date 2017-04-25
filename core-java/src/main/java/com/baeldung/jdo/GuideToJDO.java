package com.baeldung.jdo;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

public class GuideToJDO {

    private static final Logger LOGGER = Logger.getLogger(GuideToJDO.class.getName());
    private Random rnd = new Random();

    public static void main(String[] args) {
        new GuideToJDO();
    }

    public GuideToJDO() {
        CreateProducts();
        ListProducts();
    }

    public void CreateProducts() {
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
            for (int i = 0; i < 100; i++) {
                String nam = "Product-" + i;
                double price = rnd.nextDouble();
                Product productx = new Product(nam, price);
                pm.makePersistent(productx);
            }
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

    }

    @SuppressWarnings("unchecked")
    public void ListProducts() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Tutorial");
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            @SuppressWarnings("rawtypes")
            Query q = pm.newQuery("SELECT FROM " + Product.class.getName() + " WHERE price < 1");
            List<Product> products = (List<Product>) q.execute();
            Iterator<Product> iter = products.iterator();
            while (iter.hasNext()) {
                Product p = iter.next();
                LOGGER.log(Level.WARNING, "Product name: {0} - Price: {1}", new Object[] { p.name, p.price });
            }

            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }

            pm.close();
        }
    }
}
