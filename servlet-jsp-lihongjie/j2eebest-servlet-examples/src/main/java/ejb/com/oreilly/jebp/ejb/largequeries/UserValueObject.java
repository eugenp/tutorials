package com.oreilly.jebp.ejb.largequeries;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Handling Large Queries.
 *
 * The value object used by the UserQuery session bean. A list that it
 * returns gets populated with these objects.
**/
public class UserValueObject {
	private int user_id;
	private String user_name;
	private String user_country;

	public UserValueObject (int id) {
		this.user_id = id;
	}

	public int getId() {
		return user_id;
	}

	public String getName() {
		return user_name;
	}

	public void setName (String name) {
		user_name = name;
	}

	public String getCountry() {
		return user_country;
	}

	public void setCountry (String country) {
		user_country = country;
	}
} // UserValueObject