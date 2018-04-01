package com.oreilly.jebp.ejb.largequeries;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.LinkedList;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Handling Large Queries.
 *
 * Remote interface of the stateless seesion bean that implements a query.
**/
public interface UserQueryRemote extends EJBObject {
	/**
	 * Returns a list of Canadian users in the database.
	 * Each entry of the list is a UserValueObject.
	**/
	public LinkedList findCanadianUsers() throws RemoteException;

	/**
	 * Returns a list of users in the specified range.
	 * Each entry of the list is a UserValueObject.
	**/
	public LinkedList findUsers (int from, int to) throws RemoteException;
} // UserQueryRemote