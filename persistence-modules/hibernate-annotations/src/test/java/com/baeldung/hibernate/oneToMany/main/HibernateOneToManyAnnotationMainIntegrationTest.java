package com.baeldung.hibernate.oneToMany.main;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.baeldung.hibernate.oneToMany.config.HibernateAnnotationUtil;
import com.baeldung.hibernate.oneToMany.model.Cart;
import com.baeldung.hibernate.oneToMany.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


public class HibernateOneToManyAnnotationMainIntegrationTest {

	private static SessionFactory sessionFactory;

	private Session session;

	@BeforeAll
	public static void beforeTests() {
		sessionFactory = HibernateAnnotationUtil.getSessionFactory();
	}

	@BeforeEach
	public void setUp() {
		session = sessionFactory.openSession();
		session.beginTransaction();
	}

	@Test
	public void givenSession_checkIfDatabaseIsEmpty() {
		Cart cart = session.find(Cart.class, 1L);
		Assertions.assertNull(cart);
	}

	@Test
	public void givenSession_checkIfDatabaseIsPopulated_afterCommit() {
		Cart cart = new Cart();
		Set<Item> cartItems = cart.getItems();

		Assertions.assertNull(cartItems);

		Item item1 = new Item();
		item1.setCart(cart);

		assertNotNull(item1);

		Set<Item> itemSet = new HashSet<>();
		itemSet.add(item1);

		assertNotNull(itemSet);
		cart.setItems(itemSet);

		assertNotNull(cart);

		session.persist(cart);
		session.getTransaction().commit();
		session.close();

		session = sessionFactory.openSession();
		session.beginTransaction();
		cart = session.find(Cart.class, 1L);

		assertNotNull(cart);
	}

	@AfterEach
	public void tearDown() {
		session.getTransaction().commit();
		session.close();
	}

	@AfterAll
	public static void afterTests() {
		sessionFactory.close();
	}

}
