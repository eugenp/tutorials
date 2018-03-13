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

import java.lang.management.ManagementFactory;
import java.util.Hashtable;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;


/**
 * A DataSource that can be instantiated through IoC and implements the DataSource interface
 * since the DataSourceProxy is used as a generic proxy.
 * The DataSource simply wraps a {@link ConnectionPool} in order to provide a standard interface to the user.
 * @author Filip Hanik
 * @version 1.0
 */
public class DataSource extends DataSourceProxy implements javax.sql.DataSource,MBeanRegistration, org.apache.tomcat.jdbc.pool.jmx.ConnectionPoolMBean, javax.sql.ConnectionPoolDataSource {
    private static final Log log = LogFactory.getLog(DataSource.class);

    /**
     * Constructor for reflection only. A default set of pool properties will be created.
     */
    public DataSource() {
        super();
    }

    /**
     * Constructs a DataSource object wrapping a connection
     * @param poolProperties
     */
    public DataSource(PoolConfiguration poolProperties) {
        super(poolProperties);
    }





//===============================================================================
//  JMX Operations - Register the actual pool itself under the tomcat.jdbc domain
//===============================================================================
    protected volatile ObjectName oname = null;

    /**
     * Unregisters the underlying connection pool mbean.<br>
     * {@inheritDoc}
     */
    @Override
    public void postDeregister() {
        if (oname!=null) unregisterJmx();
    }

    /**
     * no-op<br>
     * {@inheritDoc}
     */
    @Override
    public void postRegister(Boolean registrationDone) {
        // NOOP
    }


    /**
     * no-op<br>
     * {@inheritDoc}
     */
    @Override
    public void preDeregister() throws Exception {
        // NOOP
    }

    /**
     * If the connection pool MBean exists, it will be registered during this operation.<br>
     * {@inheritDoc}
     */
    @Override
    public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
        try {
            if ( isJmxEnabled() ) { 
                this.oname = createObjectName(name);
                if (oname!=null) registerJmx();
            }
        }catch (MalformedObjectNameException x) {
            log.error("Unable to create object name for JDBC pool.",x);
        }
        return name;
    }

    /**
     * Creates the ObjectName for the ConnectionPoolMBean object to be registered
     * @param original the ObjectName for the DataSource
     * @return the ObjectName for the ConnectionPoolMBean
     * @throws MalformedObjectNameException
     */
    public ObjectName createObjectName(ObjectName original) throws MalformedObjectNameException {
        String domain = ConnectionPool.POOL_JMX_DOMAIN;
        Hashtable<String,String> properties = original.getKeyPropertyList();
        String origDomain = original.getDomain();
        properties.put("type", "ConnectionPool");
        properties.put("class", this.getClass().getName());
        if (original.getKeyProperty("path")!=null || properties.get("context")!=null) {
            //this ensures that if the registration came from tomcat, we're not losing
            //the unique domain, but putting that into as an engine attribute
            properties.put("engine", origDomain);
        }
        ObjectName name = new ObjectName(domain,properties);
        return name;
    }

    /**
     * Registers the ConnectionPoolMBean under a unique name based on the ObjectName for the DataSource
     */
    protected void registerJmx() {
        try {
            if (pool.getJmxPool()!=null) {
                MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
                mbs.registerMBean(pool.getJmxPool(), oname);
            }
        } catch (Exception e) {
            log.error("Unable to register JDBC pool with JMX",e);
        }
    }

    /**
     *
     */
    protected void unregisterJmx() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            mbs.unregisterMBean(oname);
        } catch (InstanceNotFoundException ignore) {
            // NOOP
        } catch (Exception e) {
            log.error("Unable to unregister JDBC pool with JMX",e);
        }
    }


}
