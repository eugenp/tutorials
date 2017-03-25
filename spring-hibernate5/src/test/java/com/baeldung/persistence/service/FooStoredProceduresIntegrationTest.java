package com.baeldung.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import java.util.List;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.persistence.model.Foo;
import com.baeldung.spring.PersistenceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FooStoredProceduresIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooStoredProceduresIntegrationTest.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private IFooService fooService;

    private Session session;

    @Before
    public final void before() {
        session = sessionFactory.openSession();
        Assume.assumeTrue(getAllFoosExists());
        Assume.assumeTrue(getFoosByNameExists());
    }

    private boolean getFoosByNameExists() {
        try {
            session.createQuery("CALL GetAllFoos()", Foo.class).getResultList();
            return true;
        } catch (SQLGrammarException e) {
            LOGGER.error("WARNING : GetFoosByName() Procedure is may be missing ", e);
            return false;
        }
    }

    private boolean getAllFoosExists() {
        try {
            session.createQuery("CALL GetAllFoos()", Foo.class).getResultList();
            return true;
        } catch (SQLGrammarException e) {
            LOGGER.error("WARNING : GetAllFoos() Procedure is may be missing ", e);
            return false;
        }
    }

    @After
    public final void after() {
        session.close();
    }

    @Test
    public final void getAllFoosUsingStoredProcedures() {

        fooService.create(new Foo(randomAlphabetic(6)));

        // Stored procedure getAllFoos using createSQLQuery
        List<Foo> allFoos = session.createQuery("CALL GetAllFoos()", Foo.class).getResultList();
        for (Foo foo : allFoos) {
            LOGGER.info("getAllFoos() SQL Query result : {}", foo.getName());
        }
        assertEquals(allFoos.size(), fooService.findAll().size());

        // Stored procedure getAllFoos using a Named Query
        @SuppressWarnings("unchecked")
        List<Foo> allFoos2 = session.getNamedQuery("callGetAllFoos").getResultList();
        for (Foo foo : allFoos2) {
            LOGGER.info("getAllFoos() NamedQuery result : {}", foo.getName());
        }
        assertEquals(allFoos2.size(), fooService.findAll().size());
    }

    @Test
    public final void getFoosByNameUsingStoredProcedures() {

        fooService.create(new Foo("NewFooName"));

        // Stored procedure getFoosByName using createSQLQuery()
        List<Foo> allFoosByName = session.createQuery("CALL GetFoosByName(:fooName)", Foo.class).setParameter("fooName", "NewFooName").getResultList();
        for (Foo foo : allFoosByName) {
            LOGGER.info("getFoosByName() using SQL Query : found => {}", foo.toString());
        }

        // Stored procedure getFoosByName using getNamedQuery()
        @SuppressWarnings("unchecked")
        List<Foo> allFoosByName2 = session.getNamedQuery("callGetFoosByName").setParameter("fooName", "NewFooName").getResultList();
        for (Foo foo : allFoosByName2) {
            LOGGER.info("getFoosByName() using Native Query : found => {}", foo.toString());
        }

    }
}
