package com.oreilly.jebp.ejb.dofactories;

import javax.ejb.*;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Domain Object Factories.
 *
 * Example bean implementation that uses a domain object factory for persistence.
**/
public class UserBean implements EntityBean {
	private EntityContext ctx;
	private transient UserFactory userFactory;
	private User user;

	public void setEntityContext (EntityContext ctx) {
		this.ctx = ctx;

		// get the factory object for the user
		userFactory = UserFactory.getInstance();
	}

	public void unsetEntityContext() {
		ctx = null;
		userFactory = null;
	}

	public void ejbActivate() {
		userFactory = UserFactory.getInstance();
	}

	public void ejbPassivate() {
	}

	public Integer ejbCreate (String name, String password)
	throws CreateException {
		// get the factory to create the user for us
		try {
			user = userFactory.createUser (name, password);
			return new Integer(user.getId());

		} catch (PersistenceException e) {
			throw new EJBException (e);
		}
	}

	public void ejbRemove() {
		try {
			userFactory.deleteUser(user.getId());
			user = null;

		} catch (PersistenceException e) {
			throw new EJBException (e);
		}
	} // ejbRemove

	public Integer ejbFindByPrimaryKey (Integer id) throws FinderException {
		try {
			// use the factory to find the user
			if (userFactory.existsUser(id.intValue())) return id;	
			throw new FinderException ("User " + id + " not found");

		} catch (PersistenceException e) {
			throw new EJBException (e);
		}
	} // ejbFindByPrimaryKey

	public void ejbLoad() {
		try {
			int id = ((Integer)ctx.getPrimaryKey()).intValue();
			user = userFactory.loadUser (id);

		} catch (PersistenceException e) {
			throw new EJBException (e);
		}
	} // ejbLoad

	public void ejbStore() {
		try {
			userFactory.saveUser (user);
		} catch (PersistenceException e) {
			throw new EJBException (e);
		}
	} // ejbStore

	// ************************
	// exposed business methods

	public String getName() {
		return user.getName();
	}

	public void setName (String name) {
		user.setName (name);
	}

	public String getPassword() {
		return user.getPassword();
	}

	public void setPassword (String password) {
		user.setPassword(password);
	}
} // UserBean