package com.baeldung.rmi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.BeforeClass;
import org.junit.Test;

public class JavaRMIIntegrationTest {
	
	@BeforeClass
	public static void whenRunServer_thenServerStarts() {
		
		try {
			MessengerServiceImpl server = new MessengerServiceImpl();
			server.createStubAndBind();
		} catch (RemoteException e) {
			fail("Exception Occurred: " + e);
		}
	}
	
	@Test
	public void whenClientSendsMessageToServer_thenServerSendsResponseMessage() {
		
		try {
			Registry registry = LocateRegistry.getRegistry();	
			MessengerService server = (MessengerService) registry.lookup("MessengerService");			
			String responseMessage = server.sendMessage("Client Message");		
			
			String expectedMessage = "Server Message";			
			assertEquals(responseMessage, expectedMessage);
		} catch (RemoteException e) {
			fail("Exception Occurred: " + e);
		} catch (NotBoundException nb) {
			fail("Exception Occurred: " + e);
		}
	}
	
}