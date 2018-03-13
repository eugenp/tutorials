package com.oreilly.jebp.ejb.dofactories;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Domain Object Factories.
 *
 * Example concrete domain object factory implementation.
**/
public class DBUserFactory extends UserFactory {
	public User createUser (String name, String password) throws PersistenceException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// figure out the next pk (not implemented here)
			int nextID = 0;

			// insert the record into the database
			stmt = conn.prepareStatement("INSERT INTO user VALUES (?,?,?)");
			stmt.setInt (1, nextID);
			stmt.setString (2, name);
			stmt.setString (3, password);
			stmt.executeUpdate();

			// create the domain object and return it
			User user = new User(nextID, name, password);
			return user;

		} catch (SQLException e) {
			throw new PersistenceException ("Error creating record", e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // create

	public boolean existsUser (int id) throws PersistenceException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// find the record in the database
			stmt = conn.prepareStatement("SELECT id FROM user WHERE id=?");
			stmt.setInt (1, id);
			rs = stmt.executeQuery();

			// return whether there's a record for the user of the given id
			return rs.next();

		} catch (SQLException e) {
			throw new PersistenceException ("Error finding user with id="+id, e);

		} finally {
			closeAll (conn, stmt, rs);
		}
	} //existsUser

	public void deleteUser (int id) throws PersistenceException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// remove the record from the database
			stmt = conn.prepareStatement("DELETE FROM user WHERE id=?");
			stmt.setInt (1, id);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException ("Error deleting user with id="+id, e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // deleteUser

	public User loadUser (int id) throws PersistenceException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get all record data from the database
			stmt = conn.prepareStatement("SELECT name, password FROM user WHERE id=?");
			stmt.setInt (1, id);
			rs = stmt.executeQuery();

			if (!rs.next()) throw new PersistenceException ("Cannot find user with id="+id);

			// create the domain object and return it
			User user = new User(id, rs.getString(1), rs.getString(2));
			return user;

		} catch (SQLException e) {
			throw new PersistenceException ("Error finding user with id="+id, e);

		} finally {
			closeAll (conn, stmt, rs);
		}		
	} // loadUser

	public void saveUser (User user) throws PersistenceException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement("UPDATE user SET name=?, password=? WHERE id=?");
			stmt.setString (1, user.getName());
			stmt.setString (2, user.getPassword());
			stmt.setInt (3, user.getId());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException ("Error updating user with id="+user.getId(), e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} //saveUser

	/**
	 * Helper class that returns a jdbc connection.
	**/
	protected Connection getConnection() throws PersistenceException {
		try {
			Context jndi = new InitialContext();
			DataSource ds = (DataSource)jndi.lookup("java:comp/env/jdbc/TestDatabase");
			return ds.getConnection();

		} catch (NamingException e) {
			throw new PersistenceException ("Error getting datasource from JNDI: ", e);

		} catch (SQLException e) {
			throw new PersistenceException ("Error getting connection from datasource: ", e);
		}
	} // getConnection

	/**
	 * Helper class that closes all sql objects that we use during database access.
	**/
	public static void closeAll (Connection conn, Statement stmt, ResultSet rs)
	throws PersistenceException {
		try {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();

		} catch (SQLException e) {
			throw new PersistenceException ("Error closing SQL objects: ", e);
		}
	} // closeAll
} // DBUserFactory