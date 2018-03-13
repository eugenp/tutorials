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
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a logical connection to the database for use by
 * an application. When closed, this logical connection returns
 * the physical connection back to the connection pool.<br/>
  * Last modified $Date$
 * @version $Revision$
 * @author George Reese
 */
public class LogicalConnection implements Connection {
    /**
     * The physical connection behind this logical one.
     */
    private PhysicalConnection physical  = null;
    /**
     * The statements that this connection has generated.
     */
    private ArrayList         statements = new ArrayList();

    /**
     * Constructs a new logical connection tied to the specified
     * physical connection.
     */
    public LogicalConnection(PhysicalConnection conn) {
        super();
        physical = conn;
    }

    /**
     * Delegates to the physical connection.
     * @throws java.sql.SQLException a database error occurred
     */
    public void clearWarnings() throws SQLException {
        validate();
        physical.clearWarnings();
    }

    /**
     * Notifies the physical connection that this logical connection is
     * no longer open so that the physical connection may return
     * to the connection pool. After this method is called, any
     * further attempts by an application to use this logical connection
     * will result in an exception.
     * @throws java.sql.SQLException a database error occurred
     */
    public void close() throws SQLException {
        Iterator it = statements.iterator();
        
        validate();
        while( it.hasNext() ) {
            PreparedStatement stmt = (PreparedStatement)it.next();

            try { stmt.close(); }
            catch( SQLException e ) { }
        }
        statements.clear();
        physical.connectionClosed();
        physical = null;
    }

    /**
     * Delegates to the physical connection.
     * @throws java.sql.SQLException a database error occurred
     */     
    public void commit() throws SQLException {
        validate();
        physical.commit();
    }

    /**
     * Delegates to the physical connection.
     * @return a new statement instance
     * @throws java.sql.SQLException a database error occurred
     */
    public Statement createStatement() throws SQLException {
        validate();
        return physical.createStatement();
    }

    /**
     * Delegates to the physical connection.
     * @param rst the result set type
     * @param rsc the result set concurrrency
     * @return a new statement instance
     * @throws java.sql.SQLException a database error occurred
     */
    public Statement createStatement(int rst, int rsc) throws SQLException {
        validate();
        return physical.createStatement(rst, rsc);
    }

    /**
     * Delegates to the physical connection.
     * @return the current auto-commit status
     * @throws java.sql.SQLException a database error occurred
     */    
    public boolean getAutoCommit() throws SQLException {
        validate();
        return physical.getAutoCommit();
    }

    /**
     * Delegates to the physical connection.
     * @return the currently selected catalog
     * @throws java.sql.SQLException a database error occurred
     */
    public String getCatalog() throws SQLException {
        validate();
        return physical.getCatalog();
    }
    
    /**
     * Delegates to the physical connection.
     * @return the meta data for the current database
     * @throws java.sql.SQLException a database error occurred
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        validate();
        return physical.getMetaData();
    }

    /**
     * Delegates to the physical connection.
     * @return the current transaction isolation level
     * @throws java.sql.SQLException a database error occurred
     */
    public int getTransactionIsolation() throws SQLException {
        validate();
        return physical.getTransactionIsolation();
    }

    /**
     * Delegates to the physical connection.
     * @return the current type mapping
     * @throws java.sql.SQLException a database error occurred
     */
    public Map getTypeMap() throws SQLException {
        validate();
        return physical.getTypeMap();
    }
    
    /**
     * Delegates to the physical connection.
     * @return any warnings
     * @throws java.sql.SQLException a database error occurred
     */
    public SQLWarning getWarnings() throws SQLException {
        validate();
        return physical.getWarnings();
    }
    
    /**
     * This method checks to see if it has a valid physical
     * connection.
     * @return true if the physical connection is no longer available
     * @throws java.sql.SQLException never thrown
     */
    public boolean isClosed() throws SQLException {
        return (physical == null);
    }

