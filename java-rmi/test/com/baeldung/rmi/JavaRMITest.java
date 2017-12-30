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
			MessengerImpl server = new MessengerImpl();
			server.createStubAndbind(1099);
			assertTrue(true);
		} catch (RemoteException e) {
			fail("Exception Occured");
		}
	}
	
	@Test
	public void WhenClientSendsMessageToServer_ServerSendsResponseMessage() {
		
		try {
			String rmiHostname = "127.0.0.1";
			int rmiPortNumber = 1099;			
			Registry registry = LocateRegistry.getRegistry(rmiHostname, rmiPortNumber);			
			MessengerInterface server = (MessengerInterface) registry.lookup("Server");			
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
