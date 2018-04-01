/* $Id$ */
/* Copyright © 2002 George Reese, Imaginet */
package org.dasein.persist;

// Developed by George Reese for the book:
// Java Best Practices, Volume II: J2EE
// Ported to the digital@jwt code library by George Reese

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * A logical statement handed to applications as an abstraction
 * of the underlying pooled prepared statement.
 * <br/>
 * Last modified $Date$
 * @version $Revision$
 * @author George Reese
 */
public class PooledStatement implements PreparedStatement {
    /**
     * The logical connection responsible for this statement.
     */
    private Connection        connection = null;
    /**
     * The list of listeners interested in closings of this statement.
     */
    private ArrayList         listeners  = new ArrayList();
    /**
     * The SQL behind this prepared statement.
     */
    private String            sql        = null;
    /**
     * The underlying physical statement.
     */
    private PreparedStatement statement  = null;

    /**
     * Constructs a new pooled statement tied to the specified
     * logical connection and prepared statement.
     * @param conn the logical database connection
     * @param sql the SQL this statemment represents
     * @param stmt the physical prepared statement
     */
    public PooledStatement(Connection conn, String sql,
                           PreparedStatement stmt) {
        super();
        this.sql = sql;
        statement = stmt;
    }

    /**
     * Delegates to the underlying prepared statement.
     * @throws java.sql.SQLException a database error occurred
     */
    public void addBatch() throws SQLException {
        validate();
        statement.addBatch();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param sql unused
     * @throws java.sql.SQLException a database error occurred
     */
    public void addBatch(String sql) throws SQLException {
        validate();
        statement.addBatch(sql);
    }

    /**
     * Adds a listener to this statement's list of listeners.
     * @param lstnr the listener to add
     */
    public void addStatementEventListener(StatementEventListener lstnr) {
        synchronized( listeners ) {
            listeners.add(lstnr);
        }
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @throws java.sql.SQLException a database error occurred
     */
    public void cancel() throws SQLException {
        validate();
        statement.cancel();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @throws java.sql.SQLException a database error occurred
     */
    public void clearBatch() throws SQLException {
        validate();
        statement.clearBatch();
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @throws java.sql.SQLException a database error occurred
     */
    public void clearParameters() throws SQLException {
        validate();
        statement.clearParameters();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @throws java.sql.SQLException a database error occurred
     */
    public void clearWarnings() throws SQLException {
        validate();
        statement.clearWarnings();
    }

    /**
     * Closes this logical statementand notifies all listeners of
     * the closure so that the physical statement can return to the pool.
     * All parameters and warnings associated with the statement
     * are cleared.
     * @throws java.sql.SQLException a database error occurred
     */
    public void close() throws SQLException {
        validate();
        statement.clearParameters();
        statement.clearWarnings();
        statement = null;
        synchronized( listeners ) {
            Iterator it = listeners.iterator();

            while( it.hasNext() ) {
                StatementEventListener lstnr;

                lstnr = (StatementEventListener)it.next();
                lstnr.statementClosed(new StatementEvent(sql, statement));
            }
        }
    }

    /**
     * Delegates to the underlying prepared statement.
     * @return true if the execution generated results
     * @throws java.sql.SQLException a database error occurred
     */
    public boolean execute() throws SQLException {
        validate();
        return statement.execute();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param sql necessary from the <code>Statement</code> interface
     * @return true if the execution generated results
     * @throws java.sql.SQLException a database error occurred
     */
    public boolean execute(String sql) throws SQLException {
        validate();
        return statement.execute(sql);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @return an array of update counts
     * @throws java.sql.SQLException a database error occurred
     */
    public int[] executeBatch() throws SQLException {
        validate();
        return statement.executeBatch();
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @return the query results
     * @throws java.sql.SQLException a database error occurred
     */
    public ResultSet executeQuery() throws SQLException {
        validate();
        return statement.executeQuery();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param sql nonsense
     * @return the query results
     * @throws java.sql.SQLException a database error occurred
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        validate();
        return statement.executeQuery(sql);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @return the number of updated rows
     * @throws java.sql.SQLException a database error occurred
     */
    public int executeUpdate() throws SQLException {
        validate();
        return statement.executeUpdate();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param sql nonsense
     * @return the number of updated rows
     * @throws java.sql.SQLException a database error occurred
     */
    public int executeUpdate(String sql) throws SQLException {
        validate();
        return statement.executeUpdate(sql);
    }

    /**
     * Provides the logical connection associated with this statement
     * @return the statement's connection
     * @throws java.sql.SQLException a database error occurred
     */
    public Connection getConnection() throws SQLException {
        validate();
        return connection;
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @return the fetch direction
     * @throws java.sql.SQLException a database error occurred
     */
    public int getFetchDirection() throws SQLException {
        validate();
        return statement.getFetchDirection();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @return the fetch size
     * @throws java.sql.SQLException a database error occurred
     */
    public int getFetchSize() throws SQLException {
        validate();
        return statement.getFetchSize();
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @return the maximum field size
     * @throws java.sql.SQLException a database error occurred
     */
    public int getMaxFieldSize() throws SQLException {
        validate();
        return statement.getMaxFieldSize();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @return the maximum number of rows
     * @throws java.sql.SQLException a database error occurred
     */
    public int getMaxRows() throws SQLException {
        validate();
        return statement.getMaxRows();
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @return the result set meta-data
     * @throws java.sql.SQLException a database error occurred
     */
    public ResultSetMetaData getMetaData() throws SQLException {
        validate();
        return statement.getMetaData();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @return more results!
     * @throws java.sql.SQLException a database error occurred
     */
    public boolean getMoreResults() throws SQLException {
        validate();
        return statement.getMoreResults();
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @return the query timeout
     * @throws java.sql.SQLException a database error occurred
     */
    public int getQueryTimeout() throws SQLException {
        validate();
        return statement.getQueryTimeout();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @return the lastt result set
     * @throws java.sql.SQLException a database error occurred
     */
    public ResultSet getResultSet() throws SQLException {
        validate();
        return statement.getResultSet();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @return the concurrency for result sets
     * @throws java.sql.SQLException a database error occurred
     */
    public int getResultSetConcurrency() throws SQLException {
        validate();
        return statement.getResultSetConcurrency();
    }

    /**
     * Delegates to the underlying prepared statement.
     * @return the type for result sets
     * @throws java.sql.SQLException a database error occurred
     */
    public int getResultSetType() throws SQLException {
        validate();
        return statement.getResultSetType();
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @return the number of rows updated
     * @throws java.sql.SQLException a database error occurred
     */
    public int getUpdateCount() throws SQLException {
        validate();
        return statement.getUpdateCount();
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @return any warnings
     * @throws java.sql.SQLException a database error occurred
     */
    public SQLWarning getWarnings() throws SQLException {
        validate();
        return statement.getWarnings();
    }

    /**
     * @return true if no statement exists
     */
    public boolean isClosed() throws SQLException {
        return (statement == null);
    }

    /**
     * Removes a listener from the stable of listeners.
     * @param lstnr the listener to remove
     */
    public void removeStatementEventListener(StatementEventListener lstnr) {
        synchronized( listeners ) {
            listeners.remove(lstnr);
        }
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param arr an array value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setArray(int idx, Array arr) throws SQLException {
        validate();
        statement.setArray(idx, arr);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param is the input stream
     * @param len the length of the stream
     * @throws java.sql.SQLException a database error occurred
     */
    public void setAsciiStream(int idx, InputStream is, int len)
        throws SQLException {
        validate();
        statement.setAsciiStream(idx, is, len);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param d the big decimal value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setBigDecimal(int idx, BigDecimal d) throws SQLException {
        validate();
        statement.setBigDecimal(idx, d);
    }
 
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param is the input stream
     * @param len the lenth of the input stream
     * @throws java.sql.SQLException a database error occurred
     */
    public void setBinaryStream(int idx, InputStream is, int len)
        throws SQLException {
        validate();
        statement.setBinaryStream(idx, is, len);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param bthe blob value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setBlob(int idx, Blob b) throws SQLException {
        validate();
        statement.setBlob(idx, b);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param b the boolean value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setBoolean(int idx, boolean b) throws SQLException {
        validate();
        statement.setBoolean(idx, b);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param b the byte value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setByte(int idx, byte b) throws SQLException {
        validate();
        statement.setByte(idx, b);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param data the binary data
     * @throws java.sql.SQLException a database error occurred
     */
    public void setBytes(int idx, byte[] data) throws SQLException {
        validate();
        statement.setBytes(idx, data);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param rdr the reader from which the value should be read
     * @param len the length of the stream
     * @throws java.sql.SQLException a database error occurred
     */
    public void setCharacterStream(int idx, Reader rdr, int len)
        throws SQLException {
        validate();
        statement.setCharacterStream(idx, rdr, len);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param c the character large object value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setClob(int idx, Clob c) throws SQLException {
        validate();
        statement.setClob(idx, c);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param name the name of the cursor
     * @throws java.sql.SQLException a database error occurred
     */
    public void setCursorName(String name) throws SQLException {
        validate();
        statement.setCursorName(name);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param d the date value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setDate(int idx, Date d) throws SQLException {
        validate();
        statement.setDate(idx, d);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param d the date value
     * @param cal the calendar of the date
     * @throws java.sql.SQLException a database error occurred
     */
    public void setDate(int idx, Date d, Calendar cal) throws SQLException {
        validate();
        statement.setDate(idx, d, cal);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param d the double value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setDouble(int idx, double d) throws SQLException {
        validate();
        statement.setDouble(idx, d);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param en whether to enable escape processing
     * @throws java.sql.SQLException a database error occurred
     */
    public void setEscapeProcessing(boolean en) throws SQLException {
        validate();
        statement.setEscapeProcessing(en);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx dir the fetch direction
     * @throws java.sql.SQLException a database error occurred
     */
    public void setFetchDirection(int dir) throws SQLException {
        validate();
        statement.setFetchDirection(dir);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param sz the fetch size
     * @throws java.sql.SQLException a database error occurred
     */
    public void setFetchSize(int sz) throws SQLException {
        validate();
        statement.setFetchSize(sz);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param f the float value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setFloat(int idx, float f) throws SQLException {
        validate();
        statement.setFloat(idx, f);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param x the integer value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setInt(int idx, int x) throws SQLException {
        validate();
        statement.setInt(idx, x);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param l the long value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setLong(int idx, long l) throws SQLException {
        validate();
        statement.setLong(idx, l);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param max the maximum field size
     * @throws java.sql.SQLException a database error occurred
     */
    public void setMaxFieldSize(int max) throws SQLException {
        validate();
        statement.setMaxFieldSize(max);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param max the maximum number of rows
     * @throws java.sql.SQLException a database error occurred
     */
    public void setMaxRows(int max) throws SQLException {
        validate();
        statement.setMaxRows(max);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param type the type from <code>java.sql.Types</code>
     * @throws java.sql.SQLException a database error occurred
     * @see java.sql.Types
     */
    public void setNull(int idx, int type) throws SQLException {
        validate();
        statement.setNull(idx, type);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param type the type from <code>java.sql.Types</code>
     * @param name the user defined type name
     * @throws java.sql.SQLException a database error occurred
     * @see java.sql.Types
     */
    public void setNull(int idx, int type, String name) throws SQLException {
        validate();
        statement.setNull(idx, type, name);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param ob the object value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setObject(int idx, Object ob) throws SQLException {
        validate();
        statement.setObject(idx, ob);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param ob the object value
     * @param type the type from <code>java.sql.Types</code>
     * @throws java.sql.SQLException a database error occurred
     * @see java.sql.Types
     */
    public void setObject(int idx, Object ob, int type) throws SQLException {
        validate();
        statement.setObject(idx, ob, type);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param ob the object value
     * @param type the type from <code>java.sql.Types</code>
     * @param scale the scale of the value
     * @throws java.sql.SQLException a database error occurred
     * @see java.sql.Types
     */
    public void setObject(int idx, Object ob, int type, int scale)
        throws SQLException {
        validate();
        statement.setObject(idx, ob, type, scale);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param sec the query timeout in seconds
     * @throws java.sql.SQLException a database error occurred
     */
    public void setQueryTimeout(int sec) throws SQLException {
        validate();
        statement.setQueryTimeout(sec);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param ref the reference value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setRef(int idx, Ref ref) throws SQLException {
        validate();
        statement.setRef(idx, ref);
    }
    
    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param s the short value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setShort(int idx, short s) throws SQLException {
        validate();
        statement.setShort(idx, s);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param str the string value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setString(int idx, String str) throws SQLException {
        validate();
        statement.setString(idx, str);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param t the time value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setTime(int idx, Time t) throws SQLException {
        validate();
        statement.setTime(idx, t);
    }

     /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param t the time value
     * @param cal the calendar for the time value
     * @throws java.sql.SQLException a database error occurred
     */
   public void setTime(int idx, Time t, Calendar cal) throws SQLException {
        validate();
        statement.setTime(idx, t, cal);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param ts the timestamp value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setTimestamp(int idx, Timestamp ts) throws SQLException {
        validate();
        statement.setTimestamp(idx, ts);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param ts the timestamp value
     * @param cal the calendar for the tiemstamp value
     * @throws java.sql.SQLException a database error occurred
     */
    public void setTimestamp(int idx, Timestamp ts, Calendar cal)
        throws SQLException {
        validate();
        statement.setTimestamp(idx, ts, cal);
    }

    /**
     * Delegates to the underlying prepared statement.
     * @param idx the index to assign a value for
     * @param is the input stream
     * @param len the length of the input stream
     * @throws java.sql.SQLException a database error occurred
     * @deprecated use setCharacterStream()
     */
    public void setUnicodeStream(int idx, InputStream is, int len)
        throws SQLException {
        validate();
        statement.setUnicodeStream(idx, is, len);
    }

    /**
     * Verifies that this statement is still valid.
     * @throws java.sql.SQLException the statement is no longer valid
     */
    private void validate() throws SQLException {
        if( isClosed() ) {
            throw new SQLException("Illegal attempt to access statement.");
        }
    }

    public String toString() {
        if( statement != null ) {
            return super.toString() + " [" + statement.toString() + "]";
        }
        else {
            return super.toString();
        }
    }
}
