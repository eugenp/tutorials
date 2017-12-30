package com.baeldung.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessengerInterface extends Remote {
	
	public String sendMessage(String requestMessage) throws RemoteException;
}
