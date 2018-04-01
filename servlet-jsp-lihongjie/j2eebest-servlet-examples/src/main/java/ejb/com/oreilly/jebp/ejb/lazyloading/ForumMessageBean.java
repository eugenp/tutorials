package com.oreilly.jebp.ejb.lazyloading;

import javax.ejb.*;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import java.io.*;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using business interfaces.
 *
 * Example entity bean implementation that uses lazy loading.
**/
public class ForumMessageBean implements EntityBean {
	// persisted fields
	private Integer id;	// pk
	private String title;
	private String author;

	private StringBuffer messageText; // this is our large data field
	private boolean isMessageTextLoaded;

	private EntityContext ctx;

	public void setEntityContext (EntityContext ctx) {
		this.ctx = ctx;
	}

	public void unsetEntityContext() {
		ctx = null;
	}

	public void ejbActivate() {
		id = (Integer)ctx.getPrimaryKey();
	}

	public void ejbPassivate() {
		id = null;
	}

	public Integer ejbCreate (String title, String author,
	                          StringBuffer message) throws CreateException {
		// create new record in the database

		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// figure out the next primary key (not implemented here)
			int nextID = 0;

			// insert the record into the database
			stmt = conn.prepareStatement("INSERT INTO message VALUES (?,?,?,?)");
			stmt.setInt (1, nextID);
			stmt.setString (2, title);
			stmt.setString (3, author);
			stmt.setCharacterStream (4,
				new StringReader(message.toString()), message.length());
			stmt.executeUpdate();

			// set instance variables
			this.id = new Integer(nextID);
			this.title = title;
			this.author = author;
			this.messageText = message;

			// indicate that the text is in the bean
			isMessageTextLoaded = true;

			return id;

		} catch (SQLException e) {
			throw new EJBException ("Error creating message", e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbCreate

	public void ejbPostCreate (String title, String author,
	                            StringBuffer message) {
	}

	public void ejbLoad() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// get all record data from the database
			stmt = conn.prepareStatement("SELECT title, author FROM message where id=?");
			stmt.setInt(1, id.intValue());
			rs = stmt.executeQuery();

			if (!rs.next()) throw new NoSuchEntityException ("Cannot find message with id="+id);

			// set instance variables
			title = rs.getString(1);
			author = rs.getString(2);

			// delay loading of the message text
			isMessageTextLoaded = false;

		} catch (SQLException e) {
			throw new EJBException ("Error loading record with id="+id, e);

		} finally {
			closeAll (conn, stmt, rs);
		}
	} // ejbLoad

	public void ejbStore() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE message SET title=?, author=?, text=? WHERE id=?");
			stmt.setString (1, title);
			stmt.setString (2, author);
			stmt.setCharacterStream (3,
				new StringReader(messageText.toString()), messageText.length());
			stmt.setInt (4, id.intValue());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException ("Error updating message with id="+id, e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbStore

	public Integer ejbFindByPrimaryKey (Integer pk) throws FinderException {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			// find the record in the database
			stmt = conn.prepareStatement("SELECT id FROM message WHERE id=?");
			stmt.setInt (1, pk.intValue());
			rs = stmt.executeQuery();

			if (!rs.next()) throw new ObjectNotFoundException ("Cannot find message with id="+pk);

			// return PK
			return pk;

		} catch (SQLException e) {
			throw new EJBException ("Error finding message with id="+pk, e);

		} finally {
			closeAll (conn, stmt, rs);
		}
	} // ejbFindByPrimaryKey

	public void ejbRemove() {
		Connection conn = getConnection();
		PreparedStatement stmt = null;

		try {
			// remove the record from the database
			stmt = conn.prepareStatement("DELETE FROM message WHERE id=?");
			stmt.setInt (1, id.intValue());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new EJBException ("Error deleting message with id="+id, e);

		} finally {
			closeAll (conn, stmt, null);
		}
	} // ejbRemove


	// ****************
	// accessor methods

	public StringBuffer getMessageText() {
		// call helper method to load the text if needed
		loadText();

		return messageText;
	}

	public void setMessageText (StringBuffer text) {
		messageText = text;

		// set the lazy-load flag to true, so that getMessageText
		// doesn’t overwrite this value
		isMessageTextLoaded = true;
	}

	/**
	 * Helper method that lazy-loads the message text from a clob.
	**/
	private void loadText() {
		// if it’s already loaded, we have nothing to do...
		if (isMessageTextLoaded) return;

		// load the text from database
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("SELECT text FROM message where id=?");
			stmt.setInt(1, id.intValue());
			rs = stmt.executeQuery();

			if (!rs.next()) throw new NoSuchEntityException ("Cannot find message with id="+id);

			// load the text
			messageText = new StringBuffer();

			Reader reader = rs.getCharacterStream(1);
			char[] buf = new char[1024];
			while (true) {
				int r = reader.read(buf, 0, buf.length);
				if (r==-1) break;
				messageText.append(buf, 0, r);
			}

			// set the lazy load flag
			isMessageTextLoaded = true;

		} catch (SQLException e) {
			throw new EJBException ("Error loading message with id="+id, e);

		} catch (IOException e) {
			throw new EJBException ("Error loading message with id="+id, e);
			
		} finally {
			closeAll (conn, stmt, rs);
		}
	} // loadText

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
} // ForumMessageBean