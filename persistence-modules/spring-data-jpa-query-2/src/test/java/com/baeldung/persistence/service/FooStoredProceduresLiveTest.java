package com.baeldung.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.config.PersistenceConfig;
import com.baeldung.persistence.model.Foo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FooStoredProceduresLiveTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooStoredProceduresLiveTest.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private IFooService fooService;

    private Session session;

    @Autowired
    @Qualifier("jpaEntityManager")
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    
    @Before
    public final void before() {
    	entityManager = entityManagerFactory.createEntityManager();
        session = sessionFactory.openSession();
        Assume.assumeTrue(getAllFoosExists());
        Assume.assumeTrue(getFoosByNameExists());
    }

    private boolean getFoosByNameExists() {
        try {
            Query sqlQuery = session.createSQLQuery("CALL GetAllFoos()").addEntity(Foo.class);
            sqlQuery.list();
            return true;
        } catch (SQLGrammarException e) {
            LOGGER.error("WARNING : GetFoosByName() Procedure is may be missing ", e);
            return false;
        }
    }

    private boolean getAllFoosExists() {
        try {
            Query sqlQuery = session.createSQLQuery("CALL GetAllFoos()").addEntity(Foo.class);
            sqlQuery.list();
            return true;
        } catch (SQLGrammarException e) {
            LOGGER.error("WARNING : GetAllFoos() Procedure is may be missing ", e);
            return false;
        }
    }

    @After
    public final void after() {
        session.close();
        entityManager.close();
    }

    @Test
    public final void getAllFoosUsingStoredProcedures() {

        fooService.create(new Foo(randomAlphabetic(6)));

        // Stored procedure getAllFoos using createSQLQuery
        Query sqlQuery = session.createSQLQuery("CALL GetAllFoos()").addEntity(Foo.class);
        @SuppressWarnings("unchecked")
        List<Foo> allFoos = sqlQuery.list();
        for (Foo foo : allFoos) {
            LOGGER.info("getAllFoos() SQL Query result : {}", foo.getName());
        }
        assertEquals(allFoos.size(), fooService.findAll().size());

        // Stored procedure getAllFoos using a Named Query
        Query namedQuery = session.getNamedQuery("callGetAllFoos");
        @SuppressWarnings("unchecked")
        List<Foo> allFoos2 = namedQuery.list();
        for (Foo foo : allFoos2) {
            LOGGER.info("getAllFoos() NamedQuery result : {}", foo.getName());
        }
        assertEquals(allFoos2.size(), fooService.findAll().size());
        
        StoredProcedureQuery spQuery = 
        		entityManager.createNamedStoredProcedureQuery("GetAllFoos");
		List<Foo> allFoos3 = spQuery.getResultList();
        for (Foo foo : allFoos3) {
            LOGGER.info("getAllFoos() StoredProcedureQuery result : {}", foo.getName());
        }
        assertEquals(allFoos3.size(), fooService.findAll().size());
        
    }

    @Test
    public final void getFoosByNameUsingStoredProcedures() {

        fooService.create(new Foo("NewFooName"));

        // Stored procedure getFoosByName using createSQLQuery()
        Query sqlQuery = session.createSQLQuery("CALL GetFoosByName(:fooName)").addEntity(Foo.class).setParameter("fooName", "NewFooName");
        @SuppressWarnings("unchecked")
        List<Foo> allFoosByName = sqlQuery.list();
        for (Foo foo : allFoosByName) {
            LOGGER.info("getFoosByName() using SQL Query : found => {}", foo.toString());
        }

        // Stored procedure getFoosByName using getNamedQuery()
        Query namedQuery = session.getNamedQuery("callGetFoosByName").setParameter("fooName", "NewFooName");
        @SuppressWarnings("unchecked")
        List<Foo> allFoosByName2 = namedQuery.list();
        for (Foo foo : allFoosByName2) {
            LOGGER.info("getFoosByName() using Native Query : found => {}", foo.toString());
        }
        
        StoredProcedureQuery spQuery = entityManager.
        		  createNamedStoredProcedureQuery("GetFoosByName")
        		  .setParameter("fooName", "NewFooName");
        List<Foo> allFoosByName3 = spQuery.getResultList();
        assertEquals(1, allFoosByName3.size());
		for (Foo foo : allFoosByName3) {
			LOGGER.info("getFoosByName() using StoredProcedureQuery : found => {}", foo.toString());
			assertEquals("NewFooName", foo.getName());
		}
    }
}
