package com.oreilly.jebp.ejb.valueobjectproxy;

import javax.ejb.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Value Object Proxy to implement large scale value object pattern.
 *
 * Example entity bean that uses the value object proxy to expose its data.
**/
public class CustomerBean implements EntityBean {
	protected EntityContext ctx;
	protected Customer valueObject;

	// *********************
	// business methods
	public Customer getValueObject() {
		return valueObject;
	}

	public void setValueObject (Customer customer) {
		this.valueObject = valueObject;
	}

	// *******************
	// ejb implementation methods

	public Integer ejbCreate (String name, String address) throws CreateException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// get the next primary key for the object (not implemented)
			int nextID = 0;

			// insert the record into the database
			stmt = conn.prepareStatement("INSERT INTO customer VALUES (?,?,?)");
			stmt.setInt (1, nextID);
			stmt.setString (2, name);
			stmt.setString (3, address);
			stmt.executeUpdate();

			// create the value object
			valueObject = (Customer)ValueObjectProxy.createValueObject(Customer.class);
			valueObject.setId(nextID);
			valueObject.setName(name);
			valueObject.setAddress(address);

			return new Integer(nextID);

		} catch (SQLException e) {
			throw new EJBException ("Error creating customer", e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbCreate

	public Integer ejbFindByPrimaryKey (Integer id) throws FinderException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// find the record in the database
			stmt = conn.prepareStatement("SELECT id FROM customer WHERE id=?");
			stmt.setInt (1, id.intValue());
			rs = stmt.executeQuery();

			if (!rs.next()) throw new ObjectNotFoundException ("Cannot find customer with id="+id);

			// return PK
			return id;

		} catch (SQLException e) {
			throw new EJBException ("Error finding customer with id="+id, e);

		} finally {
			closeAll (conn, stmt, rs);
		}
	} // ejbFindByPrimaryKey

	public void ejbPostCreate (String name, String address) throws CreateException {
		// nothing to do here
	}

	public void ejbRemove() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// insert the record into the database
			stmt = conn.prepareStatement("DELETE FROM customer WHERE id=?");
			stmt.setInt (1, valueObject.getId());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException ("Error deleting customer", e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} //ejbRemove

	public void ejbLoad() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get all record data from the database
			stmt = conn.prepareStatement("SELECT name, address FROM customer WHERE id=?");
			stmt.setInt (1, valueObject.getId());
			rs = stmt.executeQuery();

			if (!rs.next()) throw new NoSuchEntityException ("Cannot find customer with id="+valueObject.getId());

			// set value object variables
			valueObject = (Customer)ValueObjectProxy.createValueObject(Customer.class);
			valueObject.setId (((Integer)ctx.getPrimaryKey()).intValue());
			valueObject.setName(rs.getString(1));
			valueObject.setAddress(rs.getString(1));

		} catch (SQLException e) {
			throw new EJBException ("Error finding customer with id="+valueObject.getId(), e);

		} finally {
			closeAll (conn, stmt, rs);
		}		
	} // ejbLoad

	public void ejbStore() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// update the record in the database
			stmt = conn.prepareStatement("UPDATE customer SET name=?, address=? WHERE id=?");
			stmt.setString (1, valueObject.getName());
			stmt.setString (2, valueObject.getAddress());
			stmt.setInt (3, valueObject.getId());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException ("Error updating customer with id="+valueObject.getId(), e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbStore

	public void setEntityContext (EntityContext ctx) {
		this.ctx = ctx;
	}

	public void unsetEntityContext() {
		ctx = null;
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

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
} // UserBean