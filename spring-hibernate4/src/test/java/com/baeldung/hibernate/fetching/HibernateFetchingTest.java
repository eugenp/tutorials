package com.baeldung.hibernate.fetching;

import static org.junit.Assert.*;
import java.util.Set;

import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.fetching.model.OrderDetailEager;
import com.baeldung.hibernate.fetching.model.OrderDetailLazy;
import com.baeldung.hibernate.fetching.view.FetchingAppView;

public class HibernateFetchingTest {


	//this loads sample data in the database
//	@Before 
	public void addFecthingTestData(){
		FetchingAppView fav = new FetchingAppView();
		fav.createTestData();
	}
	
	//testLazyFetching() tests the lazy loading
	//Since it lazily loaded so orderDetalSetLazy won't
	//be initialized
	@Test
	public void testLazyFetching() {
		FetchingAppView fav = new FetchingAppView();
		Set<OrderDetailLazy> orderDetalSetLazy = fav.lazyLoaded();
		assertFalse(Hibernate.isInitialized(orderDetalSetLazy));
	}
	
	//testEagerFetching() tests the eager loading
	//Since it eagerly loaded so orderDetalSetLazy would
	//be initialized
	@Test
	public void testEagerFetching() {
		FetchingAppView fav = new FetchingAppView();
		Set<OrderDetailEager> orderDetalSetEager = fav.eagerLoaded();
		assertTrue(Hibernate.isInitialized(orderDetalSetEager));
	}
}
