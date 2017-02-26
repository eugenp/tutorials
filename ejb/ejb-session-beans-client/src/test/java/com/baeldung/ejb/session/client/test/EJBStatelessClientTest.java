package com.baeldung.ejb.session.client.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.baeldung.ejb.session.client.EJBStatelessClient;

public class EJBStatelessClientTest {

	@Test
	public void EJBClientTest() {
		EJBStatelessClient ejbStatelessClient = new EJBStatelessClient();
		assertTrue(ejbStatelessClient.getEJBRemoteMessage());
	}

}
