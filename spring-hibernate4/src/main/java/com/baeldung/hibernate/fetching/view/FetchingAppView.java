package com.baeldung.hibernate.fetching.view;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.baeldung.hibernate.fetching.util.HibernateUtil;

import com.baeldung.hibernate.fetching.model.OrderDetail;

import com.baeldung.hibernate.fetching.model.User;


public class FetchingAppView {

	public FetchingAppView(){
		
	}
	
	//lazily loaded 
	public Set<OrderDetail> lazyLoaded(){
		final Session sessionLazy = HibernateUtil.getHibernateSession("lazy");
		List<User> users = sessionLazy.createQuery("From User").list();
		User userLazyLoaded = new User();
		userLazyLoaded = users.get(3); 
		//since data is lazyloaded so data won't be initialized
		Set<OrderDetail> orderDetailSet = userLazyLoaded.getOrderDetail();
		return (orderDetailSet);	
	}
	
	//eagerly loaded
	public Set<OrderDetail> eagerLoaded(){
		final Session sessionEager = HibernateUtil.getHibernateSession();
		//data should be loaded in the following line
		//also note the queries generated
		List<User> users =sessionEager.createQuery("From User").list();
		User userEagerLoaded = new User();
		userEagerLoaded = users.get(3); 
		Set<OrderDetail> orderDetailSet = userEagerLoaded.getOrderDetail();
		return orderDetailSet;	
	}
	
	
	//creates test data
	//call this method to create the data in the database
	public void createTestData() {

		final Session session = HibernateUtil.getHibernateSession();
		Transaction tx = null;
		tx = session.beginTransaction();
		final User user1 = new User();
		final User user2 = new User();
		final User user3 = new User();
				
		user1.setFirstName("Priyam");
		user1.setLastName("Banerjee");
		user1.setUserName("priyambanerjee");
		session.save(user1);
		
		user2.setFirstName("Navneeta");
		user2.setLastName("Mukherjee");
		user2.setUserName("nmukh");
		session.save(user2);
		
		user3.setFirstName("Molly");
		user3.setLastName("Banerjee");
		user3.setUserName("mollyb");
		session.save(user3);

		final OrderDetail order1 = new OrderDetail();
		final OrderDetail order2 = new OrderDetail();
		final OrderDetail order3 = new OrderDetail();
		final OrderDetail order4 = new OrderDetail();
		final OrderDetail order5 = new OrderDetail();

		order1.setOrderDesc("First Order");
		order1.setOrderDate(new Date(2014, 10, 12));
		order1.setUser(user1);
		
		order2.setOrderDesc("Second Order");
		order2.setOrderDate(new Date(2016, 10, 25));
		order2.setUser(user1);
		
		order3.setOrderDesc("Third Order");
		order3.setOrderDate(new Date(2015, 2, 17));
		order3.setUser(user2);
		
		order4.setOrderDesc("Fourth Order");
		order4.setOrderDate(new Date(2014, 10, 1));
		order4.setUser(user2);
		
		order5.setOrderDesc("Fifth Order");
		order5.setOrderDate(new Date(2014, 9, 11));
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
