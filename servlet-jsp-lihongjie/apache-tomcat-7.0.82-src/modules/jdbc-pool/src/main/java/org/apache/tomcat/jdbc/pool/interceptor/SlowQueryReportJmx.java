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
package org.apache.tomcat.jdbc.pool.interceptor;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.RuntimeOperationsException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.PoolProperties.InterceptorProperty;
import org.apache.tomcat.jdbc.pool.PooledConnection;
/**
 * Publishes data to JMX and provides notifications
 * when failures happen.
 * @author fhanik
 *
 */
public class SlowQueryReportJmx extends SlowQueryReport implements NotificationEmitter, SlowQueryReportJmxMBean{
    public static final String SLOW_QUERY_NOTIFICATION = "SLOW QUERY";
    public static final String FAILED_QUERY_NOTIFICATION = "FAILED QUERY";

    public static final String objectNameAttribute = "objectName";

    protected static volatile CompositeType SLOW_QUERY_TYPE;

    private static final Log log = LogFactory.getLog(SlowQueryReportJmx.class);


    protected static ConcurrentHashMap<String,SlowQueryReportJmxMBean> mbeans =
        new ConcurrentHashMap<String,SlowQueryReportJmxMBean>();


    //==============================JMX STUFF========================
    protected volatile NotificationBroadcasterSupport notifier = new NotificationBroadcasterSupport();

