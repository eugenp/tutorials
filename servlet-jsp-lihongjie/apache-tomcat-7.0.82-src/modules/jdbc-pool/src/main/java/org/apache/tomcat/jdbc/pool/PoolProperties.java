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


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;



import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * @author Filip Hanik
 *
 */
public class PoolProperties implements PoolConfiguration, Cloneable, Serializable {

    private static final long serialVersionUID = -8519283440854213745L;
    private static final Log log = LogFactory.getLog(PoolProperties.class);

    public static final int DEFAULT_MAX_ACTIVE = 100;

    protected static AtomicInteger poolCounter = new AtomicInteger(0);
    private volatile Properties dbProperties = new Properties();
    private volatile String url = null;
    private volatile String driverClassName = null;
    private volatile Boolean defaultAutoCommit = null;
    private volatile Boolean defaultReadOnly = null;
    private volatile int defaultTransactionIsolation = DataSourceFactory.UNKNOWN_TRANSACTIONISOLATION;
    private volatile String defaultCatalog = null;
    private volatile String connectionProperties;
    private volatile int initialSize = 10;
    private volatile int maxActive = DEFAULT_MAX_ACTIVE;
    private volatile int maxIdle = maxActive;
    private volatile int minIdle = initialSize;
    private volatile int maxWait = 30000;
    private volatile String validationQuery;
    private volatile int validationQueryTimeout = -1;
    private volatile String validatorClassName;
    private volatile Validator validator;
    private volatile boolean testOnBorrow = false;
    private volatile boolean testOnReturn = false;
    private volatile boolean testWhileIdle = false;
    private volatile int timeBetweenEvictionRunsMillis = 5000;
    private volatile int numTestsPerEvictionRun;
    private volatile int minEvictableIdleTimeMillis = 60000;
    private volatile boolean accessToUnderlyingConnectionAllowed = true;
    private volatile boolean removeAbandoned = false;
    private volatile int removeAbandonedTimeout = 60;
    private volatile boolean logAbandoned = false;
    private volatile String name = "Tomcat Connection Pool["+(poolCounter.addAndGet(1))+"-"+System.identityHashCode(PoolProperties.class)+"]";
    private volatile String password;
    private volatile String username;
    private volatile long validationInterval = 3000;
    private volatile boolean jmxEnabled = true;
    private volatile String initSQL;
    private volatile boolean testOnConnect =false;
    private volatile String jdbcInterceptors=null;
    private volatile boolean fairQueue = true;
    private volatile boolean useEquals = true;
    private volatile int abandonWhenPercentageFull = 0;
    private volatile long maxAge = 0;
    private volatile boolean useLock = false;
    private volatile InterceptorDefinition[] interceptors = null;
    private volatile int suspectTimeout = 0;
    private volatile Object dataSource = null;
    private volatile String dataSourceJNDI = null;
    private volatile boolean alternateUsernameAllowed = false;
    private volatile boolean commitOnReturn = false;
    private volatile boolean rollbackOnReturn = false;
    private volatile boolean useDisposableConnectionFacade = true;
    private volatile boolean logValidationErrors = false;
    private volatile boolean propagateInterruptState = false;
    private volatile boolean ignoreExceptionOnPreLoad = false;
    private volatile boolean useStatementFacade = true;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAbandonWhenPercentageFull(int percentage) {
        if (percentage<0) abandonWhenPercentageFull = 0;
        else if (percentage>100) abandonWhenPercentageFull = 100;
        else abandonWhenPercentageFull = percentage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAbandonWhenPercentageFull() {
        return abandonWhenPercentageFull;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFairQueue() {
        return fairQueue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFairQueue(boolean fairQueue) {
        this.fairQueue = fairQueue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAccessToUnderlyingConnectionAllowed() {
        return accessToUnderlyingConnectionAllowed;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getConnectionProperties() {
        return connectionProperties;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Properties getDbProperties() {
        return dbProperties;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Boolean isDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getDefaultCatalog() {
        return defaultCatalog;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Boolean isDefaultReadOnly() {
        return defaultReadOnly;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getDefaultTransactionIsolation() {
        return defaultTransactionIsolation;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getInitialSize() {
        return initialSize;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isLogAbandoned() {
        return logAbandoned;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getMaxActive() {
        return maxActive;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getMaxIdle() {
        return maxIdle;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getMaxWait() {
        return maxWait;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getMinIdle() {
        return minIdle;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getPoolName() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isRemoveAbandoned() {
        return removeAbandoned;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getUrl() {
        return url;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getValidationQuery() {
        return validationQuery;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getValidationQueryTimeout() {
        return validationQueryTimeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValidationQueryTimeout(int validationQueryTimeout) {
        this.validationQueryTimeout = validationQueryTimeout;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getValidatorClassName() {
        return validatorClassName;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Validator getValidator() {
        return validator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValidator(Validator validator) {
        this.validator = validator;
        if (validator!=null) {
            this.validatorClassName = validator.getClass().getName();
        } else {
            this.validatorClassName = null;
        }
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public long getValidationInterval() {
        return validationInterval;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getInitSQL() {
        return initSQL;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isTestOnConnect() {
        return testOnConnect;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String getJdbcInterceptors() {
        return jdbcInterceptors;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public InterceptorDefinition[] getJdbcInterceptorsAsArray() {
        if (interceptors == null) {
            if (jdbcInterceptors==null) {
                interceptors = new InterceptorDefinition[0];
            } else {
                String[] interceptorValues = jdbcInterceptors.split(";");
                InterceptorDefinition[] definitions = new InterceptorDefinition[interceptorValues.length+1];
                //always add the trap interceptor to the mix
                definitions[0] = new InterceptorDefinition(TrapException.class);
                for (int i=0; i<interceptorValues.length; i++) {
                    int propIndex = interceptorValues[i].indexOf('(');
                    int endIndex = interceptorValues[i].indexOf(')');
                    if (propIndex<0 || endIndex<0 || endIndex <= propIndex) {
                        definitions[i+1] = new InterceptorDefinition(interceptorValues[i].trim());
                    } else {
                        String name = interceptorValues[i].substring(0,propIndex).trim();
                        definitions[i+1] = new InterceptorDefinition(name);
                        String propsAsString = interceptorValues[i].substring(propIndex+1, interceptorValues[i].length()-1);
                        String[] props = propsAsString.split(",");
                        for (int j=0; j<props.length; j++) {
                            int pidx = props[j].indexOf('=');
                            String propName = props[j].substring(0,pidx).trim();
                            String propValue = props[j].substring(pidx+1).trim();
                            definitions[i+1].addProperty(new InterceptorProperty(propName,propValue));
                        }
                    }
                }
                interceptors = definitions;
            }
        }
        return interceptors;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setAccessToUnderlyingConnectionAllowed(boolean accessToUnderlyingConnectionAllowed) {
        // NOOP
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
        getProperties(connectionProperties, getDbProperties());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDbProperties(Properties dbProperties) {
        this.dbProperties = dbProperties;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDefaultAutoCommit(Boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDefaultCatalog(String defaultCatalog) {
        this.defaultCatalog = defaultCatalog;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDefaultReadOnly(Boolean defaultReadOnly) {
        this.defaultReadOnly = defaultReadOnly;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDefaultTransactionIsolation(int defaultTransactionIsolation) {
        this.defaultTransactionIsolation = defaultTransactionIsolation;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setLogAbandoned(boolean logAbandoned) {
        this.logAbandoned = logAbandoned;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setRemoveAbandoned(boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setTimeBetweenEvictionRunsMillis(int
                                                 timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setValidationInterval(long validationInterval) {
        this.validationInterval = validationInterval;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setValidatorClassName(String className) {
        this.validatorClassName = className;

        validator = null;

        if (className == null) {
            return;
        }

        try {
            @SuppressWarnings("unchecked")
            Class<Validator> validatorClass = (Class<Validator>)Class.forName(className);
            validator = validatorClass.newInstance();
        } catch (ClassNotFoundException e) {
            log.warn("The class "+className+" cannot be found.", e);
        } catch (ClassCastException e) {
            log.warn("The class "+className+" does not implement the Validator interface.", e);
        } catch (InstantiationException e) {
            log.warn("An object of class "+className+" cannot be instantiated. Make sure that "+
                     "it includes an implicit or explicit no-arg constructor.", e);
        } catch (IllegalAccessException e) {
            log.warn("The class "+className+" or its no-arg constructor are inaccessible.", e);
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setInitSQL(String initSQL) {
        this.initSQL = initSQL;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setTestOnConnect(boolean testOnConnect) {
        this.testOnConnect = testOnConnect;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setJdbcInterceptors(String jdbcInterceptors) {
        this.jdbcInterceptors = jdbcInterceptors;
        this.interceptors = null;
    }


    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("ConnectionPool[");
        try {
            String[] fields = DataSourceFactory.ALL_PROPERTIES;
            for (String field: fields) {
                final String[] prefix = new String[] {"get","is"};
                for (int j=0; j<prefix.length; j++) {

                    String name = prefix[j]
                            + field.substring(0, 1).toUpperCase(Locale.ENGLISH)
                            + field.substring(1);
                    Method m = null;
                    try {
                        m = getClass().getMethod(name);
                    }catch (NoSuchMethodException nm) {
                        continue;
                    }
                    buf.append(field);
                    buf.append("=");
                    if (DataSourceFactory.PROP_PASSWORD.equals(field)) {
                        buf.append("********");
                    } else {
                        buf.append(m.invoke(this, new Object[0]));
                    }
                    buf.append("; ");
                    break;
                }
            }
        }catch (Exception x) {
            //shouldn't happen
            log.debug("toString() call failed", x);
        }
        return buf.toString();
    }

    public static int getPoolCounter() {
        return poolCounter.get();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isJmxEnabled() {
        return jmxEnabled;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setJmxEnabled(boolean jmxEnabled) {
        this.jmxEnabled = jmxEnabled;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Boolean getDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Boolean getDefaultReadOnly() {
        return defaultReadOnly;
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public int getSuspectTimeout() {
        return this.suspectTimeout;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setSuspectTimeout(int seconds) {
        this.suspectTimeout = seconds;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isPoolSweeperEnabled() {
        boolean timer = getTimeBetweenEvictionRunsMillis()>0;
        boolean result = timer && (isRemoveAbandoned() && getRemoveAbandonedTimeout()>0);
        result = result || (timer && getSuspectTimeout()>0);
        result = result || (timer && isTestWhileIdle() && getValidationQuery()!=null);
        result = result || (timer && getMinEvictableIdleTimeMillis()>0);
        return result;
    }


    public static class InterceptorDefinition implements Serializable {
        private static final long serialVersionUID = 1L;
        protected String className;
        protected Map<String,InterceptorProperty> properties =
                new HashMap<String,InterceptorProperty>();
        protected volatile Class<?> clazz = null;
        public InterceptorDefinition(String className) {
            this.className = className;
        }

        public InterceptorDefinition(Class<?> cl) {
            this(cl.getName());
            clazz = cl;
        }

        public String getClassName() {
            return className;
        }
        public void addProperty(String name, String value) {
            InterceptorProperty p = new InterceptorProperty(name,value);
            addProperty(p);
        }

        public void addProperty(InterceptorProperty p) {
            properties.put(p.getName(), p);
        }

        public Map<String,InterceptorProperty> getProperties() {
            return properties;
        }

        @SuppressWarnings("unchecked")
        public Class<? extends JdbcInterceptor> getInterceptorClass() throws ClassNotFoundException {
            if (clazz==null) {
                if (getClassName().indexOf('.')<0) {
                    if (log.isDebugEnabled()) {
                        log.debug("Loading interceptor class:"+PoolConfiguration.PKG_PREFIX+getClassName());
                    }
                    clazz = Class.forName(PoolConfiguration.PKG_PREFIX+getClassName(), true, this.getClass().getClassLoader());
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Loading interceptor class:"+getClassName());
                    }
                    clazz = Class.forName(getClassName(), true, this.getClass().getClassLoader());
                }
            }
            return (Class<? extends JdbcInterceptor>)clazz;
        }
    }

    public static class InterceptorProperty implements Serializable {
        private static final long serialVersionUID = 1L;
        String name;
        String value;
        public InterceptorProperty(String name, String value) {
            assert(name!=null);
            this.name = name;
            this.value = value;
        }
        public String getName() {
            return name;
        }
        public String getValue() {
            return value;
        }

        public boolean getValueAsBoolean(boolean def) {
            if (value==null) return def;
            if ("true".equals(value)) return true;
            if ("false".equals(value)) return false;
            return def;
        }

        public int getValueAsInt(int def) {
            if (value==null) return def;
            try {
                int v = Integer.parseInt(value);
                return v;
            }catch (NumberFormatException nfe) {
                return def;
            }
        }

        public long getValueAsLong(long def) {
            if (value==null) return def;
            try {
                return Long.parseLong(value);
            }catch (NumberFormatException nfe) {
                return def;
            }
        }

        public byte getValueAsByte(byte def) {
            if (value==null) return def;
            try {
                return Byte.parseByte(value);
            }catch (NumberFormatException nfe) {
                return def;
            }
        }

        public short getValueAsShort(short def) {
            if (value==null) return def;
            try {
                return Short.parseShort(value);
            }catch (NumberFormatException nfe) {
                return def;
            }
        }

        public float getValueAsFloat(float def) {
            if (value==null) return def;
            try {
                return Float.parseFloat(value);
            }catch (NumberFormatException nfe) {
                return def;
            }
        }

        public double getValueAsDouble(double def) {
            if (value==null) return def;
            try {
                return Double.parseDouble(value);
            }catch (NumberFormatException nfe) {
                return def;
            }
        }

        public char getValueAschar(char def) {
            if (value==null) return def;
            try {
                return value.charAt(0);
            }catch (StringIndexOutOfBoundsException nfe) {
                return def;
            }
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o==this) return true;
            if (o instanceof InterceptorProperty) {
                InterceptorProperty other = (InterceptorProperty)o;
                return other.name.equals(this.name);
            }
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isUseEquals() {
        return useEquals;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setUseEquals(boolean useEquals) {
        this.useEquals = useEquals;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public long getMaxAge() {
        return maxAge;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean getUseLock() {
        return useLock;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setUseLock(boolean useLock) {
        this.useLock = useLock;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataSource(Object ds) {
        this.dataSource = ds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getDataSource() {
        return dataSource;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setDataSourceJNDI(String jndiDS) {
        this.dataSourceJNDI = jndiDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDataSourceJNDI() {
        return this.dataSourceJNDI;
    }


    public static Properties getProperties(String propText, Properties props) {
        if (props==null) props = new Properties();
        if (propText != null) {
            try {
                props.load(new ByteArrayInputStream(propText.replace(';', '\n').getBytes()));
            }catch (IOException x) {
                throw new RuntimeException(x);
            }
        }
        return props;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlternateUsernameAllowed() {
        return alternateUsernameAllowed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAlternateUsernameAllowed(boolean alternateUsernameAllowed) {
        this.alternateUsernameAllowed = alternateUsernameAllowed;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommitOnReturn(boolean commitOnReturn) {
        this.commitOnReturn = commitOnReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getCommitOnReturn() {
        return this.commitOnReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRollbackOnReturn(boolean rollbackOnReturn) {
        this.rollbackOnReturn = rollbackOnReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getRollbackOnReturn() {
        return this.rollbackOnReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUseDisposableConnectionFacade(boolean useDisposableConnectionFacade) {
        this.useDisposableConnectionFacade = useDisposableConnectionFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getUseDisposableConnectionFacade() {
        return useDisposableConnectionFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLogValidationErrors(boolean logValidationErrors) {
        this.logValidationErrors = logValidationErrors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getLogValidationErrors() {
        return this.logValidationErrors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getPropagateInterruptState() {
        return propagateInterruptState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPropagateInterruptState(boolean propagateInterruptState) {
        this.propagateInterruptState = propagateInterruptState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isIgnoreExceptionOnPreLoad() {
        return ignoreExceptionOnPreLoad;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIgnoreExceptionOnPreLoad(boolean ignoreExceptionOnPreLoad) {
        this.ignoreExceptionOnPreLoad = ignoreExceptionOnPreLoad;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getUseStatementFacade() {
        return useStatementFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUseStatementFacade(boolean useStatementFacade) {
        this.useStatementFacade = useStatementFacade;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }



}
