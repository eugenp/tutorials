package com.oreilly.jebp.ejb.cmp_bmp;

import javax.ejb.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Writing Dual CMP/BMP beans.
 *
 * Example BMP entity bean implementation.
**/
public class TheBMPBean extends TheCMPBean {
	protected EntityContext ctx;
	protected String name;
	protected String address;

	// ***************************
	// overriding accessor methods

	public String getName()	{
		return name;
	}
	public void setName (String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress (String address) {
		this.name = name;
	}

	// ******************************
	// overriding persistence methods

	public String ejbCreate (String name, String address) throws CreateException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// insert the record into the database
			stmt = conn.prepareStatement("INSERT INTO customer VALUES (?,?)");
			stmt.setString (1, name);
			stmt.setString (2, address);
			stmt.executeUpdate();

			// set the instance variables
			return super.ejbCreate(name, address);

		} catch (SQLException e) {
			throw new EJBException ("Error creating customer", e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbCreate

	public String ejbFindByPrimaryKey (String name) throws FinderException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// find the record in the database
			stmt = conn.prepareStatement("SELECT name FROM customer WHERE name=?");
			stmt.setString(1, name);
			rs = stmt.executeQuery();

			if (!rs.next()) throw new ObjectNotFoundException ("Cannot find customer with name="+name);

			// return PK
			return name;

		} catch (SQLException e) {
			throw new EJBException ("Error finding customer with name="+name, e);

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
			stmt = conn.prepareStatement("DELETE FROM customer WHERE name=?");
			stmt.setString (1, name);
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
			stmt = conn.prepareStatement("SELECT address FROM customer WHERE name=?");
			stmt.setString (1, name);
			rs = stmt.executeQuery();

			if (!rs.next()) throw new NoSuchEntityException ("Cannot find customer with name="+name);

			// set instance variables
			address = rs.getString(1);

		} catch (SQLException e) {
			throw new EJBException ("Error finding customer with name="+name, e);

		} finally {
			closeAll (conn, stmt, rs);
		}		
	} // ejbLoad

	public void ejbStore() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// update the record in the database
			stmt = conn.prepareStatement("UPDATE customer SET address=? WHERE name=?");
			stmt.setString (1, address);
			stmt.setString (2, name);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException ("Error updating customer with name="+name, e);

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
		name = (String)ctx.getPrimaryKey();
	}

	public void ejbPassivate() {
		name = null;
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
} // TheBMPBean