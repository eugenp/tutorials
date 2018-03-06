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

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.RequestUtil;
import org.apache.tomcat.util.ExceptionUtils;

/**
 * This is a refactoring of the servlet to externalize
 * the output into a simple class. Although we could
 * use XSLT, that is unnecessarily complex.
 *
 * @author Peter Lin
 */
public class StatusTransformer {


    // --------------------------------------------------------- Public Methods


    public static void setContentType(HttpServletResponse response, 
                                      int mode) {
        if (mode == 0){
            response.setContentType("text/html;charset="+Constants.CHARSET);
        } else if (mode == 1){
            response.setContentType("text/xml;charset="+Constants.CHARSET);
        }
    }


    /**
     * Write an HTML or XML header.
     *
     * @param writer the PrintWriter to use
     * @param args Path prefix for URLs
     * @param mode - 0 = HTML header, 1 = XML declaration
     *
     */
    public static void writeHeader(PrintWriter writer, Object[] args, int mode) {
        if (mode == 0){
            // HTML Header Section
            writer.print(Constants.HTML_HEADER_SECTION);
        } else if (mode == 1){
            writer.write(Constants.XML_DECLARATION);
            writer.print(MessageFormat.format
                     (Constants.XML_STYLE, args));
            writer.write("<status>");
        }
    }


    /**
     * Write the header body. XML output doesn't bother
     * to output this stuff, since it's just title.
     * 
     * @param writer The output writer
     * @param args What to write
     * @param mode 0 means write 
     */
    public static void writeBody(PrintWriter writer, Object[] args, int mode) {
        if (mode == 0){
            writer.print(MessageFormat.format
                         (Constants.BODY_HEADER_SECTION, args));
        }
    }


    /**
     * Write the manager webapp information.
     * 
     * @param writer The output writer
     * @param args What to write
     * @param mode 0 means write
     */
    public static void writeManager(PrintWriter writer, Object[] args, 
                                    int mode) {
        if (mode == 0){
            writer.print(MessageFormat.format(Constants.MANAGER_SECTION, args));
        }
    }


    public static void writePageHeading(PrintWriter writer, Object[] args, 
                                        int mode) {
        if (mode == 0){
            writer.print(MessageFormat.format
                         (Constants.SERVER_HEADER_SECTION, args));
        }
    }


    public static void writeServerInfo(PrintWriter writer, Object[] args, 
                                       int mode){
        if (mode == 0){
            writer.print(MessageFormat.format(Constants.SERVER_ROW_SECTION, args));
        }
    }


    /**
     * 
     */
    public static void writeFooter(PrintWriter writer, int mode) {
        if (mode == 0){
            // HTML Tail Section
            writer.print(Constants.HTML_TAIL_SECTION);
        } else if (mode == 1){
            writer.write("</status>");
        }
    }


