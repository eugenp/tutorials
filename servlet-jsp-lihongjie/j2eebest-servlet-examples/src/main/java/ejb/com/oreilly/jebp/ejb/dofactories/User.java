package com.oreilly.jebp.ejb.dofactories;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Domain Object Factories.
 *
 * Domain object (value object) for the domain object factory example.
**/
public class User {
	protected int id;
	protected String name;
	protected String password;

	public User (int id, String name, String password) {
		setId (id);
		setName (name);
		setPassword (password);
	}

	public int getId() {
		return id;
	}

	public void setId (int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}
} // User