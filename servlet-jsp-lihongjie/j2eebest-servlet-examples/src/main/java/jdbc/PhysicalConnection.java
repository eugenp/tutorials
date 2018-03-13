/* $Id$ */
/* Copyright © 2002 George Reese, Imaginet */
package org.dasein.persist;

// Developed by George Reese for the book:
// Java Best Practices, Volume II: J2EE
// Ported to the digital@jwt code library by George Reese

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

/**
 * An abstraction for the physical connection to a vendor's
 * JDBC connection. This particular abstraction lives in a connection
 * pool and performs statement pooling.<br/>
 * Last modified $Date$
 * @version $Revision$
 * @author George Reese
 */
public class PhysicalConnection
implements PooledConnection, Connection, StatementEventListener {
    /**
     * The actual JDBC connection to the database. This class
     * works with any JDBC-compliant connection instance. For best
     * performance, the connection supplied should not be pooled.
     */
    private Connection        database  = null;
    /**
     * The list of objects listening for connection events.
     */
    private ArrayList         listeners = new ArrayList();
    /**
     * The currently open logical connection, if any.
     */
    private LogicalConnection logical   = null;
    /**
     * The pool of statements open by this connection.
     */
    private StatementPool     pool      = new StatementPool();

    /**
     * Constructs a new physical connection that pulls its
     * database connections from the specified DSN.
     * @param dsn the name of a data source providing JDBC connections
     * @throws java.sql.SQLException an error occurred connecting to the
     * database
     */
    public PhysicalConnection(String dsn) throws SQLException {
        this(dsn, null, null);
    }

    /**
     * Constructs a new physical connection that pulls its
     * database connections from the specified DSN.
     * @param dsn the name of a data source providing JDBC connections
     * @param uid the user ID to use in making the connection
     * @param pw the password to use in making the connection
     * @throws java.sql.SQLException an error occurred connecting to the
     * database
     */
    public PhysicalConnection(String dsn, String uid, String pw)
        throws SQLException {
        super();
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup(dsn);

            if( uid == null && pw == null ) {
                database = ds.getConnection();
            }
            else {
                database = ds.getConnection(uid, pw);
            }
        }
        catch( NamingException e ) {
            throw new SQLException(e.getMessage());
        }
        Thread t = new Thread() {
               public void run() {
                    keepAlive();
                }
            };

        t.setDaemon(true);
        t.setName("KEEP ALIVE");
        t.start();
    }

    /**
     * Adds new listeners to connection events. This method is part
     * of the pooled connection's contract with its pooling environment
     * so that the pooling environment knows when connections occur
     * and unrecoverable errors occur.
     * @param lstnr the new listener
     */
    public void addConnectionEventListener(ConnectionEventListener lstnr) {
        synchronized( listeners ) {
            listeners.add(lstnr);
        }
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @throws java.sql.SQLException a database error occurred
     */
    public void clearWarnings() throws SQLException {
        database.clearWarnings();
    }
    
    /**
     * Delegates to the underlying JDBC connection.
     * @throws java.sql.SQLException a database error occurred
     */
    public void close() throws SQLException {
        synchronized( listeners ) {
            if( database != null ) {
                database.close();
            }
        }
        logical = null;
    }

    /**
     * Called by the current logical connection to let the physical
     * connection know that the application has closed the logical
     * connection. This method will thus trigger a connection event
     * notifying the pooling environment that this physical connection
     * is now available for reuse.
     */
    public void connectionClosed() {
        synchronized( listeners ) {
            Iterator it = listeners.iterator();
            
            while( it.hasNext() ) {
                ConnectionEventListener lstnr;

                lstnr = (ConnectionEventListener)it.next();
                lstnr.connectionClosed(new ConnectionEvent(this));
            }
            logical = null;
            listeners.notifyAll();
        }
    }

    /**
     * Called by the current logical connection to let the physical
     * connection know that an error has occured indicating a need
     * to discard this physical connection. This method will thus
     * trigger a connection event notifying the pooling environment
     * that this physical connection should be permanently out of use.
     * @param e the exception causing the discard of this connection
     */    
    public void connectionErrored(SQLException e) {
        synchronized( listeners ) {
            Iterator it = listeners.iterator();
            
            while( it.hasNext() ) {
                ConnectionEventListener lstnr;

                lstnr = (ConnectionEventListener)it.next();
                lstnr.connectionErrorOccurred(new ConnectionEvent(this, e));
            }
        }
    }
    
    /**
     * Delegates to the underlying JDBC connection.
     * @throws java.sql.SQLException a database error occurred
     */
    public void commit() throws SQLException {
        database.commit();
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @return a new statement instance
     * @throws java.sql.SQLException a database error occurred
     */
    public Statement createStatement() throws SQLException {
        return database.createStatement();
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @param rst the result set type
     * @param rsc the result set concurrency
     * @throws java.sql.SQLException a database error occurred
     */
    public Statement createStatement(int rst, int rsc) throws SQLException {
        return database.createStatement(rst, rsc);
    }
    
    /**
     * Delegates to the underlying JDBC connection.
     * @return the current auto-commit state
     * @throws java.sql.SQLException a database error occurred
     */
    public boolean getAutoCommit() throws SQLException {
        return database.getAutoCommit();
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @return the currently selected catalog
     * @throws java.sql.SQLException a database error occurred
     */
    public String getCatalog() throws SQLException {
        return database.getCatalog();
    }
    
    /**
     * Provides the connection pooling environment with a logical
     * connection that references this physical connection.
     * @return the logical connection for use by an application
     * @throws java.sql.SQLException a database error occurred
     */
    public Connection getConnection() throws SQLException {
        synchronized( listeners ) {
            logical = new LogicalConnection(this);
            return logical;
        }
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @return the meta-data for this connection
     * @throws java.sql.SQLException a database error occurred
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        return database.getMetaData();
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @return the current transaction isolation level
     * @throws java.sql.SQLException a database error occurred
     */
    public int getTransactionIsolation() throws SQLException {
        return database.getTransactionIsolation();
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @return the current type map
     * @throws java.sql.SQLException a database error occurred
     */
    public Map getTypeMap() throws SQLException {
        return database.getTypeMap();
    }
    
    /**
     * Delegates to the underlying JDBC connection.
     * @return any warnings
     * @throws java.sql.SQLException a database error occurred
     */
    public SQLWarning getWarnings() throws SQLException {
        return database.getWarnings();
    }
    
    /**
     * Delegates to the underlying JDBC connection, if any.
     * @return true if there is no udnerlying connection or that
     * connection is closed
     * @throws java.sql.SQLException a database error occurred
     */
    public boolean isClosed() throws SQLException {
        return (database != null && database.isClosed());
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @return the current read only state
     * @throws java.sql.SQLException a database error occurred
     */
    public boolean isReadOnly() throws SQLException {
        return database.isReadOnly();
    }
    
    /**
     * This method sits in a loop and keeps the underlying connection
     * alive while the connection is in a pool. It keeps the connection
     * alive by periodically sending:<br/>
     * <code>SELECT 1</code>
     * to the underlying database.
     * @return any warnings
     * @throws java.sql.SQLException a database error occurred
     */
    private void keepAlive() {
        synchronized( listeners ) {
            boolean closed = false;
            
            do {
                try { listeners.wait(60000); }
                catch( InterruptedException e ) { }
                // only do this if the connection is in the pool
                if( logical == null ) {
                    // if an error occurs while testing
                    // this connection will be removed from the pool
                    if( !test() ) {
                        try { database.close(); }
                        catch( SQLException e ) { }
                        database = null;
                        logical = null;
                        return;
                    }
                }
                try {
                    closed = isClosed();
                }
                catch( SQLException e ) {
                    closed = true;
                }
            } while( !closed );
        }
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @param sql the ANSI SQL to translate
     * @return the native SQL for the specified ANSI SQL
     * @throws java.sql.SQLException a database error occurred
     */
    public String nativeSQL(String sql) throws SQLException {
        return database.nativeSQL(sql);
    }
    
    /**
     * Delegates to the underlying JDBC connection.
     * @param sql the stored procedure to call
     * @return the callable statement for the specified stored procedure
     * @throws java.sql.SQLException a database error occurred
     */
    public CallableStatement prepareCall(String sql) throws SQLException {
        return database.prepareCall(sql);
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @param sql the stored procedure to call
     * @param rst the result set type
     * @param rsc the result set concurrency
     * @return the callable statement for the specified stored procedure
     * @throws java.sql.SQLException a database error occurred
     */
    public CallableStatement prepareCall(String sql, int rst, int rsc)
        throws SQLException {
        return database.prepareCall(sql, rst, rsc);
    }

    /**
     * Looks in the prepared statement pool for matching SQL and if
     * no match is found prepares a new one.
     * @param sql the prepared SQL
     * @return a pooled prepared statement
     * @throws java.sql.SQLException a database error occurred
     */
    public PreparedStatement prepareStatement(String sql)
        throws SQLException {
        PreparedStatement stmt;
        PooledStatement ps;
        
        synchronized( pool ) {
            if( pool.contains(sql) ) {
                stmt = pool.pop(sql);
            }
            else {
                stmt = database.prepareStatement(sql);
            }
        }
        ps = new PooledStatement(logical, sql, stmt);
        ps.addStatementEventListener(this);
        return ps;
    }

    /**
     * Prepares a statement for the specified SQL. If the statement
     * pool contains the specified statement and the result sets
     * should be <code>ResultSet.TYPE_FORWARD_ONLY</code> and
     * <code>ResultSet.CONCUR_READ_ONLY</code>, then the statement
     * is pulled from the pool. A new statement is prepared for
     * all other statements. Scrollable and updateable result
     * sets are not supported in this pooling mechanism.
     * @param sql the SQL to prepare
     * @param rst the result set type
     * @param rsc the result set concurrency
     * @return the prepared statement for the specified SQL
     * @throws java.sql.SQLException a database error occurred
     */
    public PreparedStatement prepareStatement(String sql, int rst, int rsc)
        throws SQLException {
        if( rst == ResultSet.TYPE_FORWARD_ONLY ) {
            if( rsc == ResultSet.CONCUR_READ_ONLY ) {
                return prepareStatement(sql);
            }
        }
        return database.prepareStatement(sql);
    }

    /**
     * Removes a listener from the list of listeners in accordance
     * with this pooled connection's contract with the pooling
     * environment.
     * @param lstnr the listener to remove
     */
    public void removeConnectionEventListener(ConnectionEventListener lstnr) {
        synchronized( listeners ) {
            listeners.remove(lstnr);
        }
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @throws java.sql.SQLException a database error occurred
     */
    public void rollback() throws SQLException {
        database.rollback();
    }
    
    /**
     * Delegates to the underlying JDBC connection.
     * @param ac the auto-commit status to assign
     * @throws java.sql.SQLException a database error occurred
     */
    public void setAutoCommit(boolean ac) throws SQLException {
        database.setAutoCommit(ac);
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @param cat the catalog to assign
     * @throws java.sql.SQLException a database error occurred
     */
    public void setCatalog(String cat) throws SQLException {
        database.setCatalog(cat);
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @param ro the read-only status to assign
     * @throws java.sql.SQLException a database error occurred
     */
    public void setReadOnly(boolean ro) throws SQLException {
        database.setReadOnly(ro);
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @param lvl the transaction isolation level to assign
     * @throws java.sql.SQLException a database error occurred
     */
    public void setTransactionIsolation(int lvl) throws SQLException {
        database.setTransactionIsolation(lvl);
    }

    /**
     * Delegates to the underlying JDBC connection.
     * @param map the type map to assign
     * @throws java.sql.SQLException a database error occurred
     */
    public void setTypeMap(Map map) throws SQLException {
        database.setTypeMap(map);
    }

    /**
     * Triggered by a statement when the application closes it so that
     * this connection may return the statement to the statement pool.
     * @param evt the event triggering the return to the pool
     * @throws java.sql.SQLException a database error occurred
     */
    public void statementClosed(StatementEvent evt) {
        synchronized( pool ) {
            pool.push(evt.getSQL(), evt.getStatement());
        }
    }

    /**
     * Tests the connection to see if it is still up. If not, it
     * notifies the pooling environment that this connection is no longer
     * valid.
     */
    private boolean test() {
        Statement stmt = null;

        try {
            stmt = database.createStatement();
            stmt.executeQuery("SELECT 1");
            return true;
        }
        catch( SQLException e ) {
            connectionErrored(e);
            return false;
        }
        finally {
            if( stmt != null ) {
                try { stmt.close(); }
                catch( SQLException e ) { }
            }
        }
    }

    public String toString() {
        if( database != null ) {
            return super.toString() + " [" + database.toString() + "]";
        }
        else {
            return super.toString();
        }
    }
}
