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
package org.apache.catalina.valves;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import javax.servlet.ServletException;

import org.apache.catalina.AccessLog;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.ExceptionUtils;

/**
 * <p>
 * This Tomcat extension logs server access directly to a database, and can
 * be used instead of the regular file-based access log implemented in
 * AccessLogValve.
 * To use, copy into the server/classes directory of the Tomcat installation
 * and configure in server.xml as:
 * <pre>
 *      &lt;Valve className="org.apache.catalina.valves.JDBCAccessLogValve"
 *          driverName="<i>your_jdbc_driver</i>"
 *          connectionURL="<i>your_jdbc_url</i>"
 *          pattern="combined" resolveHosts="false"
 *      /&gt;
 * </pre>
 * </p>
 * <p>
 * Many parameters can be configured, such as the database connection (with
 * <code>driverName</code> and <code>connectionURL</code>),
 * the table name (<code>tableName</code>)
 * and the field names (corresponding to the get/set method names).
 * The same options as AccessLogValve are supported, such as
 * <code>resolveHosts</code> and <code>pattern</code> ("common" or "combined"
 * only).
 * </p>
 * <p>
 * When Tomcat is started, a database connection (with autoReconnect option)
 * is created and used for all the log activity. When Tomcat is shutdown, the
 * database connection is closed.
 * This logger can be used at the level of the Engine context (being shared
 * by all the defined hosts) or the Host context (one instance of the logger
 * per host, possibly using different databases).
 * </p>
 * <p>
 * The database table can be created with the following command:
 * </p>
 * <pre>
 * CREATE TABLE access (
 * id INT UNSIGNED AUTO_INCREMENT NOT NULL,
 * remoteHost CHAR(15) NOT NULL,
 * userName CHAR(15),
 * timestamp TIMESTAMP NOT NULL,
 * virtualHost VARCHAR(64) NOT NULL,
 * method VARCHAR(8) NOT NULL,
 * query VARCHAR(255) NOT NULL,
 * status SMALLINT UNSIGNED NOT NULL,
 * bytes INT UNSIGNED NOT NULL,
 * referer VARCHAR(128),
 * userAgent VARCHAR(128),
 * PRIMARY KEY (id),
 * INDEX (timestamp),
 * INDEX (remoteHost),
 * INDEX (virtualHost),
 * INDEX (query),
 * INDEX (userAgent)
 * );
 * </pre>
 * <p>Set JDBCAccessLogValve attribute useLongContentLength="true" as you have more then 4GB outputs.
 * Please, use long SQL datatype at access.bytes attribute.
 * The datatype of bytes at oracle is <i>number</i> and other databases use <i>bytes BIGINT NOT NULL</i>.
 * </p>
 *
 * <p>
 * If the table is created as above, its name and the field names don't need
 * to be defined.
 * </p>
 * <p>
 * If the request method is "common", only these fields are used:
 * <code>remoteHost, user, timeStamp, query, status, bytes</code>
 * </p>
 * <p>
 * <i>TO DO: provide option for excluding logging of certain MIME types.</i>
 * </p>
 *
 * @author Andre de Jesus
 * @author Peter Rossbach
 */

public final class JDBCAccessLogValve extends ValveBase implements AccessLog {

    // ----------------------------------------------------------- Constructors


    /**
     * Class constructor. Initializes the fields with the default values.
     * The defaults are:
     * <pre>
     *      driverName = null;
     *      connectionURL = null;
     *      tableName = "access";
     *      remoteHostField = "remoteHost";
     *      userField = "userName";
     *      timestampField = "timestamp";
     *      virtualHostField = "virtualHost";
     *      methodField = "method";
     *      queryField = "query";
     *      statusField = "status";
     *      bytesField = "bytes";
     *      refererField = "referer";
     *      userAgentField = "userAgent";
     *      pattern = "common";
     *      resolveHosts = false;
     * </pre>
     */
    public JDBCAccessLogValve() {
        super(true);
        driverName = null;
        connectionURL = null;
        tableName = "access";
        remoteHostField = "remoteHost";
        userField = "userName";
        timestampField = "timestamp";
        virtualHostField = "virtualHost";
        methodField = "method";
        queryField = "query";
        statusField = "status";
        bytesField = "bytes";
        refererField = "referer";
        userAgentField = "userAgent";
        pattern = "common";
        resolveHosts = false;
        conn = null;
        ps = null;
        currentTimeMillis = new java.util.Date().getTime();
    }


    // ----------------------------------------------------- Instance Variables

   /**
    * Use long contentLength as you have more 4 GB output.
    * @since 6.0.15
    */
    boolean useLongContentLength = false ;

   /**
     * The connection username to use when trying to connect to the database.
     */
    String connectionName = null;


    /**
     * The connection URL to use when trying to connect to the database.
     */
    String connectionPassword = null;

   /**
     * Instance of the JDBC Driver class we use as a connection factory.
     */
    Driver driver = null;


