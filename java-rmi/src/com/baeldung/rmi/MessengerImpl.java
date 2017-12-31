package com.baeldung.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MessengerImpl implements MessengerInterface {

	@Override
	public String sendMessage(String message) {
		
		String serverMessage = null;
		if (message.equals("Client Message")) {
			serverMessage = "Server Message";
		}
		
		return serverMessage;
	}
	
	public void createStubAndbind() throws RemoteException {
		
		MessengerInterface stub = (MessengerInterface) UnicastRemoteObject.exportObject((MessengerInterface) this, 0);
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind("Server", stub);
	}
	
	@Override
	public Message sendMessage(Message message) throws RemoteException {
		
		Message serverMessage = null;
		if (message.getMessageText().equals("Client Message")) {
			serverMessage = new Message("Server Message", "text/plain");
		}
		
		return serverMessage;
	}

	public static void main(String[] args) {
		
		MessengerImpl msgInf = new MessengerImpl();
		try {
			msgInf.createStubAndbind();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