    /**
     * Write the OS state. Mode 0 will generate HTML.
     * Mode 1 will generate XML.
     */
    public static void writeOSState(PrintWriter writer, int mode) {
        long[] result = new long[16];
        boolean ok = false;
        try {
            String methodName = "info";
            Class<?> paramTypes[] = new Class[1];
            paramTypes[0] = result.getClass();
            Object paramValues[] = new Object[1];
            paramValues[0] = result;
            Method method = Class.forName("org.apache.tomcat.jni.OS")
                .getMethod(methodName, paramTypes);
            method.invoke(null, paramValues);
            ok = true;
        } catch (Throwable t) {
            t = ExceptionUtils.unwrapInvocationTargetException(t);
            ExceptionUtils.handleThrowable(t);
        }
        
        if (ok) {
            if (mode == 0){
                writer.print("<h1>OS</h1>");

                writer.print("<p>");
                writer.print(" Physical memory: ");
                writer.print(formatSize(Long.valueOf(result[0]), true));
                writer.print(" Available memory: ");
                writer.print(formatSize(Long.valueOf(result[1]), true));
                writer.print(" Total page file: ");
                writer.print(formatSize(Long.valueOf(result[2]), true));
                writer.print(" Free page file: ");
                writer.print(formatSize(Long.valueOf(result[3]), true));
                writer.print(" Memory load: ");
                writer.print(Long.valueOf(result[6]));
                writer.print("<br>");
                writer.print(" Process kernel time: ");
                writer.print(formatTime(Long.valueOf(result[11] / 1000), true));
                writer.print(" Process user time: ");
                writer.print(formatTime(Long.valueOf(result[12] / 1000), true));
                writer.print("</p>");
            } else if (mode == 1){
                // NO-OP
            }
        }
        
    }
    
    
    /**
     * Write the VM state. Mode 0 will generate HTML.
     * Mode 1 will generate XML.
     */
    public static void writeVMState(PrintWriter writer, int mode)
        throws Exception {

        SortedMap<String, MemoryPoolMXBean> memoryPoolMBeans =
                new TreeMap<String, MemoryPoolMXBean>();
        for (MemoryPoolMXBean mbean: ManagementFactory.getMemoryPoolMXBeans()) {
            String sortKey = mbean.getType() + ":" + mbean.getName();
            memoryPoolMBeans.put(sortKey, mbean);
        }

        if (mode == 0){
            writer.print("<h1>JVM</h1>");

            writer.print("<p>");
            writer.print(" Free memory: ");
            writer.print(formatSize(
                    Long.valueOf(Runtime.getRuntime().freeMemory()), true));
            writer.print(" Total memory: ");
            writer.print(formatSize(
                    Long.valueOf(Runtime.getRuntime().totalMemory()), true));
            writer.print(" Max memory: ");
            writer.print(formatSize(
                    Long.valueOf(Runtime.getRuntime().maxMemory()), true));
            writer.print("</p>");

            writer.write("<table border=\"0\"><thead><tr><th>Memory Pool</th><th>Type</th><th>Initial</th><th>Total</th><th>Maximum</th><th>Used</th></tr></thead><tbody>");
            for (MemoryPoolMXBean memoryPoolMBean : memoryPoolMBeans.values()) {
                MemoryUsage usage = memoryPoolMBean.getUsage();
                writer.write("<tr><td>");
                writer.print(memoryPoolMBean.getName());
                writer.write("</td><td>");
                writer.print(memoryPoolMBean.getType());
                writer.write("</td><td>");
                writer.print(formatSize(Long.valueOf(usage.getInit()), true));
                writer.write("</td><td>");
                writer.print(formatSize(Long.valueOf(usage.getCommitted()), true));
                writer.write("</td><td>");
                writer.print(formatSize(Long.valueOf(usage.getMax()), true));
                writer.write("</td><td>");
                writer.print(formatSize(Long.valueOf(usage.getUsed()), true));
                if (usage.getMax() > 0) {
                    writer.write(" ("
                            + (usage.getUsed() * 100 / usage.getMax()) + "%)");
                }
                writer.write("</td></tr>");
            }
            writer.write("</tbody></table>");
        } else if (mode == 1){
            writer.write("<jvm>");

            writer.write("<memory");
            writer.write(" free='" + Runtime.getRuntime().freeMemory() + "'");
            writer.write(" total='" + Runtime.getRuntime().totalMemory() + "'");
            writer.write(" max='" + Runtime.getRuntime().maxMemory() + "'/>");

            for (MemoryPoolMXBean memoryPoolMBean : memoryPoolMBeans.values()) {
                MemoryUsage usage = memoryPoolMBean.getUsage();
                writer.write("<memorypool");
                writer.write(" name='" + memoryPoolMBean.getName() + "'");
                writer.write(" type='" + memoryPoolMBean.getType() + "'");
                writer.write(" usageInit='" + usage.getInit() + "'");
                writer.write(" usageCommitted='" + usage.getCommitted() + "'");
                writer.write(" usageMax='" + usage.getMax() + "'");
                writer.write(" usageUsed='" + usage.getUsed() + "'/>");
            }

            writer.write("</jvm>");
        }

    }


