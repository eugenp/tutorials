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
package org.apache.tomcat.jdbc.pool;

import java.util.Properties;

import org.apache.tomcat.jdbc.pool.PoolProperties.InterceptorDefinition;

/**
 * A list of properties that are configurable for a connection pool.
 * The {@link DataSource} object also implements this interface so that it can be easily configured through
 * an IoC container without having to specify a secondary object with a setter method.
 * @author fhanik
 *
 */

public interface PoolConfiguration {

    /**
     * JMX prefix for interceptors that register themselves with JMX
     */
    public static final String PKG_PREFIX = "org.apache.tomcat.jdbc.pool.interceptor.";

    /**
     * Connections that have been abandoned (timed out) wont get closed and reported up unless the number of connections in use are
     * above the percentage defined by abandonWhenPercentageFull.
     * The value should be between 0-100.
     * The default value is 0, which implies that connections are eligible for
     * closure as soon as removeAbandonedTimeout has been reached.
     * @param percentage a value between 0 and 100 to indicate when connections that have been abandoned/timed out are considered abandoned
     */
    public void setAbandonWhenPercentageFull(int percentage);

    /**
     * Connections that have been abandoned (timed out) wont get closed and reported up unless the number of connections in use are
     * above the percentage defined by abandonWhenPercentageFull.
     * The value should be between 0-100.
     * The default value is 0, which implies that connections are eligible for
     * closure as soon as removeAbandonedTimeout has been reached.
     * @return percentage - a value between 0 and 100 to indicate when connections that have been abandoned/timed out are considered abandoned
     */
    public int getAbandonWhenPercentageFull();

    /**
     * Returns true if a fair queue is being used by the connection pool
     * @return true if a fair waiting queue is being used
     */
    public boolean isFairQueue();

    /**
     * Set to true if you wish that calls to getConnection
     * should be treated fairly in a true FIFO fashion.
     * This uses the {@link FairBlockingQueue} implementation for the list of the idle connections.
     * The default value is true.
     * This flag is required when you want to use asynchronous connection retrieval.
     * @param fairQueue
     */
    public void setFairQueue(boolean fairQueue);

    /**
     * Property not used. Access is always allowed.
     * Access can be achieved by calling unwrap on the pooled connection. see {@link javax.sql.DataSource} interface
     * or call getConnection through reflection or cast the object as {@link javax.sql.PooledConnection}
     * @return true
     */
    public boolean isAccessToUnderlyingConnectionAllowed();

    /**
     * No-op
     * @param accessToUnderlyingConnectionAllowed parameter ignored
     */
    public void setAccessToUnderlyingConnectionAllowed(boolean accessToUnderlyingConnectionAllowed);

    /**
     * The connection properties that will be sent to the JDBC driver when establishing new connections.
     * Format of the string is [propertyName=property;] <br>
     * NOTE - The "user" and "password" properties will be passed explicitly, so they do not need to be included here.
     * The default value is null.
     */
    public String getConnectionProperties();

    /**
     * The properties that will be passed into {@link java.sql.Driver#connect(String, Properties)} method.
     * Username and password do not need to be stored here, they will be passed into the properties right before the connection is established.
     * @param connectionProperties properties - Format of the string is [propertyName=property;]*
     * Example: prop1=value1;prop2=value2
     */
    public void setConnectionProperties(String connectionProperties);

    /**
     * Returns the database properties that are passed into the {@link java.sql.Driver#connect(String, Properties)} method.
     * @return database properties that are passed into the {@link java.sql.Driver#connect(String, Properties)} method.
     */
    public Properties getDbProperties();

    /**
     * Overrides the database properties passed into the  {@link java.sql.Driver#connect(String, Properties)} method.
     * @param dbProperties
     */
    public void setDbProperties(Properties dbProperties);

    /**
     * The default auto-commit state of connections created by this pool.
     * If not set (null), default is JDBC driver default (If set to null then the {@link java.sql.Connection#setAutoCommit(boolean)} method will not be called.)
     * @return the default auto commit setting, null is Driver default.
     */
    public Boolean isDefaultAutoCommit();

    /**
     * The default auto-commit state of connections created by this pool.
     * If not set (null), default is JDBC driver default (If set to null then the {@link java.sql.Connection#setAutoCommit(boolean)} method will not be called.)
     * @return the default auto commit setting, null is Driver default.
     */
    public Boolean getDefaultAutoCommit();

    /**
     * The default auto-commit state of connections created by this pool.
     * If not set (null), default is JDBC driver default (If set to null then the {@link java.sql.Connection#setAutoCommit(boolean)} method will not be called.)
     * @param defaultAutoCommit default auto commit setting, null is Driver default.
     */
    public void setDefaultAutoCommit(Boolean defaultAutoCommit);

