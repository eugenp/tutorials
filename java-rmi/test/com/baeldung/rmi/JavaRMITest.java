package com.baeldung.rmi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.Test;

public class JavaRMITest {
	
	@Test
	public void WhenRunServer_ServerIsStarted() {
		
		try {
			MessengerServiceImpl server = new MessengerServiceImpl();
			server.createStubAndbind();
			assertTrue(true);
		} catch (RemoteException e) {
			fail("Exception Occured");
		}
	}
	
	@Test
	public void WhenClientSendsMessageToServer_ServerSendsResponseMessage() {
		
		try {
			Registry registry = LocateRegistry.getRegistry();	
			MessengerService server = (MessengerService) registry.lookup("MessengerService");			
			String responseMessage = server.sendMessage("Client Message");			
			String expectedMessage = "Server Message";			
			assertEquals(responseMessage, expectedMessage);
		} catch (RemoteException e) {
			fail("Exception Occured");
		} catch (NotBoundException nb) {
			fail("Exception Occured");
		}
	}
	
}