    /**
     * Write connector state.
     */
    public static void writeConnectorState(PrintWriter writer,
            ObjectName tpName, String name, MBeanServer mBeanServer,
            Vector<ObjectName> globalRequestProcessors,
            Vector<ObjectName> requestProcessors, int mode) throws Exception {

        if (mode == 0) {
            writer.print("<h1>");
            writer.print(name);
            writer.print("</h1>");

            writer.print("<p>");
            writer.print(" Max threads: ");
            writer.print(mBeanServer.getAttribute(tpName, "maxThreads"));
            writer.print(" Current thread count: ");
            writer.print(mBeanServer.getAttribute(tpName, "currentThreadCount"));
            writer.print(" Current thread busy: ");
            writer.print(mBeanServer.getAttribute(tpName, "currentThreadsBusy"));
            try {
                Object value = mBeanServer.getAttribute(tpName, "keepAliveCount");
                writer.print(" Keep alive sockets count: ");
                writer.print(value);
            } catch (Exception e) {
                // Ignore
            }
            
            writer.print("<br>");

            ObjectName grpName = null;

            Enumeration<ObjectName> enumeration =
                globalRequestProcessors.elements();
            while (enumeration.hasMoreElements()) {
                ObjectName objectName = enumeration.nextElement();
                if (name.equals(objectName.getKeyProperty("name"))) {
                    grpName = objectName;
                }
            }

            if (grpName == null) {
                return;
            }

            writer.print(" Max processing time: ");
            writer.print(formatTime(mBeanServer.getAttribute
                                    (grpName, "maxTime"), false));
            writer.print(" Processing time: ");
            writer.print(formatTime(mBeanServer.getAttribute
                                    (grpName, "processingTime"), true));
            writer.print(" Request count: ");
            writer.print(mBeanServer.getAttribute(grpName, "requestCount"));
            writer.print(" Error count: ");
            writer.print(mBeanServer.getAttribute(grpName, "errorCount"));
            writer.print(" Bytes received: ");
            writer.print(formatSize(mBeanServer.getAttribute
                                    (grpName, "bytesReceived"), true));
            writer.print(" Bytes sent: ");
            writer.print(formatSize(mBeanServer.getAttribute
                                    (grpName, "bytesSent"), true));
            writer.print("</p>");

            writer.print("<table border=\"0\"><tr><th>Stage</th><th>Time</th><th>B Sent</th><th>B Recv</th><th>Client (Forwarded)</th><th>Client (Actual)</th><th>VHost</th><th>Request</th></tr>");

            enumeration = requestProcessors.elements();
            while (enumeration.hasMoreElements()) {
                ObjectName objectName = enumeration.nextElement();
                if (name.equals(objectName.getKeyProperty("worker"))) {
                    writer.print("<tr>");
                    writeProcessorState(writer, objectName, mBeanServer, mode);
                    writer.print("</tr>");
                }
            }

            writer.print("</table>");

            writer.print("<p>");
            writer.print("P: Parse and prepare request S: Service F: Finishing R: Ready K: Keepalive");
            writer.print("</p>");
        } else if (mode == 1){
            writer.write("<connector name='" + name + "'>");

            writer.write("<threadInfo ");
            writer.write(" maxThreads=\"" + mBeanServer.getAttribute(tpName, "maxThreads") + "\"");
            writer.write(" currentThreadCount=\"" + mBeanServer.getAttribute(tpName, "currentThreadCount") + "\"");
            writer.write(" currentThreadsBusy=\"" + mBeanServer.getAttribute(tpName, "currentThreadsBusy") + "\"");
            writer.write(" />");

            ObjectName grpName = null;

            Enumeration<ObjectName> enumeration =
                globalRequestProcessors.elements();
            while (enumeration.hasMoreElements()) {
                ObjectName objectName = enumeration.nextElement();
                if (name.equals(objectName.getKeyProperty("name"))) {
                    grpName = objectName;
                }
            }

            if (grpName != null) {

                writer.write("<requestInfo ");
                writer.write(" maxTime=\"" + mBeanServer.getAttribute(grpName, "maxTime") + "\"");
                writer.write(" processingTime=\"" + mBeanServer.getAttribute(grpName, "processingTime") + "\"");
                writer.write(" requestCount=\"" + mBeanServer.getAttribute(grpName, "requestCount") + "\"");
                writer.write(" errorCount=\"" + mBeanServer.getAttribute(grpName, "errorCount") + "\"");
                writer.write(" bytesReceived=\"" + mBeanServer.getAttribute(grpName, "bytesReceived") + "\"");
                writer.write(" bytesSent=\"" + mBeanServer.getAttribute(grpName, "bytesSent") + "\"");
                writer.write(" />");

                writer.write("<workers>");
                enumeration = requestProcessors.elements();
                while (enumeration.hasMoreElements()) {
                    ObjectName objectName = enumeration.nextElement();
                    if (name.equals(objectName.getKeyProperty("worker"))) {
                        writeProcessorState(writer, objectName, mBeanServer, mode);
                    }
                }
                writer.write("</workers>");
            }

            writer.write("</connector>");
        }

    }