    @Override
    public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
        notifier.addNotificationListener(listener, filter, handback);
    }


    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return notifier.getNotificationInfo();
    }

    @Override
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        notifier.removeNotificationListener(listener);

    }

    @Override
    public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws ListenerNotFoundException {
        notifier.removeNotificationListener(listener, filter, handback);

    }


    //==============================JMX STUFF========================

    protected String poolName = null;

    protected static AtomicLong notifySequence = new AtomicLong(0);

    protected boolean notifyPool = true;

    protected ConnectionPool pool = null;

    protected static CompositeType getCompositeType() {
        if (SLOW_QUERY_TYPE==null) {
            try {
                SLOW_QUERY_TYPE = new CompositeType(
                        SlowQueryReportJmx.class.getName(),
                        "Composite data type for query statistics",
                        QueryStats.getFieldNames(),
                        QueryStats.getFieldDescriptions(),
                        QueryStats.getFieldTypes());
            }catch (OpenDataException x) {
                log.warn("Unable to initialize composite data type for JMX stats and notifications.",x);
            }
        }
        return SLOW_QUERY_TYPE;
    }

    @Override
    public void reset(ConnectionPool parent, PooledConnection con) {
        super.reset(parent, con);
        if (parent!=null) {
            poolName = parent.getName();
            pool = parent;
            registerJmx();
        }
    }


    @Override
    public void poolClosed(ConnectionPool pool) {
        this.poolName = pool.getName();
        deregisterJmx();
        super.poolClosed(pool);
    }

    @Override
    public void poolStarted(ConnectionPool pool) {
        this.pool = pool;
        super.poolStarted(pool);
        this.poolName = pool.getName();
    }

    @Override
    protected String reportFailedQuery(String query, Object[] args, String name, long start, Throwable t) {
        query = super.reportFailedQuery(query, args, name, start, t);
        notifyJmx(query,FAILED_QUERY_NOTIFICATION);
        return query;
    }

    protected void notifyJmx(String query, String type) {
        try {
            long sequence = notifySequence.incrementAndGet();

            if (isNotifyPool()) {
                if (this.pool!=null && this.pool.getJmxPool()!=null) {
                    this.pool.getJmxPool().notify(type, query);
                }
            } else {
                if (notifier!=null) {
                    Notification notification =
                        new Notification(type,
                                         this,
                                         sequence,
                                         System.currentTimeMillis(),
                                         query);

                    notifier.sendNotification(notification);
                }
            }
        } catch (RuntimeOperationsException e) {
            if (log.isDebugEnabled()) {
                log.debug("Unable to send failed query notification.",e);
            }
        }
    }

    @Override
    protected String reportSlowQuery(String query, Object[] args, String name, long start, long delta) {
        query = super.reportSlowQuery(query, args, name, start, delta);
        notifyJmx(query,SLOW_QUERY_NOTIFICATION);
        return query;
    }

    /**
     * JMX operation - return the names of all the pools
     * @return - all the names of pools that we have stored data for
     */
    public String[] getPoolNames() {
        Set<String> keys = perPoolStats.keySet();
        return keys.toArray(new String[0]);
    }

    /**
     * JMX operation - return the name of the pool
     * @return the name of the pool, unique within the JVM
     */
    public String getPoolName() {
        return poolName;
    }


    public boolean isNotifyPool() {
        return notifyPool;
    }

    public void setNotifyPool(boolean notifyPool) {
        this.notifyPool = notifyPool;
    }

    /**
     * JMX operation - remove all stats for this connection pool
     */
    public void resetStats() {
        ConcurrentHashMap<String,QueryStats> queries = perPoolStats.get(poolName);
        if (queries!=null) {
            Iterator<String> it = queries.keySet().iterator();
            while (it.hasNext()) it.remove();
        }
    }

    /**
     * JMX operation - returns all the queries we have collected.
     * @return - the slow query report as composite data.
     */
    @Override
    public CompositeData[] getSlowQueriesCD() throws OpenDataException {
        CompositeDataSupport[] result = null;
        ConcurrentHashMap<String,QueryStats> queries = perPoolStats.get(poolName);
        if (queries!=null) {
            Set<Map.Entry<String,QueryStats>> stats = queries.entrySet();
            if (stats!=null) {
                result = new CompositeDataSupport[stats.size()];
                Iterator<Map.Entry<String,QueryStats>> it = stats.iterator();
                int pos = 0;
                while (it.hasNext()) {
                    Map.Entry<String,QueryStats> entry = it.next();
                    QueryStats qs = entry.getValue();
                    result[pos++] = qs.getCompositeData(getCompositeType());
                }
            }
        }
        return result;
    }

    protected void deregisterJmx() {
        try {
            if (mbeans.remove(poolName)!=null) {
                ObjectName oname = getObjectName(getClass(),poolName);
                ManagementFactory.getPlatformMBeanServer().unregisterMBean(oname);
            }
        } catch (MBeanRegistrationException e) {
            log.debug("Jmx deregistration failed.",e);
        } catch (InstanceNotFoundException e) {
            log.debug("Jmx deregistration failed.",e);
        } catch (MalformedObjectNameException e) {
            log.warn("Jmx deregistration failed.",e);
        } catch (RuntimeOperationsException e) {
            log.warn("Jmx deregistration failed.",e);
        }

    }


    public ObjectName getObjectName(Class<?> clazz, String poolName) throws MalformedObjectNameException {
        ObjectName oname;
        Map<String,InterceptorProperty> properties = getProperties();
        if (properties != null && properties.containsKey(objectNameAttribute)) {
            oname = new ObjectName(properties.get(objectNameAttribute).getValue());
        } else {
            oname = new ObjectName(ConnectionPool.POOL_JMX_TYPE_PREFIX+clazz.getName()+",name=" + poolName);
        }
        return oname;
    }

    protected void registerJmx() {
        try {
            //only if we notify the pool itself
            if (isNotifyPool()) {

            } else if (getCompositeType()!=null) {
                ObjectName oname = getObjectName(getClass(),poolName);
                if (mbeans.putIfAbsent(poolName, this)==null) {
                    ManagementFactory.getPlatformMBeanServer().registerMBean(this, oname);
                }
            } else {
                log.warn(SlowQueryReport.class.getName()+ "- No JMX support, composite type was not found.");
            }
        } catch (MalformedObjectNameException e) {
            log.error("Jmx registration failed, no JMX data will be exposed for the query stats.",e);
        } catch (RuntimeOperationsException e) {
            log.error("Jmx registration failed, no JMX data will be exposed for the query stats.",e);
        } catch (MBeanException e) {
            log.error("Jmx registration failed, no JMX data will be exposed for the query stats.",e);
        } catch (InstanceAlreadyExistsException e) {
            log.error("Jmx registration failed, no JMX data will be exposed for the query stats.",e);
        } catch (NotCompliantMBeanException e) {
            log.error("Jmx registration failed, no JMX data will be exposed for the query stats.",e);
        }
    }

    @Override
    public void setProperties(Map<String, InterceptorProperty> properties) {
        super.setProperties(properties);
        final String threshold = "notifyPool";
        InterceptorProperty p1 = properties.get(threshold);
        if (p1!=null) {
            this.setNotifyPool(Boolean.parseBoolean(p1.getValue()));
        }
    }


}