    private String driverName;
    private String connectionURL;
    private String tableName;
    private String remoteHostField;
    private String userField;
    private String timestampField;
    private String virtualHostField;
    private String methodField;
    private String queryField;
    private String statusField;
    private String bytesField;
    private String refererField;
    private String userAgentField;
    private String pattern;
    private boolean resolveHosts;


    private Connection conn;
    private PreparedStatement ps;


    private long currentTimeMillis;

    /**
     * Should this valve set request attributes for IP address, hostname,
     * protocol and port used for the request.
     * Default is <code>true</code>.
     * @see #setRequestAttributesEnabled(boolean)
     */
    boolean requestAttributesEnabled = true;

    /**
     * The descriptive information about this implementation.
     */
    static final String info =
        "org.apache.catalina.valves.JDBCAccessLogValve/1.1";


    // ------------------------------------------------------------- Properties

    /**
     * {@inheritDoc}
     * Default is <code>true</code>.
     */
    @Override
    public void setRequestAttributesEnabled(boolean requestAttributesEnabled) {
        this.requestAttributesEnabled = requestAttributesEnabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getRequestAttributesEnabled() {
        return requestAttributesEnabled;
    }

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
     * Sets the database driver name.
     *
     * @param driverName The complete name of the database driver class.
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
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
     * Sets the JDBC URL for the database where the log is stored.
     *
     * @param connectionURL The JDBC URL of the database.
     */
    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }


    /**
     * Sets the name of the table where the logs are stored.
     *
     * @param tableName The name of the table.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    /**
     * Sets the name of the field containing the remote host.
     *
     * @param remoteHostField The name of the remote host field.
     */
    public void setRemoteHostField(String remoteHostField) {
        this.remoteHostField = remoteHostField;
    }


    /**
     * Sets the name of the field containing the remote user name.
     *
     * @param userField The name of the remote user field.
     */
    public void setUserField(String userField) {
        this.userField = userField;
    }


    /**
     * Sets the name of the field containing the server-determined timestamp.
     *
     * @param timestampField The name of the server-determined timestamp field.
     */
    public void setTimestampField(String timestampField) {
        this.timestampField = timestampField;
    }


    /**
     * Sets the name of the field containing the virtual host information
     * (this is in fact the server name).
     *
     * @param virtualHostField The name of the virtual host field.
     */
    public void setVirtualHostField(String virtualHostField) {
        this.virtualHostField = virtualHostField;
    }


    /**
     * Sets the name of the field containing the HTTP request method.
     *
     * @param methodField The name of the HTTP request method field.
     */
    public void setMethodField(String methodField) {
        this.methodField = methodField;
    }


    /**
     * Sets the name of the field containing the URL part of the HTTP query.
     *
     * @param queryField The name of the field containing the URL part of
     * the HTTP query.
     */
    public void setQueryField(String queryField) {
        this.queryField = queryField;
    }


  /**
   * Sets the name of the field containing the HTTP response status code.
   *
   * @param statusField The name of the HTTP response status code field.
   */
    public void setStatusField(String statusField) {
        this.statusField = statusField;
    }


    /**
     * Sets the name of the field containing the number of bytes returned.
     *
     * @param bytesField The name of the returned bytes field.
     */
    public void setBytesField(String bytesField) {
        this.bytesField = bytesField;
    }


    /**
     * Sets the name of the field containing the referer.
     *
     * @param refererField The referer field name.
     */
    public void setRefererField(String refererField) {
        this.refererField = refererField;
    }


    /**
     * Sets the name of the field containing the user agent.
     *
     * @param userAgentField The name of the user agent field.
     */
    public void setUserAgentField(String userAgentField) {
        this.userAgentField = userAgentField;
    }


    /**
     * Sets the logging pattern. The patterns supported correspond to the
     * file-based "common" and "combined". These are translated into the use
     * of tables containing either set of fields.
     * <P><I>TO DO: more flexible field choices.</I></P>
     *
     * @param pattern The name of the logging pattern.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }


    /**
     * Determines whether IP host name resolution is done.
     *
     * @param resolveHosts "true" or "false", if host IP resolution
     * is desired or not.
     */
    public void setResolveHosts(String resolveHosts) {
        this.resolveHosts = Boolean.parseBoolean(resolveHosts);
    }

    /**
     * get useLongContentLength
     */
    public  boolean getUseLongContentLength() {
        return this.useLongContentLength ;
    }

    /**
     * @param useLongContentLength the useLongContentLength to set
     */
    public void setUseLongContentLength(boolean useLongContentLength) {
        this.useLongContentLength = useLongContentLength;
    }

    // --------------------------------------------------------- Public Methods


    /**
     * This method is invoked by Tomcat on each query.
     *
     * @param request The Request object.
     * @param response The Response object.
     *
     * @exception IOException Should not be thrown.
     * @exception ServletException Database SQLException is wrapped
     * in a ServletException.
     */
    @Override
    public void invoke(Request request, Response response) throws IOException,
            ServletException {
        getNext().invoke(request, response);
    }