    /**
     * Write processor state.
     */
    protected static void writeProcessorState(PrintWriter writer, 
                                              ObjectName pName,
                                              MBeanServer mBeanServer, 
                                              int mode)
        throws Exception {

        Integer stageValue = 
            (Integer) mBeanServer.getAttribute(pName, "stage");
        int stage = stageValue.intValue();
        boolean fullStatus = true;
        boolean showRequest = true;
        String stageStr = null;

        switch (stage) {

        case (1/*org.apache.coyote.Constants.STAGE_PARSE*/):
            stageStr = "P";
            fullStatus = false;
            break;
        case (2/*org.apache.coyote.Constants.STAGE_PREPARE*/):
            stageStr = "P";
            fullStatus = false;
            break;
        case (3/*org.apache.coyote.Constants.STAGE_SERVICE*/):
            stageStr = "S";
            break;
        case (4/*org.apache.coyote.Constants.STAGE_ENDINPUT*/):
            stageStr = "F";
            break;
        case (5/*org.apache.coyote.Constants.STAGE_ENDOUTPUT*/):
            stageStr = "F";
            break;
        case (7/*org.apache.coyote.Constants.STAGE_ENDED*/):
            stageStr = "R";
            fullStatus = false;
            break;
        case (6/*org.apache.coyote.Constants.STAGE_KEEPALIVE*/):
            stageStr = "K";
            fullStatus = true;
            showRequest = false;
            break;
        case (0/*org.apache.coyote.Constants.STAGE_NEW*/):
            stageStr = "R";
            fullStatus = false;
            break;
        default:
            // Unknown stage
            stageStr = "?";
            fullStatus = false;

        }

        if (mode == 0) {
            writer.write("<td><strong>");
            writer.write(stageStr);
            writer.write("</strong></td>");

            if (fullStatus) {
                writer.write("<td>");
                writer.print(formatTime(mBeanServer.getAttribute
                                        (pName, "requestProcessingTime"), false));
                writer.write("</td>");
                writer.write("<td>");
                if (showRequest) {
                    writer.print(formatSize(mBeanServer.getAttribute
                                            (pName, "requestBytesSent"), false));
                } else {
                    writer.write("?");
                }
                writer.write("</td>");
                writer.write("<td>");
                if (showRequest) {
                    writer.print(formatSize(mBeanServer.getAttribute
                                            (pName, "requestBytesReceived"), 
                                            false));
                } else {
                    writer.write("?");
                }
                writer.write("</td>");
                writer.write("<td>");
                writer.print(filter(mBeanServer.getAttribute
                                    (pName, "remoteAddrForwarded")));
                writer.write("</td>");
                writer.write("<td>");
                writer.print(filter(mBeanServer.getAttribute
                                    (pName, "remoteAddr")));
                writer.write("</td>");
                writer.write("<td nowrap>");
                writer.write(filter(mBeanServer.getAttribute
                                    (pName, "virtualHost")));
                writer.write("</td>");
                writer.write("<td nowrap class=\"row-left\">");
                if (showRequest) {
                    writer.write(filter(mBeanServer.getAttribute
                                        (pName, "method")));
                    writer.write(" ");
                    writer.write(filter(mBeanServer.getAttribute
                                        (pName, "currentUri")));
                    String queryString = (String) mBeanServer.getAttribute
                        (pName, "currentQueryString");
                    if ((queryString != null) && (!queryString.equals(""))) {
                        writer.write("?");
                        writer.print(RequestUtil.filter(queryString));
                    }
                    writer.write(" ");
                    writer.write(filter(mBeanServer.getAttribute
                                        (pName, "protocol")));
                } else {
                    writer.write("?");
                }
                writer.write("</td>");
            } else {
                writer.write("<td>?</td><td>?</td><td>?</td><td>?</td><td>?</td><td>?</td>");
            }
        } else if (mode == 1){
            writer.write("<worker ");
            writer.write(" stage=\"" + stageStr + "\"");

            if (fullStatus) {
                writer.write(" requestProcessingTime=\"" 
                             + mBeanServer.getAttribute
                             (pName, "requestProcessingTime") + "\"");
                writer.write(" requestBytesSent=\"");
                if (showRequest) {
                    writer.write("" + mBeanServer.getAttribute
                                 (pName, "requestBytesSent"));
                } else {
                    writer.write("0");
                }
                writer.write("\"");
                writer.write(" requestBytesReceived=\"");
                if (showRequest) {
                    writer.write("" + mBeanServer.getAttribute
                                 (pName, "requestBytesReceived"));
                } else {
                    writer.write("0");
                }
                writer.write("\"");
                writer.write(" remoteAddr=\"" 
                             + filter(mBeanServer.getAttribute
                                      (pName, "remoteAddr")) + "\"");
                writer.write(" virtualHost=\"" 
                             + filter(mBeanServer.getAttribute
                                      (pName, "virtualHost")) + "\"");

                if (showRequest) {
                    writer.write(" method=\"" 
                                 + filter(mBeanServer.getAttribute
                                          (pName, "method")) + "\"");
                    writer.write(" currentUri=\"" 
                                 + filter(mBeanServer.getAttribute
                                          (pName, "currentUri")) + "\"");

                    String queryString = (String) mBeanServer.getAttribute
                        (pName, "currentQueryString");
                    if ((queryString != null) && (!queryString.equals(""))) {
                        writer.write(" currentQueryString=\"" 
                                     + RequestUtil.filter(queryString) + "\"");
                    } else {
                        writer.write(" currentQueryString=\"&#63;\"");
                    }
                    writer.write(" protocol=\"" 
                                 + filter(mBeanServer.getAttribute
                                          (pName, "protocol")) + "\"");
                } else {
                    writer.write(" method=\"&#63;\"");
                    writer.write(" currentUri=\"&#63;\"");
                    writer.write(" currentQueryString=\"&#63;\"");
                    writer.write(" protocol=\"&#63;\"");
                }
            } else {
                writer.write(" requestProcessingTime=\"0\"");
                writer.write(" requestBytesSent=\"0\"");
                writer.write(" requestBytesReceived=\"0\"");
                writer.write(" remoteAddr=\"&#63;\"");
                writer.write(" virtualHost=\"&#63;\"");
                writer.write(" method=\"&#63;\"");
                writer.write(" currentUri=\"&#63;\"");
                writer.write(" currentQueryString=\"&#63;\"");
                writer.write(" protocol=\"&#63;\"");
            }
            writer.write(" />");
        }

    }