    /**
     * If non null, during connection creation the method {@link java.sql.Connection#setCatalog(String)} will be called with the set value.
     * @return the default catalog, null if not set and accepting the driver default.
     */
    public String getDefaultCatalog();

    /**
     * If non null, during connection creation the method {@link java.sql.Connection#setCatalog(String)} will be called with the set value.
     * @param defaultCatalog null if not set and accepting the driver default.
     */
    public void setDefaultCatalog(String defaultCatalog);

    /**
     * If non null, during connection creation the method {@link java.sql.Connection#setReadOnly(boolean)} will be called with the set value.
     * @return null if not set and accepting the driver default otherwise the read only value
     */
    public Boolean isDefaultReadOnly();

    /**
     * If non null, during connection creation the method {@link java.sql.Connection#setReadOnly(boolean)} will be called with the set value.
     * @return null if not set and accepting the driver default otherwise the read only value
     */
    public Boolean getDefaultReadOnly();

    /**
     * If non null, during connection creation the method {@link java.sql.Connection#setReadOnly(boolean)} will be called with the set value.
     * @param defaultReadOnly null if not set and accepting the driver default.
     */
    public void setDefaultReadOnly(Boolean defaultReadOnly);


    /**
     * Returns the default transaction isolation level. If set to {@link DataSourceFactory#UNKNOWN_TRANSACTIONISOLATION} the method
     * {@link java.sql.Connection#setTransactionIsolation(int)} will not be called during connection creation.
     * @return driver transaction isolation level, or -1 {@link DataSourceFactory#UNKNOWN_TRANSACTIONISOLATION} if not set.
     */
    public int getDefaultTransactionIsolation();

    /**
     * If set to {@link DataSourceFactory#UNKNOWN_TRANSACTIONISOLATION} the method
     * {@link java.sql.Connection#setTransactionIsolation(int)} will not be called during connection creation. Otherwise the method
     * will be called with the isolation level set by this property.
     * @param defaultTransactionIsolation a value of {@link java.sql.Connection#TRANSACTION_NONE}, {@link java.sql.Connection#TRANSACTION_READ_COMMITTED},
     * {@link java.sql.Connection#TRANSACTION_READ_UNCOMMITTED}, {@link java.sql.Connection#TRANSACTION_REPEATABLE_READ},
     * {@link java.sql.Connection#TRANSACTION_SERIALIZABLE} or {@link DataSourceFactory#UNKNOWN_TRANSACTIONISOLATION}
     * The last value will not be set on the connection.
     */
    public void setDefaultTransactionIsolation(int defaultTransactionIsolation);

    /**
     * The fully qualified Java class name of the JDBC driver to be used. The driver has to be accessible from the same classloader as tomcat-jdbc.jar
     * @return fully qualified JDBC driver name.
     */
    public String getDriverClassName();

    /**
     * The fully qualified Java class name of the JDBC driver to be used. The driver has to be accessible from the same classloader as tomcat-jdbc.jar
     * @param driverClassName a fully qualified Java class name of a {@link java.sql.Driver} implementation.
     */
    public void setDriverClassName(String driverClassName);

    /**
     * Returns the number of connections that will be established when the connection pool is started.
     * Default value is 10
     * @return number of connections to be started when pool is started
     */
    public int getInitialSize();

    /**
     * Set the number of connections that will be established when the connection pool is started.
     * Default value is 10.
     * If this value exceeds {@link #setMaxActive(int)} it will automatically be lowered.
     * @param initialSize the number of connections to be established.
     *
     */
    public void setInitialSize(int initialSize);

    /**
     * boolean flag to set if stack traces should be logged for application code which abandoned a Connection.
     * Logging of abandoned Connections adds overhead for every Connection borrow because a stack trace has to be generated.
     * The default value is false.
     * @return true if the connection pool logs stack traces when connections are borrowed from the pool.
     */
    public boolean isLogAbandoned();

    /**
     * boolean flag to set if stack traces should be logged for application code which abandoned a Connection.
     * Logging of abandoned Connections adds overhead for every Connection borrow because a stack trace has to be generated.
     * The default value is false.
     * @param logAbandoned set to true if stack traces should be recorded when {@link DataSource#getConnection()} is called.
     */
    public void setLogAbandoned(boolean logAbandoned);

    /**
     * The maximum number of active connections that can be allocated from this pool at the same time. The default value is 100
     * @return the maximum number of connections used by this pool
     */
    public int getMaxActive();

