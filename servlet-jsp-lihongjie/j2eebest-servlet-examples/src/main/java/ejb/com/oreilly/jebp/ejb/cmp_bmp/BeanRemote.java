package com.oreilly.jebp.ejb.cmp_bmp;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Writing Dual CMP/BMP beans.
 *
 * Example remote interface for entity bean that has CMP and BMP implementations.
**/
public interface BeanRemote extends EJBObject {
	public String getName() throws RemoteException;
	public String getAddress() throws RemoteException;
	public void setAddress (String address) throws RemoteException;
	public String getMailingAddress() throws RemoteException;
} // BeanRemote