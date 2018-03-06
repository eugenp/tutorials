/* $Id$ */
/* Copyright © 2002 George Reese, Imaginet */
package org.dasein.persist;

// Developed by George Reese for the book:
// Java Best Practices, Volume II: J2EE
// Ported to the digital@jwt code library by George Reese

import java.io.PrintWriter;
import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

/**
 * Implementation of a connection pool data source that provides
 * for prepared statement pooling. This data source depends
 * on another data source to provide it actual database connection.
 * That other data source should not be pooled.<br/>
 * Last modified $Date$
 * @version $Revision$
 * @author George Reese
 */
public class ConnectionPool implements ConnectionPoolDataSource {
    /**
     * Inner class that performs attempts to connect to a database.
     */
    static private class ConnectionAttempt extends Thread {
        /**
         * Reference to a pooled connection that represents success.
         */
        private PhysicalConnection connection = null;
        /**
         * The DSN of the data source providing the database connection.
         */
        private String             dsn        = null;
        /**
         * Any SQL exception that occurs during the connection.
         */
        private SQLException       exception  = null;
        /**
         * The password to use to make the connection.
         */
        private String             password   = null;
        /**
         * The user ID to use to make the connection.
         */
        private String             userID     = null;
        
        /**
         * Constructs a new instance.
         * @param dsn the name of the data source for the actual connection
         * @param uid the user ID to use to make the connection
         * @param pw the password to use to make the connection
         */
        public ConnectionAttempt(String dsn, String uid, String pw) {
            super();
            this.dsn = dsn;
            this.userID = uid;
            this.password = pw;
            start();
        }

        /**
         * Makes the connection and sets either the <code>connection</code>
         * or <code>exception</code> attributes. Upon completion, calls
         * <code>this.notifyAll()</code>.
         */
        public void run() {
            synchronized( this ) {
                try {
                    connection = new PhysicalConnection(dsn, userID, password);
                }
                catch( SQLException e ) {
                    exception = e;
                }
                notifyAll();
            }
        }
    }

    /**
     * The data source name of the data source to use for the actual
     * database connections. When configuring this connection pool
     * in your JNDI repository, you should specify a value for this
     * property.
     */
    private String      dsn          = null;
    /**
     * The required log writer.
     */
    private PrintWriter logWriter    = null;
    /**
     * The required login timeout.
     */
    private int         loginTimeout = 0;

    /**
     * The data source name of the data source this pool will use
     * to create its actual connections.
     * @return the data source name
     */
    public String getDsn() {
        return dsn;
    }

    /**
     * @return the log writer
     * @throws java.sql.SQLException never actually thrown
     */
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }
    
    /**
     * @return the login timeout
     * @throws java.sql.SQLException never actually thrown
     */
    public int getLoginTimeout() throws SQLException {
        return loginTimeout;
    }

    /**
     * Provides an application data source with a reference to
     * a pooled connection. The pooled connection represents
     * the physical connection to the database. Because this
     * library is an abstraction for the sake of statement pooling,
     * our physical connection is itself an abstraction. The job
     * of the physical connection is to provide the application
     * data source with a logical connection.
     * @return a pooled database connection
     * @throws java.sql.SQLException a connection error occurred
     */
    public PooledConnection getPooledConnection() throws SQLException {
        return getPooledConnection(null, null);
    }

    /**
     * Provides an application data surce with a reference to a pooled
     * connection based on the specified authentication credentials.
     * @param uid the user ID to use in making the connection
     * @param pw the password to use in making the connection
     * @return the pooled connection
     * @throws java.sql.SQLException a connection error occurred
     */
    public PooledConnection getPooledConnection(String uid, String pw)
        throws SQLException {
        if( loginTimeout > 0 ) {
            ConnectionAttempt att = new ConnectionAttempt(dsn, uid, pw);
            long start = System.currentTimeMillis();
            long to = (long)(loginTimeout * 1000);
            
            synchronized( att ) {
                while( (System.currentTimeMillis() - start) < to ) {
                    try { att.wait(to); }
                    catch( InterruptedException e ) { }
                    if( att.connection != null ) {
                        return att.connection;
                    }
                    else if( att.exception != null ) {
                        throw att.exception;
                    }
                    else {
                        to = to - (System.currentTimeMillis() - start);
                    }
                }
                throw new SQLException("Connection timed out.");
            }
        }
        else {
            return new PhysicalConnection(dsn, uid, pw);
        }
    }

    /**
     * Sets the name of the data source to be used for generating connections.
     * @param dsn the name of the data source
     */
    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    /**
     * Assigns a print writer for logging.
     * @param out the new print writer
     * @throws java.sql.SQLException never thrown
     */
    public void setLogWriter(PrintWriter out) throws SQLException {
        logWriter = out;
    }

    /**
     * Sets a new login timeout for this pool.
     * @param sec the login timeout in seconds
     * @throws java.sql.SQLException never thrown
     */
    public void setLoginTimeout(int sec) throws SQLException {
        loginTimeout = sec;
    }
}
