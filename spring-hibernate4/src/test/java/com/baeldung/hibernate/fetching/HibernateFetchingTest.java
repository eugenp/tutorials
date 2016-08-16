package com.baeldung.hibernate.fetching;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.hibernate.fetching.view.FetchingAppView;

public class HibernateFetchingTest {

	@Test
	public void testLazyFetching() {
		FetchingAppView fav = new FetchingAppView();
		fav.createTestData();
		assertFalse(fav.lazyLoaded());
	}

	@Test
	public void testEagerFetching() {
		FetchingAppView fav = new FetchingAppView();
		assertTrue(fav.eagerLoaded());
	}

}
