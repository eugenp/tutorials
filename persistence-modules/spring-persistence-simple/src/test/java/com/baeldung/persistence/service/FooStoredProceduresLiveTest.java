package com.baeldung.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.baeldung.config.PersistenceConfig;
import com.baeldung.persistence.model.Foo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FooStoredProceduresLiveTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooStoredProceduresLiveTest.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private FooService fooService;

    private Session session;

    @Before
    public final void before() {
        session = sessionFactory.openSession();
        Assume.assumeTrue(getAllFoosExists());
        Assume.assumeTrue(getFoosByNameExists());
    }

    private boolean getFoosByNameExists() {
        try {
            @SuppressWarnings("unchecked")
            NativeQuery<Foo> sqlQuery = session.createSQLQuery("CALL GetAllFoos()").addEntity(Foo.class);
            sqlQuery.list();
            return true;
        } catch (SQLGrammarException e) {
            LOGGER.error("WARNING : GetFoosByName() Procedure is may be missing ", e);
            return false;
        }
    }

    private boolean getAllFoosExists() {
        try {
            @SuppressWarnings("unchecked")
            NativeQuery<Foo> sqlQuery = session.createSQLQuery("CALL GetAllFoos()").addEntity(Foo.class);
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
    }

    @Test
    public final void getAllFoosUsingStoredProcedures() {

        fooService.create(new Foo(randomAlphabetic(6)));

        // Stored procedure getAllFoos using createSQLQuery
        @SuppressWarnings("unchecked")
        NativeQuery<Foo> sqlQuery = session.createSQLQuery("CALL GetAllFoos()").addEntity(Foo.class);
        List<Foo> allFoos = sqlQuery.list();
        for (Foo foo : allFoos) {
            LOGGER.info("getAllFoos() SQL Query result : {}", foo.getName());
        }
        assertEquals(allFoos.size(), fooService.findAll().size());

        // Stored procedure getAllFoos using a Named Query
        @SuppressWarnings("unchecked")
        Query<Foo> namedQuery = session.getNamedQuery("callGetAllFoos");
        List<Foo> allFoos2 = namedQuery.list();
        for (Foo foo : allFoos2) {
            LOGGER.info("getAllFoos() NamedQuery result : {}", foo.getName());
        }
        assertEquals(allFoos2.size(), fooService.findAll().size());
    }

    @Test
    public final void getFoosByNameUsingStoredProcedures() {

        fooService.create(new Foo("NewFooName"));

        // Stored procedure getFoosByName using createSQLQuery()
        @SuppressWarnings("unchecked")
        Query<Foo> sqlQuery = session.createSQLQuery("CALL GetFoosByName(:fooName)").addEntity(Foo.class).setParameter("fooName", "NewFooName");
        List<Foo> allFoosByName = sqlQuery.list();
        for (Foo foo : allFoosByName) {
            LOGGER.info("getFoosByName() using SQL Query : found => {}", foo.toString());
        }

        // Stored procedure getFoosByName using getNamedQuery()
        @SuppressWarnings("unchecked")
        Query<Foo> namedQuery = session.getNamedQuery("callGetFoosByName").setParameter("fooName", "NewFooName");
        List<Foo> allFoosByName2 = namedQuery.list();
        for (Foo foo : allFoosByName2) {
            LOGGER.info("getFoosByName() using Native Query : found => {}", foo.toString());
        }

    }
}
