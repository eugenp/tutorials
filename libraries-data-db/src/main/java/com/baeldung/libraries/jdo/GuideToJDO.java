package com.baeldung.libraries.jdo;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.metadata.PersistenceUnitMetaData;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuideToJDO {

    private static final Logger LOGGER = Logger.getLogger(GuideToJDO.class.getName());
    private Random rnd = new Random();
    private PersistenceUnitMetaData pumd;
    private PersistenceUnitMetaData pumdXML;

    public static void main(String[] args) {
        new GuideToJDO();
    }

    public GuideToJDO() {
        CreateH2Properties();
        CreateXMLProperties();
        CreateProducts();
        ListProducts();
        QueryJDOQL();
        QuerySQL();
        QueryJPQL();
        UpdateProducts();
        ListProducts();
        DeleteProducts();
        ListProducts();
        persistXML();
        listXMLProducts();
    }

    public void CreateH2Properties() {

        pumd = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
        pumd.addClassName("com.baeldung.libraries.jdo.Product");
        pumd.setExcludeUnlistedClasses(true);
        pumd.addProperty("javax.jdo.option.ConnectionDriverName", "org.h2.Driver");
        pumd.addProperty("javax.jdo.option.ConnectionURL", "jdbc:h2:mem:mypersistence");
        pumd.addProperty("javax.jdo.option.ConnectionUserName", "sa");
        pumd.addProperty("javax.jdo.option.ConnectionPassword", "");
        pumd.addProperty("datanucleus.autoCreateSchema", "true");

    }

    public void CreateXMLProperties() {
        pumdXML = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
        pumdXML.addClassName("com.baeldung.libraries.jdo.ProductXML");
        pumdXML.setExcludeUnlistedClasses(true);
        pumdXML.addProperty("javax.jdo.option.ConnectionURL", "xml:file:myPersistence.xml");
        pumdXML.addProperty("datanucleus.autoCreateSchema", "true");
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
    public void UpdateProducts() {
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
    public void DeleteProducts() {
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void QueryJDOQL() {
        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            // Declarative JDOQL :
            LOGGER.log(Level.INFO, "Declarative JDOQL --------------------------------------------------------------");
            Query qDJDOQL = pm.newQuery(Product.class);
            qDJDOQL.setFilter("name == 'Tablet' && price == price_value");
            qDJDOQL.declareParameters("double price_value");
            List<Product> resultsqDJDOQL = qDJDOQL.setParameters(80.0).executeList();

            Iterator<Product> iterDJDOQL = resultsqDJDOQL.iterator();
            while (iterDJDOQL.hasNext()) {
                Product p = iterDJDOQL.next();
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void QuerySQL() {
        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            // SQL :
            LOGGER.log(Level.INFO, "SQL --------------------------------------------------------------");
            Query query = pm.newQuery("javax.jdo.query.SQL", "SELECT * FROM PRODUCT");
            query.setClass(Product.class);
            List<Product> results = query.executeList();

            Iterator<Product> iter = results.iterator();
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void QueryJPQL() {
        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            // JPQL :
            LOGGER.log(Level.INFO, "JPQL --------------------------------------------------------------");
            Query q = pm.newQuery("JPQL", "SELECT p FROM " + Product.class.getName() + " p WHERE p.name = 'Laptop'");
            List results = (List) q.execute();

            Iterator<Product> iter = results.iterator();
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

    public void persistXML() {
        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumdXML, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            ProductXML productXML = new ProductXML(0, "Tablet", 80.0);
            pm.makePersistent(productXML);
            ProductXML productXML2 = new ProductXML(1, "Phone", 20.0);
            pm.makePersistent(productXML2);
            ProductXML productXML3 = new ProductXML(2, "Laptop", 200.0);
            pm.makePersistent(productXML3);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void listXMLProducts() {
        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumdXML, null);
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();

            Query q = pm.newQuery("SELECT FROM " + ProductXML.class.getName());
            List<ProductXML> products = (List<ProductXML>) q.execute();
            Iterator<ProductXML> iter = products.iterator();
            while (iter.hasNext()) {
                ProductXML p = iter.next();
                LOGGER.log(Level.WARNING, "Product name: {0} - Price: {1}", new Object[] { p.getName(), p.getPrice() });
                pm.deletePersistent(p);
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