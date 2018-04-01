package com.oreilly.jebp.ejb.compoundkey;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Compound Keys.
 *
 * Home interface of the example entity bean.
**/
public interface CompoundHome extends EJBHome {
	public CompoundRemote create (String lastname, String firstname, int id,
		Date creation, String datum) throws CreateException, RemoteException;
	public CompoundRemote findByPrimaryKey (CompoundPK pk)
		throws FinderException, RemoteException;
} // CompoundHome