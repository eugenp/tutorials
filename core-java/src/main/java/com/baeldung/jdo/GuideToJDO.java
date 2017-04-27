package com.baeldung.jdo;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.metadata.PersistenceUnitMetaData;

public class GuideToJDO {

    private static final Logger LOGGER = Logger.getLogger(GuideToJDO.class.getName());
    private Random rnd = new Random();
    private PersistenceUnitMetaData pumd;

    public static void main(String[] args) {
        new GuideToJDO();
    }

    public GuideToJDO() {
        CreateProperties();
        CreateProducts();
        ListProducts();
        UpdateProducts();
        ListProducts();
        DeleteProducts();
        ListProducts();
    }

    public void CreateProperties(){
        
        pumd = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
        pumd.addClassName("com.baeldung.jdo.Product");
        pumd.setExcludeUnlistedClasses();
        pumd.addProperty("javax.jdo.option.ConnectionDriverName", "org.h2.Driver");
        pumd.addProperty("javax.jdo.option.ConnectionURL", "jdbc:h2:mem:mypersistence");
        pumd.addProperty("javax.jdo.option.ConnectionUserName", "sa");
        pumd.addProperty("javax.jdo.option.ConnectionPassword", "");
        pumd.addProperty("datanucleus.autoCreateSchema", "true");        
        
    }
    
    public void CreateProducts() {
        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);
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

    @SuppressWarnings("rawtypes")
    public void UpdateProducts(){
        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Query query = pm.newQuery(Product.class, "name == \"Phone\"");
            Collection result = (Collection) query.execute();
            Product product = (Product) result.iterator().next();
            product.setName("Android Phone");
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }   
            pm.close();
        }
    }
    
    @SuppressWarnings("rawtypes")
    public void DeleteProducts(){
        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Query query = pm.newQuery(Product.class, "name == \"Android Phone\"");
            Collection result = (Collection) query.execute();
            Product product = (Product) result.iterator().next();
            pm.deletePersistent(product);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }   
            pm.close();
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void ListProducts() {
        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            Query q = pm.newQuery("SELECT FROM " + Product.class.getName() + " WHERE price > 10");
            List<Product> products = (List<Product>) q.execute();
            Iterator<Product> iter = products.iterator();
            while (iter.hasNext()) {
                Product p = iter.next();
                LOGGER.log(Level.WARNING, "Product name: {0} - Price: {1}", new Object[] { p.name, p.price });
            }
            LOGGER.log(Level.INFO, "--------------------------------------------------------------");
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }

            pm.close();
        }
    }
}