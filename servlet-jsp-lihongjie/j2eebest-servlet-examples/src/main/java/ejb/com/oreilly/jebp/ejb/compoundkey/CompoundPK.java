package com.oreilly.jebp.ejb.compoundkey;

import java.io.Serializable;
import java.util.Date;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Compound Keys.
 *
 * Example of a compound key.
**/
public class CompoundPK implements Serializable {
	public String lastname;
	public String firstname;
	public int id;
	public Date creation;

	public CompoundPK (String lastname, String firstname, int id, Date creation) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.id = id;
		this.creation = creation;
	} // constructor

	public int hashCode() {
		int hash = 0;
		hash = hash * 31 + lastname.hashCode();
		hash = hash * 31 + firstname.hashCode();
		hash = hash * 31 + id;
		hash = hash * 31 + creation.hashCode();
		return hash;
	} // hashCode

	public boolean equals (Object obj) {
		if (!(obj instanceof CompoundPK)) return false;
		CompoundPK other = (CompoundPK)obj;

		return lastname.equals (other.lastname)
		    && firstname.equals (other.firstname)
		    && id == other.id
		    && creation.equals (other.creation);
	} // equals
} // CompoundPK