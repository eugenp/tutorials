package com.oreilly.jebp.ejb.businessinterface;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using business interfaces.
 *
 * Example remote interface for entity bean that uses a business interface.
**/
public interface OrderRemote extends Order, EJBObject {

	// all methods are inherited from Order and EJBObject...

} // OrderRemote