    /**
     * The maximum number of active connections that can be allocated from this pool at the same time. The default value is 100
     * @param maxActive hard limit for number of managed connections by this pool
     */
    public void setMaxActive(int maxActive);


    /**
     * The maximum number of connections that should be kept in the idle pool if {@link #isPoolSweeperEnabled()} returns false.
     * If the If {@link #isPoolSweeperEnabled()} returns true, then the idle pool can grow up to {@link #getMaxActive}
     * and will be shrunk according to {@link #getMinEvictableIdleTimeMillis()} setting.
     * Default value is maxActive:100
     * @return the maximum number of idle connections.
     */
    public int getMaxIdle();

    /**
     * The maximum number of connections that should be kept in the idle pool if {@link #isPoolSweeperEnabled()} returns false.
     * If the If {@link #isPoolSweeperEnabled()} returns true, then the idle pool can grow up to {@link #getMaxActive}
     * and will be shrunk according to {@link #getMinEvictableIdleTimeMillis()} setting.
     * Default value is maxActive:100
     * @param maxIdle the maximum size of the idle pool
     */
    public void setMaxIdle(int maxIdle);

    /**
     * The maximum number of milliseconds that the pool will wait (when there are no available connections and the
     * {@link #getMaxActive} has been reached) for a connection to be returned
     * before throwing an exception. Default value is 30000 (30 seconds)
     * @return the number of milliseconds to wait for a connection to become available if the pool is maxed out.
     */
    public int getMaxWait();

    /**
     * The maximum number of milliseconds that the pool will wait (when there are no available connections and the
     * {@link #getMaxActive} has been reached) for a connection to be returned
     * before throwing an exception. Default value is 30000 (30 seconds)
     * @param maxWait the maximum number of milliseconds to wait.
     */
    public void setMaxWait(int maxWait);

    /**
     * The minimum amount of time an object must sit idle in the pool before it is eligible for eviction.
     * The default value is 60000 (60 seconds).
     * @return the minimum amount of idle time in milliseconds before a connection is considered idle and eligible for eviction.
     */
    public int getMinEvictableIdleTimeMillis();

