package com.baeldung.hibernate.oneToMany.main;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.baeldung.hibernate.oneToMany.model.Cart;
import com.baeldung.hibernate.oneToMany.model.Items;

public class HibernateOneToManyAnnotationMainIntegrationTest {

	private static SessionFactory sessionFactory;

	private Session session;

	public HibernateOneToManyAnnotationMainIntegrationTest() {
	}

	@BeforeClass
	public static void beforeTests() {
		Configuration configuration = new Configuration().addAnnotatedClass(Cart.class).addAnnotatedClass(Items.class)
				.setProperty("hibernate.dialect", HSQLDialect.class.getName())
				.setProperty("hibernate.connection.driver_class", org.hsqldb.jdbcDriver.class.getName())
				.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:test")
				.setProperty("hibernate.connection.username", "sa").setProperty("hibernate.connection.password", "")
				.setProperty("hibernate.hbm2ddl.auto", "update");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	@Before
	public void setUp() {
		session = sessionFactory.openSession();
		session.beginTransaction();
	}

	@Test
	public void givenSession_checkIfDatabaseIsEmpty() {
		Cart cart = (Cart) session.get(Cart.class, new Long(1));
		assertNull(cart);

	}

	@Test
	public void givenSession_checkIfDatabaseIsPopulated_afterCommit() {
		Cart cart = new Cart();
		Set<Items> cartItems = new HashSet<>();
		cartItems = cart.getItems();
		Assert.assertNull(cartItems);
		Items item1 = new Items();
		item1.setCart(cart);
		assertNotNull(item1);
		Set<Items> itemsSet = new HashSet<Items>();
		itemsSet.add(item1);
		assertNotNull(itemsSet);
		cart.setItems(itemsSet);
		assertNotNull(cart);
		session.persist(cart);
		session.getTransaction().commit();
		session.close();

		session = sessionFactory.openSession();
		session.beginTransaction();
		cart = (Cart) session.get(Cart.class, new Long(1));
		assertNotNull(cart);
	}

	@After
	public void tearDown() {
		session.getTransaction().commit();
		session.close();
	}

	@AfterClass
	public static void afterTests() {
		sessionFactory.close();
	}

}
