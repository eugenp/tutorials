package com.oreilly.jebp.ejb.dirtyflags;

import java.rmi.RemoteException;
import javax.ejb.*;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using DirtyFlags in ejbStore.
 *
 * Home interface of the example bean.
**/
public interface MyHome extends EJBHome {
	public MyRemote create (String someString)
	throws CreateException, RemoteException;

	public MyRemote findByPrimaryKey (String pk)
	throws FinderException, RemoteException;
} // MyHome