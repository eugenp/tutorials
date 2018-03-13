package com.oreilly.jebp.ejb.valueobjectproxy;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Value Object Proxy to implement large scale value object pattern.
 *
 * Example value object interface.
**/
public interface Customer {
	public int getId();
	public void setId (int id);

	public String getName();
	public void setName (String name);

	public String getAddress();
	public void setAddress (String address);
}