package com.baeldung.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl implements ServerInterface {

	@Override
	public String sendMessage(String clientMessage) throws RemoteException {
		
		String serverMessage = null;
		if (clientMessage.equals("Client Message"))
			serverMessage = "Server Message";
		
		return serverMessage;
	}
	
	public static void main(String[] args) {
		
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
		
		try {
			
			ServerInterface server = new ServerImpl();
			
			ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject((ServerInterface) server, 0);
			
			Registry registry = LocateRegistry.createRegistry(1099);
			
			registry.rebind("Server", stub);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
}
