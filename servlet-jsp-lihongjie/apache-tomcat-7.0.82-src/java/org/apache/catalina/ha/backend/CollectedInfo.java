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


package org.apache.catalina.ha.backend;

/* for MBean to read ready and busy */

import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.apache.tomcat.util.modeler.Registry;

/*
 * Listener to provider informations to mod_heartbeat.c
 * *msg_format = "v=%u&ready=%u&busy=%u"; (message to send).
 * send the multicast message using the format...
 * what about the bind(IP. port) only IP makes sense (for the moment).
 * BTW:v  = version :-)
 */
public class CollectedInfo {

    /* Collect info via JMX */
    protected MBeanServer mBeanServer = null;
    protected ObjectName objName = null;

    int ready;
    int busy;

    int port = 0;
    String host = null;

    public CollectedInfo(String host, int port) throws Exception {
        init(host, port);
    }
    public void init(String host, int port) throws Exception {
        int iport = 0;
        String shost = null;
        mBeanServer = Registry.getRegistry(null, null).getMBeanServer();
        String onStr = "*:type=ThreadPool,*";
        ObjectName objectName = new ObjectName(onStr);
        Set<ObjectInstance> set = mBeanServer.queryMBeans(objectName, null);
        Iterator<ObjectInstance> iterator = set.iterator();
        while (iterator.hasNext()) {
            ObjectInstance oi = iterator.next();
            objName = oi.getObjectName();
            String name = objName.getKeyProperty("name");
              
            /* Name are:
             * http-8080
             * jk-10.33.144.3-8009
             * jk-jfcpc%2F10.33.144.3-8009
             */
            String [] elenames = name.split("-");
            String sport = elenames[elenames.length-1];
            iport = Integer.parseInt(sport);
            String [] shosts = elenames[1].split("%2F");
            shost = shosts[0];

            if (port==0 && host==null)
                  break; /* Take the first one */
            if (host==null && iport==port)
                break; /* Only port done */
            if (shost.compareTo(host) == 0)
                break; /* Done port and host are the expected ones */
        }
        if (objName == null)
            throw(new Exception("Can't find connector for " + host + ":" + port));
        this.port = iport;
        this.host = shost;
        
    }

    public void refresh() throws Exception {
        if (mBeanServer == null || objName == null) {
            throw(new Exception("Not initialized!!!"));
        }
        Integer imax = (Integer) mBeanServer.getAttribute(objName, "maxThreads");

        // the currentThreadCount could be 0 before the threads are created...
        // Integer iready = (Integer) mBeanServer.getAttribute(objName, "currentThreadCount");

        Integer ibusy  = (Integer) mBeanServer.getAttribute(objName, "currentThreadsBusy");

        busy = ibusy.intValue();
        ready = imax.intValue() - ibusy.intValue();
    }
}
