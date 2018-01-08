package com.baeldung.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClientImpl {

	public static void main(String[] args) {
		
		try {
			
			Registry registry = LocateRegistry.getRegistry();
			MessengerService server = (MessengerService) registry.lookup("MessengerService");
			String responseMessage = server.sendMessage("Client Message");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException nb) {
			nb.printStackTrace();
		}
	}
}
