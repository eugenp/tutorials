package com.baeldung.persistence.service;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.persistence.model.Foo;
import com.baeldung.spring.PersistenceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FooStoredProceduresIntegrationTest {

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
			Query sqlQuery = session.createSQLQuery("CALL GetAllFoos()")
					.addEntity(Foo.class);
			sqlQuery.list();
			return true;
		} catch (SQLGrammarException e) {
			System.out
					.println("WARNING : GetFoosByName() Procedure is may be missing ");
			return false;
		}
	}

	private boolean getAllFoosExists() {
		try {
			Query sqlQuery = session.createSQLQuery("CALL GetAllFoos()")
					.addEntity(Foo.class);
			sqlQuery.list();
			return true;
		} catch (SQLGrammarException e) {
			System.out
					.println("WARNING : GetAllFoos() Procedure is may be missing ");
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
		Query sqlQuery = session.createSQLQuery("CALL GetAllFoos()").addEntity(
				Foo.class);
		@SuppressWarnings("unchecked")
		List<Foo> allFoos = sqlQuery.list();
		for (Foo foo : allFoos) {
			System.out.println("getAllFoos() SQL Query result : "
					+ foo.getName());
		}
		assertEquals(allFoos.size(), fooService.findAll().size());

		// Stored procedure getAllFoos using a Named Query
		Query namedQuery = session.getNamedQuery("callGetAllFoos");
		@SuppressWarnings("unchecked")
		List<Foo> allFoos2 = namedQuery.list();
		for (Foo foo : allFoos2) {
			System.out.println("getAllFoos() NamedQuery result : "
					+ foo.getName());
		}
		assertEquals(allFoos2.size(), fooService.findAll().size());
	}

	@Test
	public final void getFoosByNameUsingStoredProcedures() {

		fooService.create(new Foo("NewFooName"));

		// Stored procedure getFoosByName using createSQLQuery()
		Query sqlQuery = session.createSQLQuery("CALL GetFoosByName(:fooName)")
				.addEntity(Foo.class).setParameter("fooName", "NewFooName");
		@SuppressWarnings("unchecked")
		List<Foo> allFoosByName = sqlQuery.list();
		for (Foo foo : allFoosByName) {
			System.out.println("getFoosByName() using SQL Query : found => "
					+ foo.toString());
		}

		// Stored procedure getFoosByName using getNamedQuery()
		Query namedQuery = session.getNamedQuery("callGetFoosByName")
				.setParameter("fooName", "NewFooName");
		@SuppressWarnings("unchecked")
		List<Foo> allFoosByName2 = namedQuery.list();
		for (Foo foo : allFoosByName2) {
			System.out.println("getFoosByName() using Native Query : found => "
					+ foo.toString());
		}

	}
}
