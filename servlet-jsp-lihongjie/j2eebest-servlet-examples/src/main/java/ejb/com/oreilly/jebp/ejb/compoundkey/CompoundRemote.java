package com.oreilly.jebp.ejb.compoundkey;

import javax.ejb.*;
import java.rmi.RemoteException;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Compound Keys.
 *
 * Remote interface of the example entity bean.
**/
public interface CompoundRemote extends EJBObject {
	public String getDatum() throws RemoteException;
	public void setDatum (String datum) throws RemoteException;
} // CompoundRemote