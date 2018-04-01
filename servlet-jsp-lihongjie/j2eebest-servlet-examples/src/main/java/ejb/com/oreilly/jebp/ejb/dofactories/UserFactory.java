package com.oreilly.jebp.ejb.dofactories;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Domain Object Factories.
 *
 * Example domain object factory superclass.
**/
public abstract class UserFactory {
	public static synchronized UserFactory getInstance() {
		// in this example, we’ll hardcode a particular factory
		return new DBUserFactory();
	}

	public abstract User createUser (String name, String password) throws PersistenceException;
	public abstract boolean existsUser (int id) throws PersistenceException;
	public abstract void deleteUser (int id) throws PersistenceException;
	public abstract User loadUser (int id) throws PersistenceException;
	public abstract void saveUser (User user) throws PersistenceException;
}
