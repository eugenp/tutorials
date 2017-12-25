package com.baeldung.rmi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.Test;

public class JavaRMITest {
	
	@Test
	public void WhenRunServer_ServerIsStarted() {
		
		try {
			
			ServerInterface server = new ServerImpl();
			
			ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject((ServerInterface) server, 0);
			
			Registry registry = LocateRegistry.createRegistry(1099);
			
			registry.rebind("Server", stub);
			
			assertTrue(true);
			
		} catch (RemoteException e) {
			fail("Exception Occured");
		}
	}
	
	@Test
	public void WhenClientSendsMessageToServer_ServerSendsResponseMessage() {
		
		try {
			
			Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
			
			ServerInterface server = (ServerInterface) registry.lookup("Server");
			
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
