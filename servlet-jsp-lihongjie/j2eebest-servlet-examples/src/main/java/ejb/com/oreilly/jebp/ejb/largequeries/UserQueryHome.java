package com.oreilly.jebp.ejb.largequeries;

import javax.ejb.*;
import java.rmi.RemoteException;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Handling Large Queries.
 *
 * Home interface of the stateless seesion bean that implements a query.
**/
public interface UserQueryHome extends EJBHome {
	UserQueryRemote create() throws CreateException, RemoteException;
} // UserQueryHome