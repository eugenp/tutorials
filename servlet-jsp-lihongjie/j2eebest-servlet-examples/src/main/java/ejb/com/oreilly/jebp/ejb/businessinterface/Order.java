package com.oreilly.jebp.ejb.businessinterface;

import java.rmi.RemoteException;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using business interfaces.
 *
 * Example business interface for entity bean exposing a remote interface.
**/
public interface Order {
	/**
	 * Returns the ordered quantity.
	**/
	public int getQuantity() throws RemoteException;

	/**
	 * Sets the quantity of this order.
	**/
	public void setQuantity (int quantity) throws RemoteException;

	/**
	 * Returns the price per item.
	**/
	public double getPricePerItem() throws RemoteException;

	/**
	 * Sets the price per item.
	**/
	public void setPricePerItem (double price) throws RemoteException;

	/**
	 * Returns the total price of the order.
	**/
	public double getTotalPrice() throws RemoteException;
} // Order