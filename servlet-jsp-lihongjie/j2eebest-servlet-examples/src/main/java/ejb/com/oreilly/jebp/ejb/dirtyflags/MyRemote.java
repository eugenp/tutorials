package com.oreilly.jebp.ejb.dirtyflags;

import java.rmi.RemoteException;
import javax.ejb.*;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using DirtyFlags in ejbStore.
 *
 * Remote interface of the example bean.
**/
public interface MyRemote extends EJBObject {
	public String getSomeString() throws RemoteException;
	public void setSomeString (String string) throws RemoteException;
} // MyRemote