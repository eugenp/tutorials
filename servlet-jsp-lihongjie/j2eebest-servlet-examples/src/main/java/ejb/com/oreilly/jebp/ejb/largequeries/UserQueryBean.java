package com.oreilly.jebp.ejb.largequeries;

import javax.ejb.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.LinkedList;


/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Handling Large Queries.
 *
 * The stateless seesion bean class that implements a query.
**/ 
public class UserQueryBean implements SessionBean {
	/**
	 * Returns a list of Canadian users from the database.
	 * Each entry of the list is a UserValueObject.
	**/
	public LinkedList findCanadianUsers() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get all record data from the database
			stmt = conn.prepareStatement ("SELECT * FROM user WHERE user_coutry=’Canada’ ORDER BY user_id");
			rs = stmt.executeQuery();

			return createListFromRS (rs);

		} catch (SQLException e) {
			throw new EJBException ("Error finding users", e);

		} finally {
			closeAll (conn, stmt, rs);
		}
	} // findCanadianUsers

	/**
	 * Returns a list of users in the specified range.
	 * Each entry of the list is a UserValueObject.
	**/
	public LinkedList findUsers (int from, int to) {
		Connection conn = getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// create a scrollable statement
			stmt = conn.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
			
			// give hints to the driver
			stmt.setFetchSize (to-from+1); // how many rows to fetch
			stmt.setFetchDirection (ResultSet.FETCH_FORWARD);

			// create result set
			rs = stmt.executeQuery ("SELECT * FROM user ORDER BY user_id");

			return createListFromRS (rs, from, to);

		} catch (SQLException e) {
			throw new EJBException ("Error finding users", e);

		} finally {
			closeAll (conn, stmt, rs);
		}
	}

	/**
	 * Helper method that creates a list of user value objects using the
	 * data from the result set.
	**/
	protected LinkedList createListFromRS (ResultSet rs) throws SQLException {
		// this is what we’ll return
		LinkedList list = new LinkedList();

		while (rs.next()) {
			// create value object with primary key value
			UserValueObject user = new UserValueObject (rs.getInt(1));

			// add other fields to it
			user.setName (rs.getString(2));
			user.setCountry (rs.getString(3));

			// add it to the list
			list.add (user);
		}

		return list;
	} // createListFromRS

	/**
	 * Helper method that creates a list of user value objects using only the
	 * specified range of records in the record set.
	**/
	protected LinkedList createListFromRS (ResultSet rs, int from, int to)
	throws SQLException {
		// this is what we’ll return
		LinkedList list = new LinkedList();

		rs.absolute(from); // jump to the first needed record

		for (int i=0; i<(to-from+1); i++) {
			// create value object with primary key value
			UserValueObject user = new UserValueObject (rs.getInt(1));

			// add other fields to it
			user.setName (rs.getString(2));
			user.setCountry (rs.getString(3));

			// add it to the list
			list.add (user);

			// go to the next row
			rs.next();
		}

		return list;
	} // createListFromRS

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

	// *******************
	// SessionBean methods

	public void ejbCreate() throws CreateException {
		// nothing to do
	}

	public void ejbRemove() {
		// nothing to do
	}

	public void ejbActivate() {
		// nothing to do
	}

	public void ejbPassivate() {
		// nothing to do
	}

	public void setSessionContext (SessionContext ctx) {
		// nothing to do
	}
} // UserQueryBean