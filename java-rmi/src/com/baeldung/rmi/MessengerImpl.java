package com.baeldung.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MessengerImpl implements MessengerInterface {

	@Override
	public String sendMessage(String clientMessage) {
		
		String serverMessage = null;
		if (clientMessage.equals("Client Message")) {
			serverMessage = "Server Message";
		}
		
		return serverMessage;
	}
	
	public void createStubAndbind(int rmiPortNumber) throws RemoteException {
		
		MessengerInterface stub = (MessengerInterface) UnicastRemoteObject.exportObject((MessengerInterface) this, 0);
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind("Server", stub);
	}
	
	public static void main(String[] args) {
		
		MessengerImpl msgInf = new MessengerImpl();
		int rmiPortNumber = Integer.valueOf(args[0]);
		try {
			msgInf.createStubAndbind(rmiPortNumber);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}	
}
