package com.baeldung.hibernate.fetching.view;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.baeldung.hibernate.fetching.util.HibernateUtil;
import com.baeldung.hibernate.fetching.model.OrderDetailEager;
import com.baeldung.hibernate.fetching.model.OrderDetailLazy;

import com.baeldung.hibernate.fetching.model.UserLazy;
import com.baeldung.hibernate.fetching.model.UserEager;

public class FetchingAppView {

	public FetchingAppView() {

	}

	// lazily loaded
	public Set<OrderDetailLazy> lazyLoaded() {
		final Session sessionLazy = HibernateUtil.getHibernateSession("lazy");
		List<UserLazy> users = sessionLazy.createQuery("From UserLazy").list();
		UserLazy userLazyLoaded = new UserLazy();
		userLazyLoaded = users.get(3);
		// since data is lazyloaded so data won't be initialized
		Set<OrderDetailLazy> orderDetailSet = userLazyLoaded.getOrderDetail();
		return (orderDetailSet);
	}

	// eagerly loaded
	public Set<OrderDetailEager> eagerLoaded() {
		final Session sessionEager = HibernateUtil.getHibernateSession();
		// data should be loaded in the following line
		// also note the queries generated
		List<UserEager> user = sessionEager.createQuery("From UserEager").list();
		UserEager userEagerLoaded = new UserEager();
		userEagerLoaded = user.get(3);
		Set<OrderDetailEager> orderDetailSet = userEagerLoaded.getOrderDetail();
		return orderDetailSet;
	}

	// creates test data
	// call this method to create the data in the database
	public void createTestData() {

		final Session session = HibernateUtil.getHibernateSession("lazy");
		Transaction tx = null;
		tx = session.beginTransaction();
		final UserLazy user1 = new UserLazy();
		final UserLazy user2 = new UserLazy();
		final UserLazy user3 = new UserLazy();

		session.save(user1);
		session.save(user2);
		session.save(user3);

		final OrderDetailLazy order1 = new OrderDetailLazy();
		final OrderDetailLazy order2 = new OrderDetailLazy();
		final OrderDetailLazy order3 = new OrderDetailLazy();
		final OrderDetailLazy order4 = new OrderDetailLazy();
		final OrderDetailLazy order5 = new OrderDetailLazy();

		order1.setUser(user1);
		order2.setUser(user1);
		order3.setUser(user2);
		order4.setUser(user2);
		order5.setUser(user3);

		session.saveOrUpdate(order1);
		session.saveOrUpdate(order2);
		session.saveOrUpdate(order3);
		session.saveOrUpdate(order4);
		session.saveOrUpdate(order5);

		tx.commit();
		session.close();

	}
}
