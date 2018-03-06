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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.sql.DataSource;

import org.apache.catalina.LifecycleException;
import org.apache.naming.ContextBindings;

/**
*
* Implementation of <b>Realm</b> that works with any JDBC JNDI DataSource.
* See the JDBCRealm.howto for more details on how to set up the database and
* for configuration options.
*
* @author Glenn L. Nielsen
* @author Craig R. McClanahan
* @author Carson McDonald
* @author Ignacio Ortega
*/

public class DataSourceRealm
    extends RealmBase {


    // ----------------------------------------------------- Instance Variables


    /**
     * The generated string for the roles PreparedStatement
     */
    private String preparedRoles = null;


    /**
     * The generated string for the credentials PreparedStatement
     */
    private String preparedCredentials = null;


    /**
     * The name of the JNDI JDBC DataSource
     */
    protected String dataSourceName = null;


    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String info =
        "org.apache.catalina.realm.DataSourceRealm/1.0";


    /**
     * Context local datasource.
     */
    protected boolean localDataSource = false;


    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String name = "DataSourceRealm";


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
     * Return the name of the JNDI JDBC DataSource.
     *
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    /**
     * Set the name of the JNDI JDBC DataSource.
     *
     * @param dataSourceName the name of the JNDI JDBC DataSource
     */
    public void setDataSourceName( String dataSourceName) {
      this.dataSourceName = dataSourceName;
    }

    /**
     * Return if the datasource will be looked up in the webapp JNDI Context.
     */
    public boolean getLocalDataSource() {
        return localDataSource;
    }

    /**
     * Set to true to cause the datasource to be looked up in the webapp JNDI
     * Context.
     *
     * @param localDataSource the new flag value
     */
    public void setLocalDataSource(boolean localDataSource) {
      this.localDataSource = localDataSource;
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
     * @param username Username of the Principal to look up
     * @param credentials Password or other credentials to use in
     *  authenticating this username
     */
    @Override
    public Principal authenticate(String username, String credentials) {

        // No user or no credentials
        // Can't possibly authenticate, don't bother the database then
        if (username == null || credentials == null) {
            return null;
        }

        Connection dbConnection = null;

        // Ensure that we have an open database connection
        dbConnection = open();
        if (dbConnection == null) {
            // If the db connection open fails, return "not authenticated"
            return null;
        }

        try
        {
            // Acquire a Principal object for this user
            return authenticate(dbConnection, username, credentials);
        }
        finally
        {
            close(dbConnection);
        }
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
    protected Principal authenticate(Connection dbConnection,
                                     String username,
                                     String credentials) {
        // No user or no credentials
        // Can't possibly authenticate, don't bother the database then
        if (username == null || credentials == null) {
            if (containerLog.isTraceEnabled())
                containerLog.trace(sm.getString("dataSourceRealm.authenticateFailure",
                                                username));
            return null;
        }

        // Look up the user's credentials
        String dbCredentials = getPassword(dbConnection, username);

        if(dbCredentials == null) {
            // User was not found in the database.
            // Waste a bit of time as not to reveal that the user does not exist.
            compareCredentials(credentials, getClass().getName());

            if (containerLog.isTraceEnabled())
                containerLog.trace(sm.getString("dataSourceRealm.authenticateFailure",
                                                username));
            return null;
        }

        // Validate the user's credentials
        boolean validated = compareCredentials(credentials, dbCredentials);

        if (validated) {
            if (containerLog.isTraceEnabled())
                containerLog.trace(sm.getString("dataSourceRealm.authenticateSuccess",
                                                username));
        } else {
            if (containerLog.isTraceEnabled())
                containerLog.trace(sm.getString("dataSourceRealm.authenticateFailure",
                                                username));
            return null;
        }

        ArrayList<String> list = getRoles(dbConnection, username);

        // Create and return a suitable Principal for this user
        return new GenericPrincipal(username, credentials, list);
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

        // Commit if not auto committed
        try {
            if (!dbConnection.getAutoCommit()) {
                dbConnection.commit();
            }
        } catch (SQLException e) {
            containerLog.error("Exception committing connection before closing:", e);
        }

        // Close this database connection, and log any errors
        try {
            dbConnection.close();
        } catch (SQLException e) {
            containerLog.error(sm.getString("dataSourceRealm.close"), e); // Just log it here
        }

    }

    /**
     * Open the specified database connection.
     *
     * @return Connection to the database
     */
    protected Connection open() {

        try {
            Context context = null;
            if (localDataSource) {
                context = ContextBindings.getClassLoader();
                context = (Context) context.lookup("comp/env");
            } else {
                context = getServer().getGlobalNamingContext();
            }
            DataSource dataSource = (DataSource)context.lookup(dataSourceName);
        return dataSource.getConnection();
        } catch (Exception e) {
            // Log the problem for posterity
            containerLog.error(sm.getString("dataSourceRealm.exception"), e);
        }
        return null;
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
    protected String getPassword(String username) {

        Connection dbConnection = null;

        // Ensure that we have an open database connection
        dbConnection = open();
        if (dbConnection == null) {
            return null;
        }

        try {
            return getPassword(dbConnection, username);
        } finally {
            close(dbConnection);
        }
    }

    /**
     * Return the password associated with the given principal's user name.
     * @param dbConnection The database connection to be used
     * @param username Username for which password should be retrieved
     */
    protected String getPassword(Connection dbConnection,
                                 String username) {

        ResultSet rs = null;
        PreparedStatement stmt = null;
        String dbCredentials = null;

        try {
            stmt = credentials(dbConnection, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                dbCredentials = rs.getString(1);
            }

            return (dbCredentials != null) ? dbCredentials.trim() : null;

        } catch(SQLException e) {
            containerLog.error(
                    sm.getString("dataSourceRealm.getPassword.exception",
                                 username), e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                    containerLog.error(
                        sm.getString("dataSourceRealm.getPassword.exception",
                             username), e);

            }
        }

        return null;
    }


    /**
     * Return the Principal associated with the given user name.
     */
    @Override
    protected Principal getPrincipal(String username) {
        Connection dbConnection = open();
        if (dbConnection == null) {
            return new GenericPrincipal(username, null, null);
        }
        try {
            return (new GenericPrincipal(username,
                    getPassword(dbConnection, username),
                    getRoles(dbConnection, username)));
        } finally {
            close(dbConnection);
        }

    }

    /**
     * Return the roles associated with the given user name.
     * @param username Username for which roles should be retrieved
     */
    protected ArrayList<String> getRoles(String username) {

        Connection dbConnection = null;

        // Ensure that we have an open database connection
        dbConnection = open();
        if (dbConnection == null) {
            return null;
        }

        try {
            return getRoles(dbConnection, username);
        } finally {
            close(dbConnection);
        }
    }

    /**
     * Return the roles associated with the given user name
     * @param dbConnection The database connection to be used
     * @param username Username for which roles should be retrieved
     */
    protected ArrayList<String> getRoles(Connection dbConnection,
                                     String username) {

        if (allRolesMode != AllRolesMode.STRICT_MODE && !isRoleStoreDefined()) {
            // Using an authentication only configuration and no role store has
            // been defined so don't spend cycles looking
            return null;
        }

        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<String> list = null;

        try {
            stmt = roles(dbConnection, username);
            rs = stmt.executeQuery();
            list = new ArrayList<String>();

            while (rs.next()) {
                String role = rs.getString(1);
                if (role != null) {
                    list.add(role.trim());
                }
            }
            return list;
        } catch(SQLException e) {
            containerLog.error(
                sm.getString("dataSourceRealm.getRoles.exception", username), e);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                    containerLog.error(
                        sm.getString("dataSourceRealm.getRoles.exception",
                                     username), e);
            }
        }

        return null;
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
    private PreparedStatement credentials(Connection dbConnection,
                                            String username)
        throws SQLException {

        PreparedStatement credentials =
            dbConnection.prepareStatement(preparedCredentials);

        credentials.setString(1, username);
        return (credentials);

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
    private PreparedStatement roles(Connection dbConnection, String username)
        throws SQLException {

        PreparedStatement roles =
            dbConnection.prepareStatement(preparedRoles);

        roles.setString(1, username);
        return (roles);

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

        // Create the roles PreparedStatement string
        StringBuilder temp = new StringBuilder("SELECT ");
        temp.append(roleNameCol);
        temp.append(" FROM ");
        temp.append(userRoleTable);
        temp.append(" WHERE ");
        temp.append(userNameCol);
        temp.append(" = ?");
        preparedRoles = temp.toString();

        // Create the credentials PreparedStatement string
        temp = new StringBuilder("SELECT ");
        temp.append(userCredCol);
        temp.append(" FROM ");
        temp.append(userTable);
        temp.append(" WHERE ");
        temp.append(userNameCol);
        temp.append(" = ?");
        preparedCredentials = temp.toString();

        super.startInternal();
    }
}
