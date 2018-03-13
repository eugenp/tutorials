package com.oreilly.jebp.ejb.valueobjectproxy;

import javax.ejb.*;
import java.rmi.RemoteException;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Value Object Proxy to implement large scale value object pattern.
 *
 * Home interface for entity bean that uses value object proxy.
**/
public interface CustomerHome extends EJBHome {
	public CustomerRemote create (String name, String address)
		throws CreateException, RemoteException;
	public CustomerRemote findByPrimaryKey (Integer pk)
		throws FinderException, RemoteException;
} // CustomerHome