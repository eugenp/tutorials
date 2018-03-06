package com.oreilly.jebp.ejb.compoundkey;

import javax.ejb.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Compound Keys.
 *
 * An entity bean using the example compound key.
**/
public class CompoundBean implements EntityBean {
	/**
	 * The primary key.
	**/
	private CompoundPK pk;

	/**
	 * A datum that this entity holds.
	**/
	private String datum;

	// *********************************
	// business interface implementation

	/**
	 * Returns the datum.
	**/
	public String getDatum() {
		return datum;
	}

	/**
	 * Sets the datum.
	**/
	public void setDatum (String datum) {
		this.datum = datum;
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
		pk = (CompoundPK)ctx.getPrimaryKey();
	}

	public void ejbPassivate() {
		pk = null;
	}

	public CompoundPK ejbFindByPrimaryKey (CompoundPK pk) throws FinderException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// find the records in the database that match the query
			stmt = conn.prepareStatement("SELECT employee.datum FROM employee, employee_extra WHERE employee.name=? AND employee_extra.name=? AND employee.id=? AND employee_extra.creation=?");
			stmt.setString (1, pk.lastname);
			stmt.setString (2, pk.firstname);
			stmt.setInt (3, pk.id);
			stmt.setTimestamp (4, new Timestamp(pk.creation.getTime()));
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

	public CompoundPK ejbCreate (String lastname, String firstname, int id, Date creation, String datum)
	throws CreateException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// insert the record into the first table
			stmt = conn.prepareStatement("INSERT INTO employee VALUES (?, ?, ?)");
			stmt.setString (1, lastname);
			stmt.setInt (2, id);
			stmt.setString (3, datum);
			stmt.executeUpdate();

			closeAll (null, stmt, null);

			// insert the second record
			stmt = conn.prepareStatement("INSERT INTO employee_extra VALUES (?, ?)");
			stmt.setString (1, firstname);
			stmt.setTimestamp (2, new Timestamp (creation.getTime()));

			// set instance variables
			this.datum = datum;

			return new CompoundPK (lastname, firstname, id, creation);

		} catch (SQLException e) {
			throw new EJBException ("Error creating record", e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbCreate

	public void ejbPostCreate (String lastname, String firstname, int id, Date creation, String datum) {
	}

	public void ejbRemove() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// remove the record from the first table
			stmt = conn.prepareStatement("DELETE FROM employee WHERE name=? and id=?");
			stmt.setString (1, pk.lastname);
			stmt.setInt (2, pk.id);
			stmt.executeUpdate();

			closeAll (null, stmt, null);

			// remove the record from the second table
			stmt = conn.prepareStatement("DELETE FROM employee_extra WHERE name=? and creation=?");
			stmt.setString (1, pk.firstname);
			stmt.setTimestamp (2, new Timestamp (pk.creation.getTime()));
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new EJBException ("Error deleting record with pk="+pk, e);

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
			stmt = conn.prepareStatement("SELECT employee.datum FROM employee, employee_extra WHERE employee.name=? AND employee_extra.name=? AND employee.id=? AND employee_extra.creation=?");
			stmt.setString (1, pk.lastname);
			stmt.setString (2, pk.firstname);
			stmt.setInt (3, pk.id);
			stmt.setTimestamp (4, new Timestamp(pk.creation.getTime()));
			rs = stmt.executeQuery();

			if (!rs.next()) throw new NoSuchEntityException ("Cannot find record with pk="+pk);

			// set instance variables
			datum = rs.getString (1);

		} catch (SQLException e) {
			throw new EJBException ("Error finding record with pk="+pk, e);

		} finally {
			closeAll (conn, stmt, rs);
		}		
	} // ejbLoad

	public void ejbStore() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// update the record in the database
			stmt = conn.prepareStatement("UPDATE emplyee SET datum=? WHERE name=? AND id=?");
			stmt.setString (1, datum);
			stmt.setString (2, pk.lastname);
			stmt.setInt (3, pk.id);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException ("Error updating record with pk="+pk, e);

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
} // CompoundBean