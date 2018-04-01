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
package org.apache.catalina.ha.jmx;

import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.ha.deploy.FarmWarDeployer;
import org.apache.catalina.ha.tcp.SimpleTcpCluster;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.modeler.ManagedBean;
import org.apache.tomcat.util.modeler.Registry;
/**
 * 
 * @author Filip Hanik
 *
 * @deprecated  Unused - registration now happens via
 *              {@link org.apache.catalina.util.LifecycleMBeanBase}
 */
@Deprecated
public class ClusterJmxHelper {
    
    protected static Registry registry = Registry.getRegistry(null,null);
    
    private static final Log log = LogFactory.getLog(ClusterJmxHelper.class);
    
    protected static boolean jmxEnabled = true;
    
    protected static MBeanServer mbeanServer = null;
    
    public static Registry getRegistry() {
        return registry;
    }

    public static MBeanServer getMBeanServer() throws Exception {
        if (mbeanServer == null) {
            if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
                mbeanServer = MBeanServerFactory.findMBeanServer(null).get(0);
            } else {
                mbeanServer = MBeanServerFactory.createMBeanServer();
            }
        }
        return mbeanServer;
    }
    
    protected static boolean initMetaData(Class<?> clazz) {
        try {
            if (clazz==null) return false;
            getRegistry().loadMetadata(clazz.getResourceAsStream("mbeans-descriptors.xml"));
        }catch (Exception x) {
            log.warn("Unable to load meta data for class:"+clazz.getName());
            return false;
        }
        return true;
    }
    
    public static DynamicMBean getManagedBean(Object object) throws Exception {
        DynamicMBean mbean = null;
        if (getRegistry() != null) {
            ManagedBean managedBean = registry.findManagedBean(object.getClass().getName());
            mbean = managedBean.createMBean(object);
        }
        return mbean;
    }

    
    protected static void initDefaultCluster() {
        initMetaData(SimpleTcpCluster.class);
        initMetaData(FarmWarDeployer.class); //not functional yet
    }
    
    public static boolean registerDefaultCluster(SimpleTcpCluster cluster)  {
        try {
            initDefaultCluster();
            ObjectName clusterName = getDefaultClusterName(cluster);
            if (!getMBeanServer().isRegistered(clusterName)) {
                getMBeanServer().registerMBean(getManagedBean(cluster), clusterName);
            }
            return true;
        }catch ( Exception x ) {
            log.warn("Unable to register default cluster implementation with JMX",x);
            return false;
        }
    }

    public static boolean unregisterDefaultCluster(SimpleTcpCluster cluster) {
        try {
            ObjectName clusterName = getDefaultClusterName(cluster);
            if (getMBeanServer().isRegistered(clusterName)) {
                getMBeanServer().unregisterMBean(clusterName);
            }
            return true;
        }catch ( Exception x ) {
            log.warn("Unable to unregister default cluster implementation with JMX",x);
            return false;
        }
    }

    private static ObjectName getDefaultClusterName(SimpleTcpCluster cluster) throws Exception {
        String domain = getMBeanServer().getDefaultDomain();
        String type = ":type=";
        String clusterType= type+"Cluster";
        if (cluster.getContainer() instanceof StandardHost) {
            domain = ((StandardHost) cluster.getContainer()).getDomain();
            clusterType += ",host=" + cluster.getContainer().getName();
        } else {
            if (cluster.getContainer() instanceof StandardEngine) {
                domain = ((StandardEngine) cluster.getContainer()).getDomain();
            }
        }
        ObjectName clusterName = new ObjectName(domain + clusterType);
        return clusterName;
    }
    
}