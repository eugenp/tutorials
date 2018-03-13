package com.oreilly.jebp.ejb.cmp_bmp;

import javax.ejb.*;
import java.rmi.RemoteException;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Writing Dual CMP/BMP beans.
 *
 * Example home interface for entity bean that has CMP and BMP implementations.
**/
public interface BeanHome extends EJBHome {
	public BeanRemote create (String name, String address)
		throws CreateException, RemoteException;
	public BeanRemote findByPrimaryKey (String name)
		throws FinderException, RemoteException;		
} // BeanRemote