    @Override
    public void log(Request request, Response response, long time) {
        if (!getState().isAvailable()) {
            return;
        }

        final String EMPTY = "" ;

        String remoteHost;
        if(resolveHosts) {
            if (requestAttributesEnabled) {
                Object host = request.getAttribute(REMOTE_HOST_ATTRIBUTE);
                if (host == null) {
                    remoteHost = request.getRemoteHost();
                } else {
                    remoteHost = (String) host;
                }
            } else {
                remoteHost = request.getRemoteHost();
            }
        } else {
            if (requestAttributesEnabled) {
                Object addr = request.getAttribute(REMOTE_ADDR_ATTRIBUTE);
                if (addr == null) {
                    remoteHost = request.getRemoteAddr();
                } else {
                    remoteHost = (String) addr;
                }
            } else {
                remoteHost = request.getRemoteAddr();
            }
        }
        String user = request.getRemoteUser();
        String query=request.getRequestURI();

        long bytes = response.getBytesWritten(true);
        if(bytes < 0) {
            bytes = 0;
        }
        int status = response.getStatus();
        String virtualHost = EMPTY;
        String method = EMPTY;
        String referer = EMPTY;
        String userAgent = EMPTY;
        String logPattern = pattern;
        if (logPattern.equals("combined")) {
            virtualHost = request.getServerName();
            method = request.getMethod();
            referer = request.getHeader("referer");
            userAgent = request.getHeader("user-agent");
        }
        synchronized (this) {
          int numberOfTries = 2;
          while (numberOfTries>0) {
            try {
                open();

                ps.setString(1, remoteHost);
                ps.setString(2, user);
                ps.setTimestamp(3, new Timestamp(getCurrentTimeMillis()));
                ps.setString(4, query);
                ps.setInt(5, status);

                if(useLongContentLength) {
                    ps.setLong(6, bytes);
                } else {
                    if (bytes > Integer.MAX_VALUE) {
                        bytes = -1 ;
                    }
                    ps.setInt(6, (int) bytes);
                }
                if (logPattern.equals("combined")) {
                      ps.setString(7, virtualHost);
                      ps.setString(8, method);
                      ps.setString(9, referer);
                      ps.setString(10, userAgent);
                }
                ps.executeUpdate();
                return;
              } catch (SQLException e) {
                // Log the problem for posterity
                  container.getLogger().error(sm.getString("jdbcAccessLogValve.exception"), e);

                // Close the connection so that it gets reopened next time
                if (conn != null) {
                    close();
                }
              }
              numberOfTries--;
           }
        }

    }


    /**
     * Open (if necessary) and return a database connection for use by
     * this AccessLogValve.
     *
     * @exception SQLException if a database error occurs
     */
    protected void open() throws SQLException {

        // Do nothing if there is a database connection already open
        if (conn != null) {
            return ;
        }

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
        props.put("autoReconnect", "true");
        if (connectionName != null) {
            props.put("user", connectionName);
        }
        if (connectionPassword != null) {
            props.put("password", connectionPassword);
        }
        conn = driver.connect(connectionURL, props);
        conn.setAutoCommit(true);
        String logPattern = pattern;
        if (logPattern.equals("common")) {
                ps = conn.prepareStatement
                    ("INSERT INTO " + tableName + " ("
                     + remoteHostField + ", " + userField + ", "
                     + timestampField +", " + queryField + ", "
                     + statusField + ", " + bytesField
                     + ") VALUES(?, ?, ?, ?, ?, ?)");
        } else if (logPattern.equals("combined")) {
                ps = conn.prepareStatement
                    ("INSERT INTO " + tableName + " ("
                     + remoteHostField + ", " + userField + ", "
                     + timestampField + ", " + queryField + ", "
                     + statusField + ", " + bytesField + ", "
                     + virtualHostField + ", " + methodField + ", "
                     + refererField + ", " + userAgentField
                     + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
    }

    /**
     * Close the specified database connection.
     */
    protected void close() {

        // Do nothing if the database connection is already closed
        if (conn == null) {
            return;
        }

        // Close our prepared statements (if any)
        try {
            ps.close();
        } catch (Throwable f) {
            ExceptionUtils.handleThrowable(f);
        }
        this.ps = null;



        // Close this database connection, and log any errors
        try {
            conn.close();
        } catch (SQLException e) {
            container.getLogger().error(sm.getString("jdbcAccessLogValve.close"), e); // Just log it here
        } finally {
           this.conn = null;
        }

    }


    /**
     * Start this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void startInternal() throws LifecycleException {

        try {
            open() ;
        } catch (SQLException e) {
            throw new LifecycleException(e);
        }

        setState(LifecycleState.STARTING);
    }


    /**
     * Stop this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void stopInternal() throws LifecycleException {

        setState(LifecycleState.STOPPING);

        close() ;
    }


    public long getCurrentTimeMillis() {
        long systime  =  System.currentTimeMillis();
        if ((systime - currentTimeMillis) > 1000) {
            currentTimeMillis  =  new java.util.Date(systime).getTime();
        }
        return currentTimeMillis;
    }

}
