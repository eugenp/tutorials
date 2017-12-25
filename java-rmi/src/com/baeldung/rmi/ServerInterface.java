package com.baeldung.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	
	public String sendMessage(String requestMessage) throws RemoteException;
}
