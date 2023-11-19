package com.baeldung.jpa.projections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

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
        final CriteriaBuilder criteria = session.getCriteriaBuilder();
        final CriteriaQuery<Object[]> criteriaQuery = criteria.createQuery(Object[].class);
        final Root<Product> root = criteriaQuery.from(Product.class);
        final SingularAttribute<Product, String> name = Product_.name;
        final SingularAttribute<Product, Long> id = Product_.id;
        final Path<String> nameProjection = root.get(name);
        final Path<Long> idProjection = root.get(id);
        criteriaQuery.multiselect(idProjection, nameProjection);
        final List<Object[]> resultList = session.createQuery(criteriaQuery).getResultList();

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
        final CriteriaBuilder criteria = session.getCriteriaBuilder();
        final CriteriaQuery<String> criteriaQuery = criteria.createQuery(String.class);
        final Root<Product> root = criteriaQuery.from(Product.class);
        final SingularAttribute<Product, String> name = Product_.name;
        final Path<String> nameProjection = root.get(name);
        criteriaQuery.select(nameProjection);
        final List<String> resultList = session.createQuery(criteriaQuery).getResultList();

        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals("Product Name 1", resultList.get(0));
        assertEquals("Product Name 2", resultList.get(1));
        assertEquals("Product Name 3", resultList.get(2));
        assertEquals("Product Name 4", resultList.get(3));
    }
    
    @Test
    public void givenProductData_whenCountByCategoryUsingCriteria_thenOK() {
        final CriteriaBuilder criteria = session.getCriteriaBuilder();
        final CriteriaQuery<Object[]> criteriaQuery = criteria.createQuery(Object[].class);
        final Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.groupBy(root.get("category"));
        criteriaQuery.multiselect(root.get("category"), criteria.count(root));
        final List<Object[]> resultList = session.createQuery(criteriaQuery).getResultList();

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
        final CriteriaBuilder criteria = session.getCriteriaBuilder();
        final CriteriaQuery<Object[]> criteriaQuery = criteria.createQuery(Object[].class);
        final Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.groupBy(root.get("category"));
        criteriaQuery.multiselect(root.get("category"), criteria.count(root));
        criteriaQuery.orderBy(criteria.asc(criteria.count(root)));
        List<Object[]> resultList = session.createQuery(criteriaQuery).getResultList();

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
