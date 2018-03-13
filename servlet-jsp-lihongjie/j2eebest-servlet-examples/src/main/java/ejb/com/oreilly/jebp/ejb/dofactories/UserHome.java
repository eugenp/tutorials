package com.oreilly.jebp.ejb.dofactories;

import javax.ejb.*;
import java.rmi.RemoteException;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Domain Object Factories.
 *
 * Home interface for the example bean.
**/
public interface UserHome extends EJBHome {
	public UserRemote create (String name, String password)
		throws CreateException, RemoteException;
	public UserRemote findByPrimaryKey (Integer id)
		throws FinderException, RemoteException;
} // UserHome