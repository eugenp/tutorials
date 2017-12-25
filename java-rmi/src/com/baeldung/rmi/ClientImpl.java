package com.baeldung.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientImpl {

	public static void main(String[] args) {
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			
			Registry registry = LocateRegistry.getRegistry(args[0], Integer.valueOf(args[1]));
			
			ServerInterface server = (ServerInterface) registry.lookup("Server");
			
			String responseMessage = server.sendMessage("Client Message");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException nb) {
			nb.printStackTrace();
		}
	}
	
}
