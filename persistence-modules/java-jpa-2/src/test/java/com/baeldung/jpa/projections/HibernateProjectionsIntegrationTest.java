package com.baeldung.jpa.projections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateProjectionsIntegrationTest {
    private static Session session;
    private static SessionFactory sessionFactory;
    private Transaction transaction;

    @BeforeClass
    public static void init() {
        Configuration configuration = getConfiguration();
        configuration.addAnnotatedClass(Product.class);
        sessionFactory = configuration.buildSessionFactory();
    }    
    
    @Before
    public void before() {
        session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();        
    }   
    
    @After
    public void after() {
        if(transaction.isActive()) {
            transaction.rollback();
        }
    }  

    private static Configuration getConfiguration() {
        Configuration cfg = new Configuration();
        cfg.setProperty(AvailableSettings.DIALECT,
            "org.hibernate.dialect.H2Dialect");
        cfg.setProperty(AvailableSettings.HBM2DDL_AUTO, "none");
        cfg.setProperty(AvailableSettings.DRIVER, "org.h2.Driver");
        cfg.setProperty(AvailableSettings.URL,
            "jdbc:h2:mem:myexceptiondb2;DB_CLOSE_DELAY=-1;;INIT=RUNSCRIPT FROM 'src/test/resources/products.sql'");
        cfg.setProperty(AvailableSettings.USER, "sa");
        cfg.setProperty(AvailableSettings.PASS, "");
        cfg.setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        return cfg;
    }
    
    
    @SuppressWarnings("deprecation")
    @Test
    public void givenProductData_whenIdAndNameProjectionUsingCriteria_thenListOfObjectArrayReturned() {
        Criteria criteria = session.createCriteria(Product.class);
        criteria = criteria.setProjection(Projections.projectionList()
            .add(Projections.id())
            .add(Projections.property("name")));
        List<Object[]> resultList = criteria.list();

        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals(1L, resultList.get(0)[0]);
        assertEquals("Product Name 1", resultList.get(0)[1]);
        assertEquals(2L, resultList.get(1)[0]);
        assertEquals("Product Name 2", resultList.get(1)[1]);
        assertEquals(3L, resultList.get(2)[0]);
        assertEquals("Product Name 3", resultList.get(2)[1]);
        assertEquals(4L, resultList.get(3)[0]);
        assertEquals("Product Name 4", resultList.get(3)[1]);
    }
    
    
    @Test
    public void givenProductData_whenNameProjectionUsingCriteria_thenListOfStringReturned() {
        Criteria criteria = session.createCriteria(Product.class);
        criteria = criteria.setProjection(Projections.property("name"));
        List resultList = criteria.list();

        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals("Product Name 1", resultList.get(0));
        assertEquals("Product Name 2", resultList.get(1));
        assertEquals("Product Name 3", resultList.get(2));
        assertEquals("Product Name 4", resultList.get(3));
    }
    
    @Test
    public void givenProductData_whenCountByCategoryUsingCriteria_thenOK() {
        Criteria criteria = session.createCriteria(Product.class);
        criteria = criteria.setProjection(Projections.projectionList()
            .add(Projections.groupProperty("category"))
            .add(Projections.rowCount()));
        List<Object[]> resultList = criteria.list();

        assertNotNull(resultList);
        assertEquals(3, resultList.size());
        assertEquals("category1", resultList.get(0)[0]);
        assertEquals(2L, resultList.get(0)[1]);
        assertEquals("category2", resultList.get(1)[0]);
        assertEquals(1L, resultList.get(1)[1]);
        assertEquals("category3", resultList.get(2)[0]);
        assertEquals(1L, resultList.get(2)[1]);
    }
    
    @Test
    public void givenProductData_whenCountByCategoryWithAliasUsingCriteria_thenOK() {
        Criteria criteria = session.createCriteria(Product.class);
        criteria = criteria.setProjection(Projections.projectionList()
            .add(Projections.groupProperty("category"))
            .add(Projections.alias(Projections.rowCount(), "count")));
        criteria.addOrder(Order.asc("count"));
        List<Object[]> resultList = criteria.list();

        assertNotNull(resultList);
        assertEquals(3, resultList.size());
        assertEquals("category2", resultList.get(0)[0]);
        assertEquals(1L, resultList.get(0)[1]);
        assertEquals("category3", resultList.get(1)[0]);
        assertEquals(1L, resultList.get(1)[1]);
        assertEquals("category1", resultList.get(2)[0]);
        assertEquals(2L, resultList.get(2)[1]);
    }
}
