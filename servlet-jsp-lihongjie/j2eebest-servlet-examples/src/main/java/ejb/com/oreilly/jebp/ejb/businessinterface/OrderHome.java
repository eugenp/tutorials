package com.oreilly.jebp.ejb.businessinterface;

import javax.ejb.*;
import java.rmi.RemoteException;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using business interfaces.
 *
 * Example remote interface for entity bean that uses a business interface.
**/
public interface OrderHome extends EJBHome {
	public OrderRemote create (int quantity, double price)
		throws CreateException, RemoteException;
	public OrderRemote findByPrimaryKey (Integer pk)
		throws FinderException, RemoteException;
} // OrderHome