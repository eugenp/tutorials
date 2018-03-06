package com.oreilly.jebp.ejb.businessdelegate;

import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;
import javax.naming.*;
import javax.ejb.*;
import java.util.LinkedList;

// example bean used for delegation
import com.oreilly.jebp.ejb.largequeries.*;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Business Delegates.
 *
 * Business delegate example.
**/
public class UserQueryDelegate {
	private static final int NETWORK_RETRIES = 3;
	private UserQueryRemote bean;

	public void create() throws ApplicationException {
		// here we get a bean instance
		try {
			InitialContext ctx = new InitialContext();
			UserQueryHome home = (UserQueryHome) PortableRemoteObject.narrow (
					ctx.lookup ("ejb/UserQueryHome"),
					UserQueryHome.class);

			// retry in case of network problems
			for (int i=0; i<NETWORK_RETRIES; i++)
				try {
					bean = home.create();
					return;

				} catch (RemoteException e) {
					if (i+1 < NETWORK_RETRIES) continue;
					throw new ApplicationException ("Network problem "	+ e.toString());
				}


		} catch (CreateException e) {
			throw new ApplicationException ("Unexpected error creating an order instance: "
				+ e.toString());
		} catch (NamingException e) {
			throw new ApplicationException ("Error getting the home interface: "
				+ e.toString());
		}
	} // create

	public void remove() throws ApplicationException {
		// we release the session bean here

		// retry in case of network problems
		for (int i=0; i<NETWORK_RETRIES; i++)
			try {
				bean.remove();
				return;

			} catch (RemoteException e) {
				if (i+1 < NETWORK_RETRIES) continue;
				throw new ApplicationException ("Network problem "	+ e.toString());

			} catch (RemoveException e) {
				throw new ApplicationException ("Unexpected error releasing the order instance: "
					+ e.toString());
			}
	} // remove

	/**
	 * Returns a list of Canadian users in the database.
	**/
	public LinkedList findCanadianUsers() throws ApplicationException {
		// call a bean method here
		for (int i=0; i<NETWORK_RETRIES; i++)
			try {
				return bean.findCanadianUsers();

			} catch (RemoteException e) {
				if (i+1 < NETWORK_RETRIES) continue;
				throw new ApplicationException ("Network problem "	+ e.toString());
			}
		// never gets here
		return null;
	} // findCanadianUsers

	/**
	 * Returns a list of Canadian users in the database.
	**/
	public LinkedList findUsers (int from, int to) throws ApplicationException {
		// call a bean method here
		for (int i=0; i<NETWORK_RETRIES; i++)
			try {
				return bean.findUsers (from, to);

			} catch (RemoteException e) {
				if (i+1 < NETWORK_RETRIES) continue;
				throw new ApplicationException ("Network problem " + e.toString());
			}
		// never gets here
		return null;
	} // findUsers
} // UserQueryDelegate