    /**
     * The minimum amount of time an object must sit idle in the pool before it is eligible for eviction.
     * The default value is 60000 (60 seconds).
     * @param minEvictableIdleTimeMillis the number of milliseconds a connection must be idle to be eligible for eviction.
     */
    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis);

    /**
     * The minimum number of established connections that should be kept in the pool at all times.
     * The connection pool can shrink below this number if validation queries fail and connections get closed.
     * Default value is derived from {@link #getInitialSize()} (also see {@link #setTestWhileIdle(boolean)}
     * The idle pool will not shrink below this value during an eviction run, hence the number of actual connections
     * can be between {@link #getMinIdle()} and somewhere between {@link #getMaxIdle()} and {@link #getMaxActive()}
     * @return the minimum number of idle or established connections
     */
    public int getMinIdle();

    /**
     * The minimum number of established connections that should be kept in the pool at all times.
     * The connection pool can shrink below this number if validation queries fail and connections get closed.
     * Default value is derived from {@link #getInitialSize()} (also see {@link #setTestWhileIdle(boolean)}
     * The idle pool will not shrink below this value during an eviction run, hence the number of actual connections
     * can be between {@link #getMinIdle()} and somewhere between {@link #getMaxIdle()} and {@link #getMaxActive()}
     *
     * @param minIdle the minimum number of idle or established connections
     */
    public void setMinIdle(int minIdle);

    /**
     * Returns the name of the connection pool. By default a JVM unique random name is assigned.
     * @return the name of the pool, should be unique in a JVM
     */
    public String getName();

    /**
     * Sets the name of the connection pool
     * @param name the name of the pool, should be unique in a runtime JVM
     */
    public void setName(String name);

    /**
     * Property not used
     * @return unknown value
     */
    public int getNumTestsPerEvictionRun();

    /**
     * Property not used
     * @param numTestsPerEvictionRun parameter ignored.
     */
    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun);

    /**
     * Returns the password used when establishing connections to the database.
     * @return the password in string format
     */
    public String getPassword();

    /**
     * Sets the password to establish the connection with.
     * The password will be included as a database property with the name 'password'.
     * @param password
     * @see #getDbProperties()
     */
    public void setPassword(String password);

    /**
     * @see #getName()
     * @return name
     */
    public String getPoolName();

    /**
     * Returns the username used to establish the connection with
     * @return the username used to establish the connection with
     */
    public String getUsername();

    /**
     * Sets the username used to establish the connection with
     * It will also be a property called 'user' in the database properties.
     * @param username
     * @see #getDbProperties()
     */
    public void setUsername(String username);


    /**
     * boolean flag to remove abandoned connections if they exceed the removeAbandonedTimout.
     * If set to true a connection is considered abandoned and eligible for removal if it has
     * been in use longer than the {@link #getRemoveAbandonedTimeout()} and the condition for
     * {@link #getAbandonWhenPercentageFull()} is met.
     * Setting this to true can recover db connections from applications that fail to close a connection.
     * See also {@link #isLogAbandoned()} The default value is false.
     * @return true if abandoned connections can be closed and expelled out of the pool
     */
    public boolean isRemoveAbandoned();

    /**
     * boolean flag to remove abandoned connections if they exceed the removeAbandonedTimout.
     * If set to true a connection is considered abandoned and eligible for removal if it has
     * been in use longer than the {@link #getRemoveAbandonedTimeout()} and the condition for
     * {@link #getAbandonWhenPercentageFull()} is met.
     * Setting this to true can recover db connections from applications that fail to close a connection.
     * See also {@link #isLogAbandoned()} The default value is false.
     * @param removeAbandoned set to true if abandoned connections can be closed and expelled out of the pool
     */
    public void setRemoveAbandoned(boolean removeAbandoned);

    /**
     * The time in seconds before a connection can be considered abandoned.
     * The timer can be reset upon queries using an interceptor.
     * @param removeAbandonedTimeout the time in seconds before a used connection can be considered abandoned
     * @see org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer
     */
    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout);

    /**
     * The time in seconds before a connection can be considered abandoned.
     * The timer can be reset upon queries using an interceptor.
     * @see org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer
     * @return the time in seconds before a used connection can be considered abandoned
     */
    public int getRemoveAbandonedTimeout();

    /**
     * The indication of whether objects will be validated before being borrowed from the pool.
     * If the object fails to validate, it will be dropped from the pool, and we will attempt to borrow another.
     * NOTE - for a true value to have any effect, the validationQuery parameter must be set to a non-null string.
     * Default value is false
     * In order to have a more efficient validation, see {@link #setValidationInterval(long)}
     * @return true if the connection is to be validated upon borrowing a connection from the pool
     * @see #getValidationInterval()
     */
    public boolean isTestOnBorrow();

    /**
     * The indication of whether objects will be validated before being borrowed from the pool.
     * If the object fails to validate, it will be dropped from the pool, and we will attempt to borrow another.
     * NOTE - for a true value to have any effect, the validationQuery parameter must be set to a non-null string.
     * Default value is false
     * In order to have a more efficient validation, see {@link #setValidationInterval(long)}
     * @param testOnBorrow set to true if validation should take place before a connection is handed out to the application
     * @see #getValidationInterval()
     */
    public void setTestOnBorrow(boolean testOnBorrow);

    /**
     * The indication of whether objects will be validated after being returned to the pool.
     * If the object fails to validate, it will be dropped from the pool.
     * NOTE - for a true value to have any effect, the validationQuery parameter must be set to a non-null string.
     * Default value is false
     * In order to have a more efficient validation, see {@link #setValidationInterval(long)}
     * @return true if validation should take place after a connection is returned to the pool
     * @see #getValidationInterval()
     */
    public boolean isTestOnReturn();

    /**
     * The indication of whether objects will be validated after being returned to the pool.
     * If the object fails to validate, it will be dropped from the pool.
     * NOTE - for a true value to have any effect, the validationQuery parameter must be set to a non-null string.
     * Default value is false
     * In order to have a more efficient validation, see {@link #setValidationInterval(long)}
     * @param testOnReturn true if validation should take place after a connection is returned to the pool
     * @see #getValidationInterval()
     */
    public void setTestOnReturn(boolean testOnReturn);


    /**
     * Set to true if query validation should take place while the connection is idle.
     * @return true if validation should take place during idle checks
     * @see #setTimeBetweenEvictionRunsMillis(int)
     */
    public boolean isTestWhileIdle();

    /**
     * Set to true if query validation should take place while the connection is idle.
     * @param testWhileIdle true if validation should take place during idle checks
     * @see #setTimeBetweenEvictionRunsMillis(int)
     */
    public void setTestWhileIdle(boolean testWhileIdle);

    /**
     * The number of milliseconds to sleep between runs of the idle connection validation, abandoned cleaner
     * and idle pool resizing. This value should not be set under 1 second.
     * It dictates how often we check for idle, abandoned connections, and how often we validate idle connection and resize the idle pool.
     * The default value is 5000 (5 seconds)
     * @return the sleep time in between validations in milliseconds
     */
    public int getTimeBetweenEvictionRunsMillis();

    /**
     * The number of milliseconds to sleep between runs of the idle connection validation, abandoned cleaner
     * and idle pool resizing. This value should not be set under 1 second.
     * It dictates how often we check for idle, abandoned connections, and how often we validate idle connection and resize the idle pool.
     * The default value is 5000 (5 seconds)
     * @param timeBetweenEvictionRunsMillis the sleep time in between validations in milliseconds
     */
    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis);

    /**
     * The URL used to connect to the database
     * @return the configured URL for this connection pool
     * @see java.sql.Driver#connect(String, Properties)
     */
    public String getUrl();

    /**
     * Sets the URL used to connect to the database
     * @param url the configured URL for this connection pool
     * @see java.sql.Driver#connect(String, Properties)
     */
    public void setUrl(String url);

    /**
     * The SQL query that will be used to validate connections from this
     * pool before returning them to the caller or pool.
     * If specified, this query does not have to return any data,
     * it just can't throw a SQLException.
     * The default value is null.
     * Example values are SELECT 1(mysql),
     * select 1 from dual(oracle),
     * SELECT 1(MS Sql Server)
     * @return the query used for validation or null if no validation is performed
     */
    public String getValidationQuery();

    /**
     * The SQL query that will be used to validate connections from this
     * pool before returning them to the caller or pool.
     * If specified, this query does not have to return any data,
     * it just can't throw a SQLException.
     * The default value is null.
     * Example values are SELECT 1(mysql),
     * select 1 from dual(oracle),
     * SELECT 1(MS Sql Server)
     * @param validationQuery the query used for validation or null if no validation is performed
     */
    public void setValidationQuery(String validationQuery);

    /**
     * The timeout in seconds before a connection validation queries fail.
     * A value less than or equal to zero will disable this feature.  Defaults to -1.
     * @return the timeout value in seconds
     */
    public int getValidationQueryTimeout();

    /**
     * The timeout in seconds before a connection validation queries fail.
     * A value less than or equal to zero will disable this feature.  Defaults to -1.
     */
    public void setValidationQueryTimeout(int validationQueryTimeout);

    /**
     * Return the name of the optional validator class - may be null.
     *
     * @return the name of the optional validator class - may be null
     */
    public String getValidatorClassName();

    /**
     * Set the name for an optional validator class which will be used in place of test queries. If set to
     * null, standard validation will be used.
     *
     * @param className the name of the optional validator class
     */
    public void setValidatorClassName(String className);

    /**
     * @return the optional validator object - may be null
     */
    public Validator getValidator();

    /**
     * Sets the validator object
     * If this is a non null object, it will be used as a validator instead of the validationQuery
     * If this is null, remove the usage of the validator.
     */
    public void setValidator(Validator validator);

    /**
     * avoid excess validation, only run validation at most at this frequency - time in milliseconds.
     * If a connection is due for validation, but has been validated previously
     * within this interval, it will not be validated again.
     * The default value is 3000 (3 seconds).
     * @return the validation interval in milliseconds
     */
    public long getValidationInterval();

    /**
     * avoid excess validation, only run validation at most at this frequency - time in milliseconds.
     * If a connection is due for validation, but has been validated previously
     * within this interval, it will not be validated again.
     * The default value is 3000 (3 seconds).
     * @param validationInterval the validation interval in milliseconds
     */
    public void setValidationInterval(long validationInterval);

    /**
     * A custom query to be run when a connection is first created. The default value is null.
     * This query only runs once per connection, and that is when a new connection is established to the database.
     * If this value is non null, it will replace the validation query during connection creation.
     * @return the init SQL used to run against the DB or null if not set
     */
    public String getInitSQL();

    /**
     * A custom query to be run when a connection is first created. The default value is null.
     * This query only runs once per connection, and that is when a new connection is established to the database.
     * If this value is non null, it will replace the validation query during connection creation.
     * @param initSQL the init SQL used to run against the DB or null if no query should be executed
     */
    public void setInitSQL(String initSQL);

    /**
     * Returns true if we should run the validation query when connecting to the database for the first time on a connection.
     * Normally this is always set to false, unless one wants to use the validationQuery as an init query.
     * @return true if we should run the validation query upon connect
     */
    public boolean isTestOnConnect();

    /**
     * Set to true if we should run the validation query when connecting to the database for the first time on a connection.
     * Normally this is always set to false, unless one wants to use the validationQuery as an init query.
     * Setting an {@link #setInitSQL(String)} will override this setting, as the init SQL will be used instead of the validation query
     * @param testOnConnect set to true if we should run the validation query upon connect
     */
    public void setTestOnConnect(boolean testOnConnect);

    /**
     * A semicolon separated list of classnames extending {@link org.apache.tomcat.jdbc.pool.JdbcInterceptor} class.
     * These interceptors will be inserted as an interceptor into the chain of operations on a java.sql.Connection object.
     * Example interceptors are {@link org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer StatementFinalizer} to close all
     * used statements during the session.
     * {@link org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer ResetAbandonedTimer} resets the timer upon every operation
     * on the connection or a statement.
     * {@link org.apache.tomcat.jdbc.pool.interceptor.ConnectionState ConnectionState} caches the auto commit, read only and catalog settings to avoid round trips to the DB.
     * The default value is null.
     * @return the interceptors that are used for connections.
     * Example format: 'ConnectionState(useEquals=true,fast=yes);ResetAbandonedTimer'
     */
    public String getJdbcInterceptors();

    /**
     * A semicolon separated list of classnames extending {@link org.apache.tomcat.jdbc.pool.JdbcInterceptor} class.
     * These interceptors will be inserted as an interceptor into the chain of operations on a java.sql.Connection object.
     * Example interceptors are {@link org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer StatementFinalizer} to close all
     * used statements during the session.
     * {@link org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer ResetAbandonedTimer} resets the timer upon every operation
     * on the connection or a statement.
     * {@link org.apache.tomcat.jdbc.pool.interceptor.ConnectionState ConnectionState} caches the auto commit, read only and catalog settings to avoid round trips to the DB.
     * The default value is null.
     * @param jdbcInterceptors the interceptors that are used for connections.
     * Example format: 'ConnectionState(useEquals=true,fast=yes);ResetAbandonedTimer'
     */
    public void setJdbcInterceptors(String jdbcInterceptors);

    /**
     * Returns the {@link #getJdbcInterceptors()} as an array of objects with properties and the classes.
     * @return an array of interceptors that have been configured
     */
    public InterceptorDefinition[] getJdbcInterceptorsAsArray();


    /**
     * If set to true, the connection pool creates a {@link org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean} object
     * that can be registered with JMX to receive notifications and state about the pool.
     * The ConnectionPool object doesn't register itself, as there is no way to keep a static non changing ObjectName across JVM restarts.
     * @return true if the mbean object will be created upon startup.
     */
    public boolean isJmxEnabled();

    /**
     * If set to true, the connection pool creates a {@link org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean} object
     * that can be registered with JMX to receive notifications and state about the pool.
     * The ConnectionPool object doesn't register itself, as there is no way to keep a static non changing ObjectName across JVM restarts.
     * @param jmxEnabled set to to if the mbean object should be created upon startup.
     */
    public void setJmxEnabled(boolean jmxEnabled);

    /**
     * Returns true if the pool sweeper is enabled for the connection pool.
     * The pool sweeper is enabled if any settings that require async intervention in the pool are turned on
     * <source>
        boolean result = getTimeBetweenEvictionRunsMillis()>0;
        result = result && (isRemoveAbandoned() && getRemoveAbandonedTimeout()>0);
        result = result || (isTestWhileIdle() && getValidationQuery()!=null);
        return result;
       </source>
     *
     * @return true if a background thread is or will be enabled for this pool
     */
    public boolean isPoolSweeperEnabled();

    /**
     * Set to true if you wish the <code>ProxyConnection</code> class to use <code>String.equals</code> instead of
     * <code>==</code> when comparing method names.
     * This property does not apply to added interceptors as those are configured individually.
     * The default value is <code>false</code>.
     * @return true if pool uses {@link String#equals(Object)} instead of == when comparing method names on {@link java.sql.Connection} methods
     */
    public boolean isUseEquals();

    /**
     * Set to true if you wish the <code>ProxyConnection</code> class to use <code>String.equals</code> instead of
     * <code>==</code> when comparing method names.
     * This property does not apply to added interceptors as those are configured individually.
     * The default value is <code>false</code>.
     * @param useEquals set to true if the pool should use {@link String#equals(Object)} instead of ==
     * when comparing method names on {@link java.sql.Connection} methods
     */
    public void setUseEquals(boolean useEquals);

    /**
     * Time in milliseconds to keep this connection alive even when used.
     * When a connection is returned to the pool, the pool will check to see if the
     * ((now - time-when-connected) > maxAge) has been reached, and if so,
     * it closes the connection rather than returning it to the pool.
     * The default value is 0, which implies that connections will be left open and no
     * age check will be done upon returning the connection to the pool.
     * This is a useful setting for database sessions that leak memory as it ensures that the session
     * will have a finite life span.
     * @return the time in milliseconds a connection will be open for when used
     */
    public long getMaxAge();

    /**
     * Time in milliseconds to keep this connection alive even when used.
     * When a connection is returned to the pool, the pool will check to see if the
     * ((now - time-when-connected) > maxAge) has been reached, and if so,
     * it closes the connection rather than returning it to the pool.
     * The default value is 0, which implies that connections will be left open and no
     * age check will be done upon returning the connection to the pool.
     * This is a useful setting for database sessions that leak memory as it ensures that the session
     * will have a finite life span.
     * @param maxAge the time in milliseconds a connection will be open for when used
     */
    public void setMaxAge(long maxAge);

    /**
     * Return true if a lock should be used when operations are performed on the connection object.
     * Should be set to false unless you plan to have a background thread of your own doing idle and abandon checking
     * such as JMX clients. If the pool sweeper is enabled, then the lock will automatically be used regardless of this setting.
     * @return true if a lock is used.
     */
    public boolean getUseLock();

    /**
     * Set to true if a lock should be used when operations are performed on the connection object.
     * Should be set to false unless you plan to have a background thread of your own doing idle and abandon checking
     * such as JMX clients. If the pool sweeper is enabled, then the lock will automatically be used regardless of this setting.
     * @param useLock set to true if a lock should be used on connection operations
     */
    public void setUseLock(boolean useLock);

    /**
     * Similar to {@link #setRemoveAbandonedTimeout(int)} but instead of treating the connection
     * as abandoned, and potentially closing the connection, this simply logs the warning if
     * {@link #isLogAbandoned()} returns true. If this value is equal or less than 0, no suspect
     * checking will be performed. Suspect checking only takes place if the timeout value is larger than 0 and
     * the connection was not abandoned or if abandon check is disabled. If a connection is suspect a WARN message gets
     * logged and a JMX notification gets sent once.
     * @param seconds - the amount of time in seconds that has to pass before a connection is marked suspect.
     */
    public void setSuspectTimeout(int seconds);

    /**
     * Returns the time in seconds to pass before a connection is marked an abandoned suspect.
     * Any value lesser than or equal to 0 means the check is disabled.
     * @return Returns the time in seconds to pass before a connection is marked an abandoned suspect.
     */
    public int getSuspectTimeout();

    /**
     * Injects a datasource that will be used to retrieve/create connections.
     * If a data source is set, the {@link PoolConfiguration#getUrl()} and {@link PoolConfiguration#getDriverClassName()} methods are ignored
     * and not used by the pool. If the {@link PoolConfiguration#getUsername()} and {@link PoolConfiguration#getPassword()}
     * values are set, the method {@link javax.sql.DataSource#getConnection(String, String)} method will be called instead of the
     * {@link javax.sql.DataSource#getConnection()} method.
     * If the data source implements {@link javax.sql.XADataSource} the methods
     * {@link javax.sql.XADataSource#getXAConnection()} and {@link javax.sql.XADataSource#getXAConnection(String,String)}
     * will be invoked.
     * @param ds the {@link javax.sql.DataSource} to be used for creating connections to be pooled.
     */
    public void setDataSource(Object ds);

    /**
     * Returns a datasource, if one exists that is being used to create connections.
     * This method will return null if the pool is using a {@link java.sql.Driver}
     * @return the {@link javax.sql.DataSource} to be used for creating connections to be pooled or null if a Driver is used.
     */
    public Object getDataSource();

    /**
     * Configure the connection pool to use a DataSource according to {@link PoolConfiguration#setDataSource(Object)}
     * But instead of injecting the object, specify the JNDI location.
     * After a successful JNDI look, the {@link PoolConfiguration#getDataSource()} will not return null.
     * @param jndiDS -the JNDI string @TODO specify the rules here.
     */
    public void setDataSourceJNDI(String jndiDS);

    /**
     * Returns the JNDI string configured for data source usage.
     * @return the JNDI string or null if not set
     */
    public String getDataSourceJNDI();

    /**
     * Returns true if the call {@link DataSource#getConnection(String, String) getConnection(username,password)} is
     * allowed. This is used for when the pool is used by an application accessing multiple schemas.
     * There is a performance impact turning this option on.
     * @return true if {@link DataSource#getConnection(String, String) getConnection(username,password)} is honored, false if it is ignored.
     */
    public boolean isAlternateUsernameAllowed();

    /**
     * Set to true if the call {@link DataSource#getConnection(String, String) getConnection(username,password)} is
     * allowed and honored.. This is used for when the pool is used by an application accessing multiple schemas.
     * There is a performance impact turning this option on, even when not used due to username checks.
     * @param alternateUsernameAllowed - set true if {@link DataSource#getConnection(String, String) getConnection(username,password)} is honored,
     * false if it is to be ignored.
     */
    public void setAlternateUsernameAllowed(boolean alternateUsernameAllowed);
    /**
     * Set to true if you want the connection pool to commit any pending transaction when a connection is returned.
     * The default value is false, as this could result in committing data.
     * This parameter is only looked at if the {@link #getDefaultAutoCommit()} returns false
     * @param commitOnReturn set to true if the pool should call {@link java.sql.Connection#commit()} when a connection is returned to the pool.
     * Default is false
     */
    public void setCommitOnReturn(boolean commitOnReturn);

    /**
     * @see PoolConfiguration#setCommitOnReturn(boolean)
     */
    public boolean getCommitOnReturn();

    /**
     * Set to true if you want the connection pool to rollback any pending transaction when a connection is returned.
     * The default value is false, as this could result in committing data.
     * This parameter is only looked at if the {@link #getDefaultAutoCommit()} returns false
     * @param rollbackOnReturn set to true if the pool should call {@link java.sql.Connection#rollback()} when a connection is returned to the pool.
     * Default is false
     */
    public void setRollbackOnReturn(boolean rollbackOnReturn);

    /**
     * @see PoolConfiguration#setRollbackOnReturn(boolean)
     */
    public boolean getRollbackOnReturn();

    /**
     * If set to true, the connection will be wrapped with facade that will disallow the connection to be used after
     * {@link java.sql.Connection#close()} is called. If set to true, after {@link java.sql.Connection#close()} all calls except
     * {@link java.sql.Connection#close()} and {@link java.sql.Connection#isClosed()} will throw an exception.
     * @param useDisposableConnectionFacade
     */
    public void setUseDisposableConnectionFacade(boolean useDisposableConnectionFacade);
    /**
     * Returns true if this connection pool is configured to use a connection facade to prevent re-use of connection after
     * {@link java.sql.Connection#close()} has been invoked
     * @return true if {@link java.sql.Connection#close()} has been invoked.
     */
    public boolean getUseDisposableConnectionFacade();

    /**
     * Set to true if you wish that errors from validation should be logged as error messages.
     * @param logValidationErrors set to true to log validation errors
     */
    public void setLogValidationErrors(boolean logValidationErrors);

    /**
     * Returns true if errors that happen during validation will be logged
     * @return true if errors that happen during validation will be logged
     */
    public boolean getLogValidationErrors();

    /**
     * Returns true if the pool is configured to propagate interrupt state of a thread.
     * A thread waiting for a connection, can have its wait interrupted, and by default
     * will clear the interrupt flag and throw a {@link PoolExhaustedException}
     * @return true if the pool is configured to propagate and not clear the thread interrupt state
     */
    public boolean getPropagateInterruptState();

    /**
     * Configure the pool to propagate interrupt state for interrupted threads waiting for a connection
     * A thread waiting for a connection, can have its wait interrupted, and by default
     * will clear the interrupt flag and throw a {@link PoolExhaustedException}
     * If set to true, this behavior will change, while the {@link PoolExhaustedException} is still thrown, the threads interrupted state is still set.
     * @param propagateInterruptState - set to true to not clear, but propagate, a threads interrupted state.
     */
    public void setPropagateInterruptState(boolean propagateInterruptState);

    /**
     * Set to true if you want to ignore error of connection creation while initializing the pool.
     * Set to false if you want to fail the initialization of the pool by throwing exception.
     * @param ignoreExceptionOnPreLoad set to true if you want to ignore error of connection creation while initializing the pool.
     */
    public void setIgnoreExceptionOnPreLoad(boolean ignoreExceptionOnPreLoad);

    /**
     * @see PoolConfiguration#setIgnoreExceptionOnPreLoad(boolean)
     */
    public boolean isIgnoreExceptionOnPreLoad();

    /**
     * Set this to true if you wish to wrap statements in order to enable equals() and hashCode()
     * methods to be called on the closed statements if any statement proxy is set.
     * @param useStatementFacade set to <code>true</code> to wrap statements
     */
    public void setUseStatementFacade(boolean useStatementFacade);

    /**
     * Returns <code>true</code> if this connection pool is configured to wrap statements in order
     * to enable equals() and hashCode() methods to be called on the closed statements if any
     * statement proxy is set.
     * @return <code>true</code> if the statements are wrapped
     */
    public boolean getUseStatementFacade();
}
