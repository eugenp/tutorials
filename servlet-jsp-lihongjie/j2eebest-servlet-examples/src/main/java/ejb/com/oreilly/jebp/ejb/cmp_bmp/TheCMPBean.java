package com.oreilly.jebp.ejb.cmp_bmp;

import javax.ejb.*;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Writing Dual CMP/BMP beans.
 *
 * Example CMP entity bean implementation.
**/
public abstract class TheCMPBean implements EntityBean {
	// ***********
	// CMP fields

	public abstract String getName();		// pk
	public abstract void setName (String name);
	public abstract String getAddress();
	public abstract void setAddress (String address);

	public String ejbCreate (String name, String address) throws CreateException {
		setName (name);
		setAddress(address);
		return name;
	}

	// the business methods...
	public String getMailingAddress() {
		return getName() + "\n" + getAddress();
	}

	// ********************************
	// other EJB methods - empty in CMP

	public void ejbPostCreate (String name, String address) throws CreateException {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void ejbRemove() {
	}

	public void ejbLoad() {
	}

	public void ejbStore() {
	}

	public void setEntityContext (EntityContext ctx) {
	}

	public void unsetEntityContext() {
	}
} // TheCMPBean