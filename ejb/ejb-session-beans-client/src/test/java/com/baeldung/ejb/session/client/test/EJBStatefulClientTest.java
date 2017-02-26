package com.baeldung.ejb.session.client.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.ejb.session.client.EJBStatefulClient;

public class EJBStatefulClientTest {

	@Test
	public void EJBClientTest() {
		EJBStatefulClient ejbStatefulClient = new EJBStatefulClient();
		assertFalse(ejbStatefulClient.getEJBRemoteMessage());
	}

}