    /**
     * Write applications state.
     */
    public static void writeDetailedState(PrintWriter writer,
                                          MBeanServer mBeanServer, int mode)
        throws Exception {

        if (mode == 0){
            ObjectName queryHosts = new ObjectName("*:j2eeType=WebModule,*");
            Set<ObjectName> hostsON = mBeanServer.queryNames(queryHosts, null);

            // Navigation menu
            writer.print("<h1>");
            writer.print("Application list");
            writer.print("</h1>");

            writer.print("<p>");
            int count = 0;
            Iterator<ObjectName> iterator = hostsON.iterator();
            while (iterator.hasNext()) {
                ObjectName contextON = iterator.next();
                String webModuleName = contextON.getKeyProperty("name");
                if (webModuleName.startsWith("//")) {
                    webModuleName = webModuleName.substring(2);
                }
                int slash = webModuleName.indexOf('/');
                if (slash == -1) {
                    count++;
                    continue;
                }

                writer.print("<a href=\"#" + (count++) + ".0\">");
                writer.print(filter(webModuleName));
                writer.print("</a>");
                if (iterator.hasNext()) {
                    writer.print("<br>");
                }

            }
            writer.print("</p>");

            // Webapp list
            count = 0;
            iterator = hostsON.iterator();
            while (iterator.hasNext()) {
                ObjectName contextON = iterator.next();
                writer.print("<a class=\"A.name\" name=\"" 
                             + (count++) + ".0\">");
                writeContext(writer, contextON, mBeanServer, mode);
            }

        } else if (mode == 1){
            // for now we don't write out the Detailed state in XML
        }

    }


