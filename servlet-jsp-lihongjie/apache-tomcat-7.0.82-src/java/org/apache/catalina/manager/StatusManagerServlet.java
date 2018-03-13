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


package org.apache.catalina.manager;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.management.MBeanServer;
import javax.management.MBeanServerNotification;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.ServerInfo;
import org.apache.tomcat.util.modeler.Registry;
import org.apache.tomcat.util.res.StringManager;

/**
 * This servlet will display a complete status of the HTTP/1.1 connector.
 *
 * @author Remy Maucherat
 */
public class StatusManagerServlet
    extends HttpServlet implements NotificationListener {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------- Instance Variables
    /**
     * MBean server.
     */
    protected MBeanServer mBeanServer = null;


    /**
     * Vector of protocol handlers object names.
     */
    protected Vector<ObjectName> protocolHandlers = new Vector<ObjectName>();


    /**
     * Vector of thread pools object names.
     */
    protected Vector<ObjectName> threadPools = new Vector<ObjectName>();


    /**
     * Vector of request processors object names.
     */
    protected Vector<ObjectName> requestProcessors = new Vector<ObjectName>();


    /**
     * Vector of global request processors object names.
     */
    protected Vector<ObjectName> globalRequestProcessors = new Vector<ObjectName>();


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    // --------------------------------------------------------- Public Methods


    /**
     * Initialize this servlet.
     */
    @Override
    public void init() throws ServletException {

        // Retrieve the MBean server
        mBeanServer = Registry.getRegistry(null, null).getMBeanServer();

        try {

            // Query protocol handlers
            String onStr = "*:type=ProtocolHandler,*";
            ObjectName objectName = new ObjectName(onStr);
            Set<ObjectInstance> set = mBeanServer.queryMBeans(objectName, null);
            Iterator<ObjectInstance> iterator = set.iterator();
            while (iterator.hasNext()) {
                ObjectInstance oi = iterator.next();
                protocolHandlers.addElement(oi.getObjectName());
            }

            // Query Thread Pools
            onStr = "*:type=ThreadPool,*";
            objectName = new ObjectName(onStr);
            set = mBeanServer.queryMBeans(objectName, null);
            iterator = set.iterator();
            while (iterator.hasNext()) {
                ObjectInstance oi = iterator.next();
                threadPools.addElement(oi.getObjectName());
            }

            // Query Global Request Processors
            onStr = "*:type=GlobalRequestProcessor,*";
            objectName = new ObjectName(onStr);
            set = mBeanServer.queryMBeans(objectName, null);
            iterator = set.iterator();
            while (iterator.hasNext()) {
                ObjectInstance oi = iterator.next();
                globalRequestProcessors.addElement(oi.getObjectName());
            }

            // Query Request Processors
            onStr = "*:type=RequestProcessor,*";
            objectName = new ObjectName(onStr);
            set = mBeanServer.queryMBeans(objectName, null);
            iterator = set.iterator();
            while (iterator.hasNext()) {
                ObjectInstance oi = iterator.next();
                requestProcessors.addElement(oi.getObjectName());
            }

            // Register with MBean server
            onStr = "JMImplementation:type=MBeanServerDelegate";
            objectName = new ObjectName(onStr);
            mBeanServer.addNotificationListener(objectName, this, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Finalize this servlet.
     */
    @Override
    public void destroy() {

        // Unregister with MBean server
        String onStr = "JMImplementation:type=MBeanServerDelegate";
        ObjectName objectName;
        try {
            objectName = new ObjectName(onStr);
            mBeanServer.removeNotificationListener(objectName, this, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Process a GET request for the specified resource.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet-specified error occurs
     */
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException {

        // mode is flag for HTML or XML output
        int mode = 0;
        // if ?XML=true, set the mode to XML
        if (request.getParameter("XML") != null 
            && request.getParameter("XML").equals("true")) {
            mode = 1;
        }
        StatusTransformer.setContentType(response, mode);

        PrintWriter writer = response.getWriter();

        boolean completeStatus = false;
        if ((request.getPathInfo() != null) 
            && (request.getPathInfo().equals("/all"))) {
            completeStatus = true;
        }
        // use StatusTransformer to output status
        Object[] args = new Object[1];
        args[0] = request.getContextPath();
        StatusTransformer.writeHeader(writer,args,mode);

        // Body Header Section
        args = new Object[2];
        args[0] = request.getContextPath();
        if (completeStatus) {
            args[1] = sm.getString("statusServlet.complete");
        } else {
            args[1] = sm.getString("statusServlet.title");
        }
        // use StatusTransformer to output status
        StatusTransformer.writeBody(writer,args,mode);

        // Manager Section
        args = new Object[9];
        args[0] = sm.getString("htmlManagerServlet.manager");
        args[1] = response.encodeURL(request.getContextPath() + "/html/list");
        args[2] = sm.getString("htmlManagerServlet.list");
        args[3] = response.encodeURL
            (request.getContextPath() + "/" +
             sm.getString("htmlManagerServlet.helpHtmlManagerFile"));
        args[4] = sm.getString("htmlManagerServlet.helpHtmlManager");
        args[5] = response.encodeURL
            (request.getContextPath() + "/" +
             sm.getString("htmlManagerServlet.helpManagerFile"));
        args[6] = sm.getString("htmlManagerServlet.helpManager");
        if (completeStatus) {
            args[7] = response.encodeURL
                (request.getContextPath() + "/status");
            args[8] = sm.getString("statusServlet.title");
        } else {
            args[7] = response.encodeURL
                (request.getContextPath() + "/status/all");
            args[8] = sm.getString("statusServlet.complete");
        }
        // use StatusTransformer to output status
        StatusTransformer.writeManager(writer,args,mode);

        // Server Header Section
        args = new Object[9];
        args[0] = sm.getString("htmlManagerServlet.serverTitle");
        args[1] = sm.getString("htmlManagerServlet.serverVersion");
        args[2] = sm.getString("htmlManagerServlet.serverJVMVersion");
        args[3] = sm.getString("htmlManagerServlet.serverJVMVendor");
        args[4] = sm.getString("htmlManagerServlet.serverOSName");
        args[5] = sm.getString("htmlManagerServlet.serverOSVersion");
        args[6] = sm.getString("htmlManagerServlet.serverOSArch");
        args[7] = sm.getString("htmlManagerServlet.serverHostname");
        args[8] = sm.getString("htmlManagerServlet.serverIPAddress");
        // use StatusTransformer to output status
        StatusTransformer.writePageHeading(writer,args,mode);

        // Server Row Section
        args = new Object[8];
        args[0] = ServerInfo.getServerInfo();
        args[1] = System.getProperty("java.runtime.version");
        args[2] = System.getProperty("java.vm.vendor");
        args[3] = System.getProperty("os.name");
        args[4] = System.getProperty("os.version");
        args[5] = System.getProperty("os.arch");
        try {
            InetAddress address = InetAddress.getLocalHost();
            args[6] = address.getHostName();
            args[7] = address.getHostAddress();
         } catch (UnknownHostException e) {
            args[6] = "-";
            args[7] = "-";
        }
        // use StatusTransformer to output status
        StatusTransformer.writeServerInfo(writer, args, mode);

        try {

            // Display operating system statistics using APR if available
            StatusTransformer.writeOSState(writer,mode);

            // Display virtual machine statistics
            StatusTransformer.writeVMState(writer,mode);

            Enumeration<ObjectName> enumeration = threadPools.elements();
            while (enumeration.hasMoreElements()) {
                ObjectName objectName = enumeration.nextElement();
                String name = objectName.getKeyProperty("name");
                // use StatusTransformer to output status
                StatusTransformer.writeConnectorState
                    (writer, objectName,
                     name, mBeanServer, globalRequestProcessors,
                     requestProcessors, mode);
            }

            if ((request.getPathInfo() != null) 
                && (request.getPathInfo().equals("/all"))) {
                // Note: Retrieving the full status is much slower
                // use StatusTransformer to output status
                StatusTransformer.writeDetailedState
                    (writer, mBeanServer, mode);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

        // use StatusTransformer to output status
        StatusTransformer.writeFooter(writer, mode);

    }

    // ------------------------------------------- NotificationListener Methods


    @Override
    public void handleNotification(Notification notification,
                                   java.lang.Object handback) {

        if (notification instanceof MBeanServerNotification) {
            ObjectName objectName = 
                ((MBeanServerNotification) notification).getMBeanName();
            if (notification.getType().equals
                (MBeanServerNotification.REGISTRATION_NOTIFICATION)) {
                String type = objectName.getKeyProperty("type");
                if (type != null) {
                    if (type.equals("ProtocolHandler")) {
                        protocolHandlers.addElement(objectName);
                    } else if (type.equals("ThreadPool")) {
                        threadPools.addElement(objectName);
                    } else if (type.equals("GlobalRequestProcessor")) {
                        globalRequestProcessors.addElement(objectName);
                    } else if (type.equals("RequestProcessor")) {
                        requestProcessors.addElement(objectName);
                    }
                }
            } else if (notification.getType().equals
                       (MBeanServerNotification.UNREGISTRATION_NOTIFICATION)) {
                String type = objectName.getKeyProperty("type");
                if (type != null) {
                    if (type.equals("ProtocolHandler")) {
                        protocolHandlers.removeElement(objectName);
                    } else if (type.equals("ThreadPool")) {
                        threadPools.removeElement(objectName);
                    } else if (type.equals("GlobalRequestProcessor")) {
                        globalRequestProcessors.removeElement(objectName);
                    } else if (type.equals("RequestProcessor")) {
                        requestProcessors.removeElement(objectName);
                    }
                }
                String j2eeType = objectName.getKeyProperty("j2eeType");
                if (j2eeType != null) {
                    
                }
            }
        }

    }


}