    /**
     * Delegates to the physical connection.
     * @return whether this connection is read only
     * @throws java.sql.SQLException a database error occurred
     */
    public boolean isReadOnly() throws SQLException {
        validate();
        return physical.isReadOnly();
    }
    
    /**
     * Delegates to the physical connection.
     * @return the SQL native to the underlying database for the specified
     * ANSI SQL
     * @throws java.sql.SQLException a database error occurred
     */
    public String nativeSQL(String sql) throws SQLException {
        validate();
        return physical.nativeSQL(sql);
    }
    
    /**
     * Delegates to the physical connection.
     * @param the name of the stored procedure
     * @return a callable statement for the specified stored procedure
     * @throws java.sql.SQLException a database error occurred
     */
    public CallableStatement prepareCall(String sql) throws SQLException {
        validate();
        return physical.prepareCall(sql);
    }

    /**
     * Delegates to the physical connection.
     * @param the name of the stored procedure
     * @param rst the result set type
     * @param the result set concurrency
     * @return a callable statement for the specified stored procedure
     * @throws java.sql.SQLException a database error occurred
     */
    public CallableStatement prepareCall(String sql, int rst, int rsc)
        throws SQLException {
        validate();
        return physical.prepareCall(sql, rst, rsc);
    }
    
    /**
     * Delegates to the physical connection.
     * @param the prepared SQL
     * @return a prepared statement for the specified SQL
     * @throws java.sql.SQLException a database error occurred
     */
    public PreparedStatement prepareStatement(String sql)
        throws SQLException {
        PreparedStatement stmt;
        
        validate();
        stmt = physical.prepareStatement(sql);
        statements.add(stmt);
        return stmt;
    }

    /**
     * Delegates to the physical connection.
     * @param the prepared SQL
     * @param rst the result set type
     * @param rsc the result set concurrency
     * @return a prepared statement for the specified SQL
     * @throws java.sql.SQLException a database error occurred
     */
    public PreparedStatement prepareStatement(String sql, int rst, int rsc)
        throws SQLException {
        PreparedStatement stmt;
        
        validate();
        stmt = physical.prepareStatement(sql, rst, rsc);
        statements.add(stmt);
        return stmt;
    }

    /**
     * Delegates to the physical connection.
     * @throws java.sql.SQLException a database error occurred
     */
    public void rollback() throws SQLException {
        validate();
        physical.rollback();
    }
    
    /**
     * Delegates to the physical connection.
     * @param ac the auto-commit status to assign
     * @throws java.sql.SQLException a database error occurred
     */
    public void setAutoCommit(boolean ac) throws SQLException {
        validate();
        physical.setAutoCommit(ac);
    }

    /**
     * Delegates to the physical connection.
     * @param cat the catalog status to select
     * @throws java.sql.SQLException a database error occurred
     */
    public void setCatalog(String cat) throws SQLException {
        validate();
        physical.setCatalog(cat);
    }
    
    /**
     * Delegates to the physical connection.
     * @param ro the read-only status
     * @throws java.sql.SQLException a database error occurred
     */
    public void setReadOnly(boolean ro) throws SQLException {
        validate();
        physical.setReadOnly(ro);
    }

    /**
     * Delegates to the physical connection.
     * @param lvl the transaction isolation level to assign
     * @throws java.sql.SQLException a database error occurred
     */
    public void setTransactionIsolation(int lvl) throws SQLException {
        validate();
        physical.setTransactionIsolation(lvl);
    }

    /**
     * Delegates to the physical connection.
     * @param map the map to use for type mapping
     * @throws java.sql.SQLException a database error occurred
     */
    public void setTypeMap(Map map) throws SQLException {
        validate();
        physical.setTypeMap(map);
    }

    public String toString() {
        if( physical != null ) {
            return super.toString() + "(Physical: " + physical.toString() +")";
        }
        else {
            return super.toString();
        }
    }
    
    private void validate() throws SQLException {
        if( isClosed() ) {
            throw new SQLException("Illegal attempt to reuse connection.");
        }
    }
}