    /**
     * Write context state.
     */
    protected static void writeContext(PrintWriter writer, 
                                       ObjectName objectName,
                                       MBeanServer mBeanServer, int mode)
        throws Exception {

        if (mode == 0){
            String webModuleName = objectName.getKeyProperty("name");
            String name = webModuleName;
            if (name == null) {
                return;
            }
            
            String hostName = null;
            String contextName = null;
            if (name.startsWith("//")) {
                name = name.substring(2);
            }
            int slash = name.indexOf('/');
            if (slash != -1) {
                hostName = name.substring(0, slash);
                contextName = name.substring(slash);
            } else {
                return;
            }

            ObjectName queryManager = new ObjectName
                (objectName.getDomain() + ":type=Manager,context=" + contextName 
                 + ",host=" + hostName + ",*");
            Set<ObjectName> managersON =
                mBeanServer.queryNames(queryManager, null);
            ObjectName managerON = null;
            Iterator<ObjectName> iterator2 = managersON.iterator();
            while (iterator2.hasNext()) {
                managerON = iterator2.next();
            }

            ObjectName queryJspMonitor = new ObjectName
                (objectName.getDomain() + ":type=JspMonitor,WebModule=" +
                 webModuleName + ",*");
            Set<ObjectName> jspMonitorONs =
                mBeanServer.queryNames(queryJspMonitor, null);

            // Special case for the root context
            if (contextName.equals("/")) {
                contextName = "";
            }

            writer.print("<h1>");
            writer.print(filter(name));
            writer.print("</h1>");
            writer.print("</a>");

            writer.print("<p>");
            Object startTime = mBeanServer.getAttribute(objectName,
                                                        "startTime");
            writer.print(" Start time: " +
                         new Date(((Long) startTime).longValue()));
            writer.print(" Startup time: ");
            writer.print(formatTime(mBeanServer.getAttribute
                                    (objectName, "startupTime"), false));
            writer.print(" TLD scan time: ");
            writer.print(formatTime(mBeanServer.getAttribute
                                    (objectName, "tldScanTime"), false));
            if (managerON != null) {
                writeManager(writer, managerON, mBeanServer, mode);
            }
            if (jspMonitorONs != null) {
                writeJspMonitor(writer, jspMonitorONs, mBeanServer, mode);
            }
            writer.print("</p>");

            String onStr = objectName.getDomain() 
                + ":j2eeType=Servlet,WebModule=" + webModuleName + ",*";
            ObjectName servletObjectName = new ObjectName(onStr);
            Set<ObjectInstance> set =
                mBeanServer.queryMBeans(servletObjectName, null);
            Iterator<ObjectInstance> iterator = set.iterator();
            while (iterator.hasNext()) {
                ObjectInstance oi = iterator.next();
                writeWrapper(writer, oi.getObjectName(), mBeanServer, mode);
            }

        } else if (mode == 1){
            // for now we don't write out the context in XML
        }

    }


