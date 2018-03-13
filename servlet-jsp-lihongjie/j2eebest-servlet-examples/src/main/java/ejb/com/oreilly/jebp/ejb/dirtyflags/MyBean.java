package com.oreilly.jebp.ejb.dirtyflags;

import javax.ejb.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using DirtyFlags in ejbStore.
 *
 * Example bean implementation that uses a dirty flag.
**/
public class MyBean implements EntityBean {
	// our dirty flag
	private boolean isModified;

	// the primary key
	private String pk;

	// some data stored in this entity bean
	private String someString;

	// entity context
	private EntityContext ctx;

	public void ejbLoad() {
		// load the data from the database

		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get all record data from the database
			stmt = conn.prepareStatement("SELECT somestring FROM mytable WHERE pk=?");
			stmt.setString(1, pk);
			rs = stmt.executeQuery();

			if (!rs.next()) throw new NoSuchEntityException ("Cannot find record with pk="+pk);

			// set instance variables
			someString = rs.getString(1);

		} catch (SQLException e) {
			throw new EJBException ("Error finding record with pk="+pk, e);

		} finally {
			closeAll (conn, stmt, rs);
		}		
		// so far, our data reflects the database data
		isModified = false;
	} // ejbLoad

	public void ejbStore() {
		// no need to save?
		if (!isModified) return;

		// data has been changed, update the database

		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement("UPDATE mytable set somestring=? WHERE pk=?");
			stmt.setString(1, someString);
			stmt.setString (2, pk);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException ("Error updating mytable with pk="+pk, e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbStore


	// ****************
	// business methods

	public String getSomeString() {
		return someString;
	}

	public void setSomeString (String str) {
		someString = str;

		// our data has been changed
		isModified = true;
	}


	// *****************
	// other EJB methods

	public void setEntityContext (EntityContext ctx) {
		this.ctx = ctx;
	}

	public void unsetEntityContext() {
		ctx = null;
	}

	public void ejbActivate() {
		pk = (String)ctx.getPrimaryKey();
	}

	public void ejbPassivate() {
		pk = null;
	}

	public String ejbFindByPrimaryKey (String pk) throws FinderException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// find the record in the database
			stmt = conn.prepareStatement("SELECT pk FROM mytable WHERE pk=?");
			stmt.setString(1, pk);
			rs = stmt.executeQuery();

			if (!rs.next()) throw new ObjectNotFoundException ("Cannot find record with pk="+pk);

			// return PK
			return pk;

		} catch (SQLException e) {
			throw new EJBException ("Error finding record with pk="+pk, e);

		} finally {
			closeAll (conn, stmt, rs);
		}
	} // ejbFindByPrimaryKey

	public String ejbCreate (String someString)
	throws CreateException {

		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// figure out the next pk (not implemented here)
			String nextPK = "nextPK";

			// insert the record into the database
			stmt = conn.prepareStatement("INSERT INTO mytable VALUES (?, ?)");
			stmt.setString(1, nextPK);
			stmt.setString(2, someString);
			stmt.executeUpdate();

			// set instance variables
			this.someString = someString;
			this.pk = nextPK;

			// this instance has the same values as the database record
			this.isModified = false;

			// return PK
			return nextPK;

		} catch (SQLException e) {
			throw new EJBException ("Error creating record", e);

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
			stmt = conn.prepareStatement("DELETE FROM mytable WHERE pk=?");
			stmt.setString(1, pk);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException ("Error deleting record with pk="+pk, e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbRemove

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
} // MyBean