package com.baeldung.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClientImpl {

	public static void main(String[] args) {
		
		try {
			
			String rmiHostname = args[0];
			int rmiPortNumber = Integer.valueOf(args[1]);
			Registry registry = LocateRegistry.getRegistry(rmiHostname, rmiPortNumber);
			MessengerInterface server = (MessengerInterface) registry.lookup("Server");
			String responseMessage = server.sendMessage("Client Message");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException nb) {
			nb.printStackTrace();
		}
	}
}