    /**
     * Write detailed information about a manager.
     */
    public static void writeManager(PrintWriter writer, ObjectName objectName,
                                    MBeanServer mBeanServer, int mode)
        throws Exception {

        if (mode == 0) {
            writer.print("<br>");
            writer.print(" Active sessions: ");
            writer.print(mBeanServer.getAttribute
                         (objectName, "activeSessions"));
            writer.print(" Session count: ");
            writer.print(mBeanServer.getAttribute
                         (objectName, "sessionCounter"));
            writer.print(" Max active sessions: ");
            writer.print(mBeanServer.getAttribute(objectName, "maxActive"));
            writer.print(" Rejected session creations: ");
            writer.print(mBeanServer.getAttribute
                         (objectName, "rejectedSessions"));
            writer.print(" Expired sessions: ");
            writer.print(mBeanServer.getAttribute
                         (objectName, "expiredSessions"));
            writer.print(" Longest session alive time: ");
            writer.print(formatSeconds(mBeanServer.getAttribute(
                                                    objectName,
                                                    "sessionMaxAliveTime")));
            writer.print(" Average session alive time: ");
            writer.print(formatSeconds(mBeanServer.getAttribute(
                                                    objectName,
                                                    "sessionAverageAliveTime")));
            writer.print(" Processing time: ");
            writer.print(formatTime(mBeanServer.getAttribute
                                    (objectName, "processingTime"), false));
        } else if (mode == 1) {
            // for now we don't write out the wrapper details
        }

    }


    /**
     * Write JSP monitoring information.
     */
    public static void writeJspMonitor(PrintWriter writer,
                                       Set<ObjectName> jspMonitorONs,
                                       MBeanServer mBeanServer,
                                       int mode)
            throws Exception {

        int jspCount = 0;
        int jspReloadCount = 0;

        Iterator<ObjectName> iter = jspMonitorONs.iterator();
        while (iter.hasNext()) {
            ObjectName jspMonitorON = iter.next();
            Object obj = mBeanServer.getAttribute(jspMonitorON, "jspCount");
            jspCount += ((Integer) obj).intValue();
            obj = mBeanServer.getAttribute(jspMonitorON, "jspReloadCount");
            jspReloadCount += ((Integer) obj).intValue();
        }

        if (mode == 0) {
            writer.print("<br>");
            writer.print(" JSPs loaded: ");
            writer.print(jspCount);
            writer.print(" JSPs reloaded: ");
            writer.print(jspReloadCount);
        } else if (mode == 1) {
            // for now we don't write out anything
        }
    }


