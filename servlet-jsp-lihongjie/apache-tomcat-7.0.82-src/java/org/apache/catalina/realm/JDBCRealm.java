/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.catalina.realm;


import java.security.Principal;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.catalina.LifecycleException;
import org.apache.tomcat.util.ExceptionUtils;


/**
*
* Implementation of <b>Realm</b> that works with any JDBC supported database.
* See the JDBCRealm.howto for more details on how to set up the database and
* for configuration options.
*
* <p>For a <b>Realm</b> implementation that supports connection pooling and
* doesn't require synchronisation of <code>authenticate()</code>,
* <code>getPassword()</code>, <code>roles()</code> and
* <code>getPrincipal()</code> or the ugly connection logic use the
* <code>DataSourceRealm</code>.</p>
*
* @author Craig R. McClanahan
* @author Carson McDonald
* @author Ignacio Ortega
*/
public class JDBCRealm
    extends RealmBase {


    // ----------------------------------------------------- Instance Variables


    /**
     * The connection username to use when trying to connect to the database.
     */
    protected String connectionName = null;


    /**
     * The connection URL to use when trying to connect to the database.
     */
    protected String connectionPassword = null;


    /**
     * The connection URL to use when trying to connect to the database.
     */
    protected String connectionURL = null;


    /**
     * The connection to the database.
     */
    protected Connection dbConnection = null;


    /**
     * Instance of the JDBC Driver class we use as a connection factory.
     */
    protected Driver driver = null;


    /**
     * The JDBC driver to use.
     */
    protected String driverName = null;


    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String info =
        "org.apache.catalina.realm.JDBCRealm/1.0";


    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String name = "JDBCRealm";


    /**
     * The PreparedStatement to use for authenticating users.
     */
    protected PreparedStatement preparedCredentials = null;


    /**
     * The PreparedStatement to use for identifying the roles for
     * a specified user.
     */
    protected PreparedStatement preparedRoles = null;


    /**
     * The column in the user role table that names a role
     */
    protected String roleNameCol = null;


    /**
     * The column in the user table that holds the user's credentials
     */
    protected String userCredCol = null;


    /**
     * The column in the user table that holds the user's name
     */
    protected String userNameCol = null;


    /**
     * The table that holds the relation between user's and roles
     */
    protected String userRoleTable = null;


    /**
     * The table that holds user data.
     */
    protected String userTable = null;


    // ------------------------------------------------------------- Properties

    /**
     * Return the username to use to connect to the database.
     *
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Set the username to use to connect to the database.
     *
     * @param connectionName Username
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Return the password to use to connect to the database.
     *
     */
    public String getConnectionPassword() {
        return connectionPassword;
    }

    /**
     * Set the password to use to connect to the database.
     *
     * @param connectionPassword User password
     */
    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }

    /**
     * Return the URL to use to connect to the database.
     *
     */
    public String getConnectionURL() {
        return connectionURL;
    }

    /**
     * Set the URL to use to connect to the database.
     *
     * @param connectionURL The new connection URL
     */
    public void setConnectionURL( String connectionURL ) {
      this.connectionURL = connectionURL;
    }

    /**
     * Return the JDBC driver that will be used.
     *
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Set the JDBC driver that will be used.
     *
     * @param driverName The driver name
     */
    public void setDriverName( String driverName ) {
      this.driverName = driverName;
    }

    /**
     * Return the column in the user role table that names a role.
     *
     */
    public String getRoleNameCol() {
        return roleNameCol;
    }

    /**
     * Set the column in the user role table that names a role.
     *
     * @param roleNameCol The column name
     */
    public void setRoleNameCol( String roleNameCol ) {
        this.roleNameCol = roleNameCol;
    }

    /**
     * Return the column in the user table that holds the user's credentials.
     *
     */
    public String getUserCredCol() {
        return userCredCol;
    }

    /**
     * Set the column in the user table that holds the user's credentials.
     *
     * @param userCredCol The column name
     */
    public void setUserCredCol( String userCredCol ) {
       this.userCredCol = userCredCol;
    }

    /**
     * Return the column in the user table that holds the user's name.
     *
     */
    public String getUserNameCol() {
        return userNameCol;
    }

    /**
     * Set the column in the user table that holds the user's name.
     *
     * @param userNameCol The column name
     */
    public void setUserNameCol( String userNameCol ) {
       this.userNameCol = userNameCol;
    }

    /**
     * Return the table that holds the relation between user's and roles.
     *
     */
    public String getUserRoleTable() {
        return userRoleTable;
    }

    /**
     * Set the table that holds the relation between user's and roles.
     *
     * @param userRoleTable The table name
     */
    public void setUserRoleTable( String userRoleTable ) {
        this.userRoleTable = userRoleTable;
    }

    /**
     * Return the table that holds user data..
     *
     */
    public String getUserTable() {
        return userTable;
    }

    /**
     * Set the table that holds user data.
     *
     * @param userTable The table name
     */
    public void setUserTable( String userTable ) {
      this.userTable = userTable;
    }

    /**
     * Return descriptive information about this Realm implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    @Override
    public String getInfo() {

        return info;

    }

    // --------------------------------------------------------- Public Methods


    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * If there are any errors with the JDBC connection, executing
     * the query or anything we return null (don't authenticate). This
     * event is also logged, and the connection will be closed so that
     * a subsequent request will automatically re-open it.
     *
     *
     * @param username Username of the Principal to look up
     * @param credentials Password or other credentials to use in
     *  authenticating this username
     */
    @Override
    public synchronized Principal authenticate(String username, String credentials) {

        // Number of tries is the number of attempts to connect to the database
        // during this login attempt (if we need to open the database)
        // This needs rewritten with better pooling support, the existing code
        // needs signature changes since the Prepared statements needs cached
        // with the connections.
        // The code below will try twice if there is a SQLException so the
        // connection may try to be opened again. On normal conditions (including
        // invalid login - the above is only used once.
        int numberOfTries = 2;
        while (numberOfTries>0) {
            try {

                // Ensure that we have an open database connection
                open();

                // Acquire a Principal object for this user
                Principal principal = authenticate(dbConnection,
                                                   username, credentials);


                // Return the Principal (if any)
                return (principal);

            } catch (SQLException e) {

                // Log the problem for posterity
                containerLog.error(sm.getString("jdbcRealm.exception"), e);

                // Close the connection so that it gets reopened next time
                if (dbConnection != null)
                    close(dbConnection);

            }

            numberOfTries--;
        }

        // Worst case scenario
        return null;

    }


    // -------------------------------------------------------- Package Methods


    // ------------------------------------------------------ Protected Methods


    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param dbConnection The database connection to be used
     * @param username Username of the Principal to look up
     * @param credentials Password or other credentials to use in
     *  authenticating this username
     */
    public synchronized Principal authenticate(Connection dbConnection,
                                               String username,
                                               String credentials) {
        // No user or no credentials
        // Can't possibly authenticate, don't bother the database then
        if (username == null || credentials == null) {
            if (containerLog.isTraceEnabled())
                containerLog.trace(sm.getString("jdbcRealm.authenticateFailure",
                                                username));
            return null;
        }

        // Look up the user's credentials
        String dbCredentials = getPassword(username);

        if (dbCredentials == null) {
            // User was not found in the database.
            // Waste a bit of time as not to reveal that the user does not exist.
            compareCredentials(credentials, getClass().getName());

            if (containerLog.isTraceEnabled())
                containerLog.trace(sm.getString("jdbcRealm.authenticateFailure",
                                                username));
            return null;
        }

        // Validate the user's credentials
        boolean validated = compareCredentials(credentials, dbCredentials);

        if (validated) {
            if (containerLog.isTraceEnabled())
                containerLog.trace(sm.getString("jdbcRealm.authenticateSuccess",
                                                username));
        } else {
            if (containerLog.isTraceEnabled())
                containerLog.trace(sm.getString("jdbcRealm.authenticateFailure",
                                                username));
            return null;
        }

        ArrayList<String> roles = getRoles(username);

        // Create and return a suitable Principal for this user
        return (new GenericPrincipal(username, credentials, roles));
    }


    /**
     * Close the specified database connection.
     *
     * @param dbConnection The connection to be closed
     */
    protected void close(Connection dbConnection) {

        // Do nothing if the database connection is already closed
        if (dbConnection == null)
            return;

        // Close our prepared statements (if any)
        try {
            preparedCredentials.close();
        } catch (Throwable f) {
            ExceptionUtils.handleThrowable(f);
        }
        this.preparedCredentials = null;


        try {
            preparedRoles.close();
        } catch (Throwable f) {
            ExceptionUtils.handleThrowable(f);
        }
        this.preparedRoles = null;


        // Close this database connection, and log any errors
        try {
            dbConnection.close();
        } catch (SQLException e) {
            containerLog.warn(sm.getString("jdbcRealm.close"), e); // Just log it here
        } finally {
           this.dbConnection = null;
        }

    }


    /**
     * Return a PreparedStatement configured to perform the SELECT required
     * to retrieve user credentials for the specified username.
     *
     * @param dbConnection The database connection to be used
     * @param username Username for which credentials should be retrieved
     *
     * @exception SQLException if a database error occurs
     */
    protected PreparedStatement credentials(Connection dbConnection,
                                            String username)
        throws SQLException {

        if (preparedCredentials == null) {
            StringBuilder sb = new StringBuilder("SELECT ");
            sb.append(userCredCol);
            sb.append(" FROM ");
            sb.append(userTable);
            sb.append(" WHERE ");
            sb.append(userNameCol);
            sb.append(" = ?");

            if(containerLog.isDebugEnabled()) {
                containerLog.debug("credentials query: " + sb.toString());
            }

            preparedCredentials =
                dbConnection.prepareStatement(sb.toString());
        }

        if (username == null) {
            preparedCredentials.setNull(1,java.sql.Types.VARCHAR);
        } else {
            preparedCredentials.setString(1, username);
        }

        return (preparedCredentials);
    }


    /**
     * Return a short name for this Realm implementation.
     */
    @Override
    protected String getName() {

        return (name);

    }


    /**
     * Return the password associated with the given principal's user name.
     */
    @Override
    protected synchronized String getPassword(String username) {

        // Look up the user's credentials
        String dbCredentials = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // Number of tries is the number of attempts to connect to the database
        // during this login attempt (if we need to open the database)
        // This needs rewritten with better pooling support, the existing code
        // needs signature changes since the Prepared statements needs cached
        // with the connections.
        // The code below will try twice if there is a SQLException so the
        // connection may try to be opened again. On normal conditions (including
        // invalid login - the above is only used once.
        int numberOfTries = 2;
        while (numberOfTries > 0) {
            try {
                // Ensure that we have an open database connection
                open();

                stmt = credentials(dbConnection, username);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    dbCredentials = rs.getString(1);
                }

                dbConnection.commit();

                if (dbCredentials != null) {
                    dbCredentials = dbCredentials.trim();
                }

                return dbCredentials;

            } catch (SQLException e) {
                // Log the problem for posterity
                containerLog.error(sm.getString("jdbcRealm.exception"), e);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch(SQLException e) {
                        containerLog.warn(sm.getString(
                                "jdbcRealm.abnormalCloseResultSet"));
                    }
                }
            }

            // Close the connection so that it gets reopened next time
            if (dbConnection != null) {
                close(dbConnection);
            }

            numberOfTries--;
        }

        return (null);
    }


    /**
     * Return the Principal associated with the given user name.
     */
    @Override
    protected synchronized Principal getPrincipal(String username) {

        return (new GenericPrincipal(username,
                                     getPassword(username),
                                     getRoles(username)));

    }


    /**
     * Return the roles associated with the gven user name.
     */
    protected ArrayList<String> getRoles(String username) {

        if (allRolesMode != AllRolesMode.STRICT_MODE && !isRoleStoreDefined()) {
            // Using an authentication only configuration and no role store has
            // been defined so don't spend cycles looking
            return null;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;

        // Number of tries is the number of attempts to connect to the database
        // during this login attempt (if we need to open the database)
        // This needs rewritten wuth better pooling support, the existing code
        // needs signature changes since the Prepared statements needs cached
        // with the connections.
        // The code below will try twice if there is a SQLException so the
        // connection may try to be opened again. On normal conditions (including
        // invalid login - the above is only used once.
        int numberOfTries = 2;
        while (numberOfTries>0) {
            try {

                // Ensure that we have an open database connection
                open();

                try {
                    // Accumulate the user's roles
                    ArrayList<String> roleList = new ArrayList<String>();
                    stmt = roles(dbConnection, username);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        String role = rs.getString(1);
                        if (null!=role) {
                            roleList.add(role.trim());
                        }
                    }
                    rs.close();
                    rs = null;

                    return (roleList);

                } finally {
                    if (rs!=null) {
                        try {
                            rs.close();
                        } catch(SQLException e) {
                            containerLog.warn(sm.getString("jdbcRealm.abnormalCloseResultSet"));
                        }
                    }
                    dbConnection.commit();
                }

            } catch (SQLException e) {

                // Log the problem for posterity
                containerLog.error(sm.getString("jdbcRealm.exception"), e);

                // Close the connection so that it gets reopened next time
                if (dbConnection != null)
                    close(dbConnection);

            }

            numberOfTries--;
        }

        return null;
    }


    /**
     * Open (if necessary) and return a database connection for use by
     * this Realm.
     *
     * @exception SQLException if a database error occurs
     */
    protected Connection open() throws SQLException {

        // Do nothing if there is a database connection already open
        if (dbConnection != null)
            return (dbConnection);

        // Instantiate our database driver if necessary
        if (driver == null) {
            try {
                Class<?> clazz = Class.forName(driverName);
                driver = (Driver) clazz.newInstance();
            } catch (Throwable e) {
                ExceptionUtils.handleThrowable(e);
                throw new SQLException(e.getMessage(), e);
            }
        }

        // Open a new connection
        Properties props = new Properties();
        if (connectionName != null)
            props.put("user", connectionName);
        if (connectionPassword != null)
            props.put("password", connectionPassword);
        dbConnection = driver.connect(connectionURL, props);
        if (dbConnection == null) {
            throw new SQLException(sm.getString(
                    "jdbcRealm.open.invalidurl",driverName, connectionURL));
        }
        dbConnection.setAutoCommit(false);
        return (dbConnection);

    }


    /**
     * Release our use of this connection so that it can be recycled.
     *
     * @param dbConnection The connection to be released
     *
     * @deprecated  Unused
     */
    @Deprecated
    protected void release(Connection dbConnection) {

        // NO-OP since we are not pooling anything

    }


    /**
     * Return a PreparedStatement configured to perform the SELECT required
     * to retrieve user roles for the specified username.
     *
     * @param dbConnection The database connection to be used
     * @param username Username for which roles should be retrieved
     *
     * @exception SQLException if a database error occurs
     */
    protected synchronized PreparedStatement roles(Connection dbConnection,
            String username)
        throws SQLException {

        if (preparedRoles == null) {
            StringBuilder sb = new StringBuilder("SELECT ");
            sb.append(roleNameCol);
            sb.append(" FROM ");
            sb.append(userRoleTable);
            sb.append(" WHERE ");
            sb.append(userNameCol);
            sb.append(" = ?");
            preparedRoles =
                dbConnection.prepareStatement(sb.toString());
        }

        preparedRoles.setString(1, username);
        return (preparedRoles);

    }


    private boolean isRoleStoreDefined() {
        return userRoleTable != null || roleNameCol != null;
    }


    // ------------------------------------------------------ Lifecycle Methods

    /**
     * Prepare for the beginning of active use of the public methods of this
     * component and implement the requirements of
     * {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected void startInternal() throws LifecycleException {

        // Validate that we can open our connection - but let tomcat
        // startup in case the database is temporarily unavailable
        try {
            open();
        } catch (SQLException e) {
            containerLog.error(sm.getString("jdbcRealm.open"), e);
        }

        super.startInternal();
    }


    /**
     * Gracefully terminate the active use of the public methods of this
     * component and implement the requirements of
     * {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that needs to be reported
     */
     @Override
    protected void stopInternal() throws LifecycleException {

        super.stopInternal();

        // Close any open DB connection
        close(this.dbConnection);

    }


}
