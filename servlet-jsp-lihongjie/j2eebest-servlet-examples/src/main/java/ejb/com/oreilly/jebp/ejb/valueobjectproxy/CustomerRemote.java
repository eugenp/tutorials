package com.oreilly.jebp.ejb.valueobjectproxy;

import javax.ejb.*;
import java.rmi.RemoteException;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Value Object Proxy to implement large scale value object pattern.
 *
 * Remote interface for entity bean that uses value object proxy.
**/
public interface CustomerRemote extends EJBObject {
	public Customer getValueObject() throws RemoteException;
	public void setValueObject (Customer customer) throws RemoteException;
} // CustomerRemote