    /**
     * Write detailed information about a wrapper.
     */
    public static void writeWrapper(PrintWriter writer, ObjectName objectName,
                                    MBeanServer mBeanServer, int mode)
        throws Exception {

        if (mode == 0) {
            String servletName = objectName.getKeyProperty("name");
            
            String[] mappings = (String[]) 
                mBeanServer.invoke(objectName, "findMappings", null, null);
            
            writer.print("<h2>");
            writer.print(filter(servletName));
            if ((mappings != null) && (mappings.length > 0)) {
                writer.print(" [ ");
                for (int i = 0; i < mappings.length; i++) {
                    writer.print(filter(mappings[i]));
                    if (i < mappings.length - 1) {
                        writer.print(" , ");
                    }
                }
                writer.print(" ] ");
            }
            writer.print("</h2>");
            
            writer.print("<p>");
            writer.print(" Processing time: ");
            writer.print(formatTime(mBeanServer.getAttribute
                                    (objectName, "processingTime"), true));
            writer.print(" Max time: ");
            writer.print(formatTime(mBeanServer.getAttribute
                                    (objectName, "maxTime"), false));
            writer.print(" Request count: ");
            writer.print(mBeanServer.getAttribute(objectName, "requestCount"));
            writer.print(" Error count: ");
            writer.print(mBeanServer.getAttribute(objectName, "errorCount"));
            writer.print(" Load time: ");
            writer.print(formatTime(mBeanServer.getAttribute
                                    (objectName, "loadTime"), false));
            writer.print(" Classloading time: ");
            writer.print(formatTime(mBeanServer.getAttribute
                                    (objectName, "classLoadTime"), false));
            writer.print("</p>");
        } else if (mode == 1){
            // for now we don't write out the wrapper details
        }

    }


    /**
     * Filter the specified message string for characters that are sensitive
     * in HTML.  This avoids potential attacks caused by including JavaScript
     * codes in the request URL that is often reported in error messages.
     *
     * @param obj The message string to be filtered
     */
    public static String filter(Object obj) {

        if (obj == null)
            return ("?");
        String message = obj.toString();

        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuilder result = new StringBuilder(content.length + 50);
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
            case '<':
                result.append("&lt;");
                break;
            case '>':
                result.append("&gt;");
                break;
            case '&':
                result.append("&amp;");
                break;
            case '"':
                result.append("&quot;");
                break;
            default:
                result.append(content[i]);
            }
        }
        return (result.toString());

    }


    /**
     * Display the given size in bytes, either as KB or MB.
     *
     * @param mb true to display megabytes, false for kilobytes
     */
    public static String formatSize(Object obj, boolean mb) {

        long bytes = -1L;

        if (obj instanceof Long) {
            bytes = ((Long) obj).longValue();
        } else if (obj instanceof Integer) {
            bytes = ((Integer) obj).intValue();
        }

        if (mb) {
            StringBuilder buff = new StringBuilder();
            if (bytes < 0) {
                buff.append('-');
                bytes = -bytes;
            }
            long mbytes = bytes / (1024 * 1024);
            long rest = 
                ((bytes - (mbytes * (1024 * 1024))) * 100) / (1024 * 1024);
            buff.append(mbytes).append('.');
            if (rest < 10) {
                buff.append('0');
            }
            buff.append(rest).append(" MB");
            return buff.toString();
        } else {
            return ((bytes / 1024) + " KB");
        }

    }


    /**
     * Display the given time in ms, either as ms or s.
     *
     * @param seconds true to display seconds, false for milliseconds
     */
    public static String formatTime(Object obj, boolean seconds) {

        long time = -1L;

        if (obj instanceof Long) {
            time = ((Long) obj).longValue();
        } else if (obj instanceof Integer) {
            time = ((Integer) obj).intValue();
        }

        if (seconds) {
            return ((((float) time ) / 1000) + " s");
        } else {
            return (time + " ms");
        }
    }


    /**
     * Formats the given time (given in seconds) as a string.
     *
     * @param obj Time object to be formatted as string
     *
     * @return String formatted time
     */
    public static String formatSeconds(Object obj) {

        long time = -1L;

        if (obj instanceof Long) {
            time = ((Long) obj).longValue();
        } else if (obj instanceof Integer) {
            time = ((Integer) obj).intValue();
        }

        return (time + " s");
    }

}
