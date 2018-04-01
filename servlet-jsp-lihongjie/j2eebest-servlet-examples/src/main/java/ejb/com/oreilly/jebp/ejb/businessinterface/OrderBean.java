package com.oreilly.jebp.ejb.businessinterface;

import javax.ejb.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using business interfaces.
 *
 * Example entity bean implementation that uses a business interface.
**/
public class OrderBean implements Order, EntityBean {
	/**
	 * Primary key for the order record.
	**/
	private Integer pk;

	/**
	 * Quantity of items in this order.
	**/
	private int quantity;

	/**
	 * Price of the item.
	**/
	private double pricePerItem;

	// *********************************
	// business interface implementation

	/**
	 * Returns the ordered quantity.
	**/
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity of this order.
	**/
	public void setQuantity (int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Returns the price per item.
	**/
	public double getPricePerItem() {
		return pricePerItem;
	}

	/**
	 * Sets the price per item.
	**/
	public void setPricePerItem (double price) {
		this.pricePerItem = pricePerItem;
	}

	/**
	 * Returns the total price of the order.
	**/
	public double getTotalPrice() {
		return quantity*pricePerItem;
	}

	// **********************************
	// entity bean implementation methods

	private EntityContext ctx;

	public void setEntityContext (EntityContext ctx) {
		this.ctx = ctx;
	}

	public void unsetEntityContext() {
		ctx = null;
	}

	public void ejbActivate() {
		pk = (Integer)ctx.getPrimaryKey();
	}

	public void ejbPassivate() {
		pk = null;
	}

	public Integer ejbFindByPrimaryKey (Integer pk) throws FinderException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// find the record in the database
			stmt = conn.prepareStatement("SELECT order_id FROM order WHERE order_id=?");
			stmt.setInt(1, pk.intValue());
			rs = stmt.executeQuery();

			if (!rs.next()) throw new ObjectNotFoundException ("Cannot find order with id="+pk);

			// return PK
			return pk;

		} catch (SQLException e) {
			throw new EJBException ("Error finding order with id="+pk, e);

		} finally {
			closeAll (conn, stmt, rs);
		}
	} // ejbFindByPrimaryKey

	public Integer ejbCreate (int quantity, double price)
	throws CreateException {
		// check parameters
		if (quantity<=0) throw new CreateException ("Quantity must be greater than 0");
		if (price<0) throw new CreateException ("We can't GIVE people money to take our product!");

		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// get the next pk in the sequence (not implemented here)
			int nextPK = 0;

			// insert the record into the database
			stmt = conn.prepareStatement("INSERT INTO order VALUES (?, ?, ?)");
			stmt.setInt(1, nextPK);
			stmt.setInt(2, quantity);
			stmt.setDouble(3, price);
			stmt.executeUpdate();

			// set instance variables
			this.quantity = quantity;
			this.pricePerItem = price;
			this.pk = new Integer (nextPK);

			// return PK
			return pk;

		} catch (SQLException e) {
			throw new EJBException ("Error creating order", e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbCreate

	public void ejbPostCreate (int quantity, double price) {
	}

	public void ejbRemove() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// remove the record from the database
			stmt = conn.prepareStatement("DELETE FROM order WHERE order_id=?");
			stmt.setInt(1, pk.intValue());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException ("Error deleting order with id="+pk, e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbRemove

	public void ejbLoad() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get all record data from the database
			stmt = conn.prepareStatement("SELECT order_quantity, order_price FROM order WHERE order_id=?");
			stmt.setInt(1, pk.intValue());
			rs = stmt.executeQuery();

			if (!rs.next()) throw new NoSuchEntityException ("Cannot find order with id="+pk);

			// set instance variables
			quantity = rs.getInt(1);
			pricePerItem = rs.getDouble(2);

		} catch (SQLException e) {
			throw new EJBException ("Error finding order with id="+pk, e);

		} finally {
			closeAll (conn, stmt, rs);
		}		
	} // ejbLoad

	public void ejbStore() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// update the record in the database
			stmt = conn.prepareStatement("UPDATE order SET order_quantity=?, order_price=? WHERE order_id=?");
			stmt.setInt(1, quantity);
			stmt.setDouble (2, pricePerItem);
			stmt.setInt(3, pk.intValue());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException ("Error updating order with id="+pk, e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbStore

	/**
	 * Helper class that returns a jdbc connection.
	**/
	protected Connection getConnection() throws EJBException {
		try {
			Context jndi = new InitialContext();
			DataSource ds = (DataSource)jndi.lookup("java:comp/env/jdbc/TestDatabase");
			return ds.getConnection();

		} catch (NamingException e) {
			throw new EJBException ("Error getting datasource from JNDI", e);

		} catch (SQLException e) {
			throw new EJBException ("Error getting connection from datasource", e);
		}
	} // getConnection

	/**
	 * Helper class that closes all sql objects that we use during database access.
	**/
	public static void closeAll (Connection conn, Statement stmt, ResultSet rs)
	throws EJBException {
		try {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();

		} catch (SQLException e) {
			throw new EJBException ("Error closing SQL objects", e);
		}
	} // closeAll
} // OrderBean