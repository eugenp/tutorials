package com.oreilly.jebp.ejb.dofactories;

import javax.ejb.*;
import java.rmi.RemoteException;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Domain Object Factories.
 *
 * Remote interface for the example bean.
**/
public interface UserRemote extends EJBObject {
	public String getName() throws RemoteException;
	public void setName (String name) throws RemoteException;
	public String getPassword() throws RemoteException;
	public void setPassword (String password) throws RemoteException;
} // UserRemote