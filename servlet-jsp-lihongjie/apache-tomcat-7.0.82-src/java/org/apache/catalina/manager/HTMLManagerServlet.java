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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.DistributedManager;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.manager.util.BaseSessionComparator;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.catalina.util.ContextName;
import org.apache.catalina.util.RequestUtil;
import org.apache.catalina.util.ServerInfo;
import org.apache.catalina.util.URLEncoder;
import org.apache.tomcat.util.http.fileupload.ParameterParser;
import org.apache.tomcat.util.res.StringManager;

/**
* Servlet that enables remote management of the web applications deployed
* within the same virtual host as this web application is.  Normally, this
* functionality will be protected by a security constraint in the web
* application deployment descriptor.  However, this requirement can be
* relaxed during testing.
* <p>
* The difference between the <code>ManagerServlet</code> and this
* Servlet is that this Servlet prints out a HTML interface which
* makes it easier to administrate.
* <p>
* However if you use a software that parses the output of
* <code>ManagerServlet</code> you won't be able to upgrade
* to this Servlet since the output are not in the
* same format ar from <code>ManagerServlet</code>
*
* @author Bip Thelin
* @author Malcolm Edgar
* @author Glenn L. Nielsen
* @see ManagerServlet
*/
public final class HTMLManagerServlet extends ManagerServlet {

    private static final long serialVersionUID = 1L;

    static final String APPLICATION_MESSAGE = "message";
    static final String APPLICATION_ERROR = "error";

    static final String sessionsListJspPath  = "/WEB-INF/jsp/sessionsList.jsp";
    static final String sessionDetailJspPath = "/WEB-INF/jsp/sessionDetail.jsp";

    private boolean showProxySessions = false;

    // --------------------------------------------------------- Public Methods

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

        StringManager smClient = StringManager.getManager(
                Constants.Package, request.getLocales());

        // Identify the request parameters that we need
        // By obtaining the command from the pathInfo, per-command security can
        // be configured in web.xml
        String command = request.getPathInfo();

        String path = request.getParameter("path");
        ContextName cn = null;
        if (path != null) {
            cn = new ContextName(path, request.getParameter("version"));
        }

        // Prepare our output writer to generate the response message
        response.setContentType("text/html; charset=" + Constants.CHARSET);

        String message = "";
        // Process the requested command
        if (command == null || command.equals("/")) {
            // No command == list
        } else if (command.equals("/list")) {
            // List always displayed - nothing to do here
        } else if (command.equals("/sessions")) {
            try {
                doSessions(cn, request, response, smClient);
                return;
            } catch (Exception e) {
                log("HTMLManagerServlet.sessions[" + cn + "]", e);
                message = smClient.getString("managerServlet.exception",
                        e.toString());
            }
        } else if (command.equals("/upload") || command.equals("/deploy") ||
                command.equals("/reload") || command.equals("/undeploy") ||
                command.equals("/expire") || command.equals("/start") ||
                command.equals("/stop")) {
            message =
                smClient.getString("managerServlet.postCommand", command);
        } else {
            message =
                smClient.getString("managerServlet.unknownCommand", command);
        }

        list(request, response, message, smClient);
    }

    /**
     * Process a POST request for the specified resource.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet-specified error occurs
     */
    @Override
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException {

        StringManager smClient = StringManager.getManager(
                Constants.Package, request.getLocales());

        // Identify the request parameters that we need
        // By obtaining the command from the pathInfo, per-command security can
        // be configured in web.xml
        String command = request.getPathInfo();

        String path = request.getParameter("path");
        ContextName cn = null;
        if (path != null) {
            cn = new ContextName(path, request.getParameter("version"));
        }
        String deployPath = request.getParameter("deployPath");
        ContextName deployCn = null;
        if (deployPath != null) {
            deployCn = new ContextName(deployPath,
                    request.getParameter("deployVersion"));
        }
        String deployConfig = request.getParameter("deployConfig");
        String deployWar = request.getParameter("deployWar");

        // Prepare our output writer to generate the response message
        response.setContentType("text/html; charset=" + Constants.CHARSET);

        String message = "";

        if (command == null || command.length() == 0) {
            // No command == list
            // List always displayed -> do nothing
        } else if (command.equals("/upload")) {
            message = upload(request, smClient);
        } else if (command.equals("/deploy")) {
            message = deployInternal(deployConfig, deployCn, deployWar,
                    smClient);
        } else if (command.equals("/reload")) {
            message = reload(cn, smClient);
        } else if (command.equals("/undeploy")) {
            message = undeploy(cn, smClient);
        } else if (command.equals("/expire")) {
            message = expireSessions(cn, request, smClient);
        } else if (command.equals("/start")) {
            message = start(cn, smClient);
        } else if (command.equals("/stop")) {
            message = stop(cn, smClient);
        } else if (command.equals("/findleaks")) {
            message = findleaks(smClient);
        } else {
            // Try GET
            doGet(request,response);
            return;
        }

        list(request, response, message, smClient);
    }

    protected String upload(HttpServletRequest request, StringManager smClient) {
        String message = "";

        try {
            while (true) {
                Part warPart = request.getPart("deployWar");
                if (warPart == null) {
                    message = smClient.getString(
                            "htmlManagerServlet.deployUploadNoFile");
                    break;
                }
                String filename =
                    extractFilename(warPart.getHeader("Content-Disposition"));
                if (!filename.toLowerCase(Locale.ENGLISH).endsWith(".war")) {
                    message = smClient.getString(
                            "htmlManagerServlet.deployUploadNotWar", filename);
                    break;
                }
                // Get the filename if uploaded name includes a path
                if (filename.lastIndexOf('\\') >= 0) {
                    filename =
                        filename.substring(filename.lastIndexOf('\\') + 1);
                }
                if (filename.lastIndexOf('/') >= 0) {
                    filename =
                        filename.substring(filename.lastIndexOf('/') + 1);
                }

                // Identify the appBase of the owning Host of this Context
                // (if any)
                File file = new File(deployed, filename);
                if (file.exists()) {
                    message = smClient.getString(
                            "htmlManagerServlet.deployUploadWarExists",
                            filename);
                    break;
                }

                ContextName cn = new ContextName(filename, true);
                String name = cn.getName();

                if ((host.findChild(name) != null) && !isDeployed(name)) {
                    message = smClient.getString(
                            "htmlManagerServlet.deployUploadInServerXml",
                            filename);
                    break;
                }

                if (isServiced(name)) {
                    message = smClient.getString("managerServlet.inService", name);
                } else {
                    addServiced(name);
                    try {
                        warPart.write(file.getAbsolutePath());
                        // Perform new deployment
                        check(name);
                    } finally {
                        removeServiced(name);
                    }
                }
                break;
            }
        } catch(Exception e) {
            message = smClient.getString
                ("htmlManagerServlet.deployUploadFail", e.getMessage());
            log(message, e);
        }
        return message;
    }

    /*
     * Adapted from FileUploadBase.getFileName()
     */
    private String extractFilename(String cd) {
        String fileName = null;
        if (cd != null) {
            String cdl = cd.toLowerCase(Locale.ENGLISH);
            if (cdl.startsWith("form-data") || cdl.startsWith("attachment")) {
                ParameterParser parser = new ParameterParser();
                parser.setLowerCaseNames(true);
                // Parameter parser can handle null input
                Map<String,String> params =
                    parser.parse(cd, ';');
                if (params.containsKey("filename")) {
                    fileName = params.get("filename");
                    if (fileName != null) {
                        fileName = fileName.trim();
                    } else {
                        // Even if there is no value, the parameter is present,
                        // so we return an empty file name rather than no file
                        // name.
                        fileName = "";
                    }
                }
            }
        }
        return fileName;
    }

    /**
     * Deploy an application for the specified path from the specified
     * web application archive.
     *
     * @param config URL of the context configuration file to be deployed
     * @param cn Name of the application to be deployed
     * @param war URL of the web application archive to be deployed
     * @return message String
     */
    protected String deployInternal(String config, ContextName cn, String war,
            StringManager smClient) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        super.deploy(printWriter, config, cn, war, false, smClient);

        return stringWriter.toString();
    }

    /**
     * Render a HTML list of the currently active Contexts in our virtual host,
     * and memory and server status information.
     *
     * @param request The request
     * @param response The response
     * @param message a message to display
     */
    protected void list(HttpServletRequest request,
                     HttpServletResponse response,
                     String message,
                     StringManager smClient) throws IOException {

        if (debug >= 1)
            log("list: Listing contexts for virtual host '" +
                host.getName() + "'");

        PrintWriter writer = response.getWriter();

        // HTML Header Section
        writer.print(Constants.HTML_HEADER_SECTION);

        // Body Header Section
        Object[] args = new Object[2];
        args[0] = request.getContextPath();
        args[1] = smClient.getString("htmlManagerServlet.title");
        writer.print(MessageFormat.format
                     (Constants.BODY_HEADER_SECTION, args));

        // Message Section
        args = new Object[3];
        args[0] = smClient.getString("htmlManagerServlet.messageLabel");
        if (message == null || message.length() == 0) {
            args[1] = "OK";
        } else {
            args[1] = RequestUtil.filter(message);
        }
        writer.print(MessageFormat.format(Constants.MESSAGE_SECTION, args));

        // Manager Section
        args = new Object[9];
        args[0] = smClient.getString("htmlManagerServlet.manager");
        args[1] = response.encodeURL(request.getContextPath() + "/html/list");
        args[2] = smClient.getString("htmlManagerServlet.list");
        args[3] = response.encodeURL
            (request.getContextPath() + "/" +
             smClient.getString("htmlManagerServlet.helpHtmlManagerFile"));
        args[4] = smClient.getString("htmlManagerServlet.helpHtmlManager");
        args[5] = response.encodeURL
            (request.getContextPath() + "/" +
             smClient.getString("htmlManagerServlet.helpManagerFile"));
        args[6] = smClient.getString("htmlManagerServlet.helpManager");
        args[7] = response.encodeURL
            (request.getContextPath() + "/status");
        args[8] = smClient.getString("statusServlet.title");
        writer.print(MessageFormat.format(Constants.MANAGER_SECTION, args));

        // Apps Header Section
        args = new Object[7];
        args[0] = smClient.getString("htmlManagerServlet.appsTitle");
        args[1] = smClient.getString("htmlManagerServlet.appsPath");
        args[2] = smClient.getString("htmlManagerServlet.appsVersion");
        args[3] = smClient.getString("htmlManagerServlet.appsName");
        args[4] = smClient.getString("htmlManagerServlet.appsAvailable");
        args[5] = smClient.getString("htmlManagerServlet.appsSessions");
        args[6] = smClient.getString("htmlManagerServlet.appsTasks");
        writer.print(MessageFormat.format(APPS_HEADER_SECTION, args));

        // Apps Row Section
        // Create sorted map of deployed applications by context name.
        Container children[] = host.findChildren();
        String contextNames[] = new String[children.length];
        for (int i = 0; i < children.length; i++)
            contextNames[i] = children[i].getName();

        Arrays.sort(contextNames);

        String appsStart = smClient.getString("htmlManagerServlet.appsStart");
        String appsStop = smClient.getString("htmlManagerServlet.appsStop");
        String appsReload = smClient.getString("htmlManagerServlet.appsReload");
        String appsUndeploy =
            smClient.getString("htmlManagerServlet.appsUndeploy");
        String appsExpire = smClient.getString("htmlManagerServlet.appsExpire");
        String noVersion = "<i>" +
            smClient.getString("htmlManagerServlet.noVersion") + "</i>";

        boolean isHighlighted = true;
        boolean isDeployed = true;
        String highlightColor = null;

        for (String contextName : contextNames) {
            Context ctxt = (Context) host.findChild(contextName);

            if (ctxt != null) {
                // Bugzilla 34818, alternating row colors
                isHighlighted = !isHighlighted;
                if(isHighlighted) {
                    highlightColor = "#C3F3C3";
                } else {
                    highlightColor = "#FFFFFF";
                }

                String contextPath = ctxt.getPath();
                String displayPath = contextPath;
                if (displayPath.equals("")) {
                    displayPath = "/";
                }

                StringBuilder tmp = new StringBuilder();
                tmp.append("path=");
                tmp.append(URLEncoder.DEFAULT.encode(displayPath, "UTF-8"));
                if (ctxt.getWebappVersion().length() > 0) {
                    tmp.append("&version=");
                    tmp.append(URLEncoder.DEFAULT.encode(ctxt.getWebappVersion(), "UTF-8"));
                }
                String pathVersion = tmp.toString();

                try {
                    isDeployed = isDeployed(contextName);
                } catch (Exception e) {
                    // Assume false on failure for safety
                    isDeployed = false;
                }

                args = new Object[7];
                args[0] = "<a href=\"" + URLEncoder.DEFAULT.encode(contextPath + "/", "UTF-8") +
                        "\">" + RequestUtil.filter(displayPath) + "</a>";
                if ("".equals(ctxt.getWebappVersion())) {
                    args[1] = noVersion;
                } else {
                    args[1] = RequestUtil.filter(ctxt.getWebappVersion());
                }
                if (ctxt.getDisplayName() == null) {
                    args[2] = "&nbsp;";
                } else {
                    args[2] = RequestUtil.filter(ctxt.getDisplayName());
                }
                args[3] = Boolean.valueOf(ctxt.getState().isAvailable());
                args[4] = RequestUtil.filter(response.encodeURL(request.getContextPath() +
                     "/html/sessions?" + pathVersion));
                Manager manager = ctxt.getManager();
                if (manager instanceof DistributedManager && showProxySessions) {
                    args[5] = Integer.valueOf(
                            ((DistributedManager)manager).getActiveSessionsFull());
                } else if (manager != null){
                    args[5] = Integer.valueOf(manager.getActiveSessions());
                } else {
                    args[5] = Integer.valueOf(0);
                }

                args[6] = highlightColor;

                writer.print
                    (MessageFormat.format(APPS_ROW_DETAILS_SECTION, args));

                args = new Object[14];
                args[0] = RequestUtil.filter(response.encodeURL(request
                        .getContextPath() + "/html/start?" + pathVersion));
                args[1] = appsStart;
                args[2] = RequestUtil.filter(response.encodeURL(request
                        .getContextPath() + "/html/stop?" + pathVersion));
                args[3] = appsStop;
                args[4] = RequestUtil.filter(response.encodeURL(request
                        .getContextPath() + "/html/reload?" + pathVersion));
                args[5] = appsReload;
                args[6] = RequestUtil.filter(response.encodeURL(request
                        .getContextPath() + "/html/undeploy?" + pathVersion));
                args[7] = appsUndeploy;
                args[8] = RequestUtil.filter(response.encodeURL(request
                        .getContextPath() + "/html/expire?" + pathVersion));
                args[9] = appsExpire;
                args[10] = smClient.getString("htmlManagerServlet.expire.explain");
                if (manager == null) {
                    args[11] = smClient.getString("htmlManagerServlet.noManager");
                } else {
                    args[11] = Integer.valueOf(ctxt.getSessionTimeout());
                }
                args[12] = smClient.getString("htmlManagerServlet.expire.unit");
                args[13] = highlightColor;

                if (ctxt.getName().equals(this.context.getName())) {
                    writer.print(MessageFormat.format(
                        MANAGER_APP_ROW_BUTTON_SECTION, args));
                } else if (ctxt.getState().isAvailable() && isDeployed) {
                    writer.print(MessageFormat.format(
                        STARTED_DEPLOYED_APPS_ROW_BUTTON_SECTION, args));
                } else if (ctxt.getState().isAvailable() && !isDeployed) {
                    writer.print(MessageFormat.format(
                        STARTED_NONDEPLOYED_APPS_ROW_BUTTON_SECTION, args));
                } else if (!ctxt.getState().isAvailable() && isDeployed) {
                    writer.print(MessageFormat.format(
                        STOPPED_DEPLOYED_APPS_ROW_BUTTON_SECTION, args));
                } else {
                    writer.print(MessageFormat.format(
                        STOPPED_NONDEPLOYED_APPS_ROW_BUTTON_SECTION, args));
                }

            }
        }

        // Deploy Section
        args = new Object[7];
        args[0] = smClient.getString("htmlManagerServlet.deployTitle");
        args[1] = smClient.getString("htmlManagerServlet.deployServer");
        args[2] = response.encodeURL(request.getContextPath() + "/html/deploy");
        args[3] = smClient.getString("htmlManagerServlet.deployPath");
        args[4] = smClient.getString("htmlManagerServlet.deployConfig");
        args[5] = smClient.getString("htmlManagerServlet.deployWar");
        args[6] = smClient.getString("htmlManagerServlet.deployButton");
        writer.print(MessageFormat.format(DEPLOY_SECTION, args));

        args = new Object[4];
        args[0] = smClient.getString("htmlManagerServlet.deployUpload");
        args[1] = response.encodeURL(request.getContextPath() + "/html/upload");
        args[2] = smClient.getString("htmlManagerServlet.deployUploadFile");
        args[3] = smClient.getString("htmlManagerServlet.deployButton");
        writer.print(MessageFormat.format(UPLOAD_SECTION, args));

        // Diagnostics section
        args = new Object[5];
        args[0] = smClient.getString("htmlManagerServlet.diagnosticsTitle");
        args[1] = smClient.getString("htmlManagerServlet.diagnosticsLeak");
        args[2] = response.encodeURL(
                request.getContextPath() + "/html/findleaks");
        args[3] = smClient.getString("htmlManagerServlet.diagnosticsLeakWarning");
        args[4] = smClient.getString("htmlManagerServlet.diagnosticsLeakButton");
        writer.print(MessageFormat.format(DIAGNOSTICS_SECTION, args));

        // Server Header Section
        args = new Object[9];
        args[0] = smClient.getString("htmlManagerServlet.serverTitle");
        args[1] = smClient.getString("htmlManagerServlet.serverVersion");
        args[2] = smClient.getString("htmlManagerServlet.serverJVMVersion");
        args[3] = smClient.getString("htmlManagerServlet.serverJVMVendor");
        args[4] = smClient.getString("htmlManagerServlet.serverOSName");
        args[5] = smClient.getString("htmlManagerServlet.serverOSVersion");
        args[6] = smClient.getString("htmlManagerServlet.serverOSArch");
        args[7] = smClient.getString("htmlManagerServlet.serverHostname");
        args[8] = smClient.getString("htmlManagerServlet.serverIPAddress");
        writer.print(MessageFormat.format
                     (Constants.SERVER_HEADER_SECTION, args));

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
        writer.print(MessageFormat.format(Constants.SERVER_ROW_SECTION, args));

        // HTML Tail Section
        writer.print(Constants.HTML_TAIL_SECTION);

        // Finish up the response
        writer.flush();
        writer.close();
    }

    /**
     * Reload the web application at the specified context path.
     *
     * @see ManagerServlet#reload(PrintWriter, ContextName, StringManager)
     *
     * @param cn Name of the application to be restarted
     * @param smClient  StringManager for the client's locale
     * @return message String
     */
    protected String reload(ContextName cn, StringManager smClient) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        super.reload(printWriter, cn, smClient);

        return stringWriter.toString();
    }

    /**
     * Undeploy the web application at the specified context path.
     *
     * @see ManagerServlet#undeploy(PrintWriter, ContextName, StringManager)
     *
     * @param cn Name of the application to be undeployed
     * @param smClient  StringManager for the client's locale
     * @return message String
     */
    protected String undeploy(ContextName cn, StringManager smClient) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        super.undeploy(printWriter, cn, smClient);

        return stringWriter.toString();
    }

    /**
     * Display session information and invoke list.
     *
     * @see ManagerServlet#sessions(PrintWriter, ContextName, int,
     *          StringManager)
     *
     * @param cn Name of the application to list session information
     * @param idle Expire all sessions with idle time &ge; idle for this context
     * @param smClient  StringManager for the client's locale
     * @return message String
     */
    protected String sessions(ContextName cn, int idle, StringManager smClient) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        super.sessions(printWriter, cn, idle, smClient);

        return stringWriter.toString();
    }

    /**
     * Start the web application at the specified context path.
     *
     * @see ManagerServlet#start(PrintWriter, ContextName, StringManager)
     *
     * @param cn Name of the application to be started
     * @param smClient  StringManager for the client's locale
     * @return message String
     */
    protected String start(ContextName cn, StringManager smClient) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        super.start(printWriter, cn, smClient);

        return stringWriter.toString();
    }

    /**
     * Stop the web application at the specified context path.
     *
     * @see ManagerServlet#stop(PrintWriter, ContextName, StringManager)
     *
     * @param cn Name of the application to be stopped
     * @param smClient  StringManager for the client's locale
     * @return message String
     */
    protected String stop(ContextName cn, StringManager smClient) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        super.stop(printWriter, cn, smClient);

        return stringWriter.toString();
    }

    /**
     * Find potential memory leaks caused by web application reload.
     *
     * @see ManagerServlet#findleaks(boolean, PrintWriter, StringManager)
     *
     * @param smClient  StringManager for the client's locale
     *
     * @return message String
     */
    protected String findleaks(StringManager smClient) {

        StringBuilder msg = new StringBuilder();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        super.findleaks(false, printWriter, smClient);

        String writerText = stringWriter.toString();

        if (writerText.length() > 0) {
            if (!writerText.startsWith("FAIL -")) {
                msg.append(smClient.getString(
                        "htmlManagerServlet.findleaksList"));
            }
            msg.append(writerText);
        } else {
            msg.append(smClient.getString("htmlManagerServlet.findleaksNone"));
        }

        return msg.toString();
    }


    /**
     * @see javax.servlet.Servlet#getServletInfo()
     */
    @Override
    public String getServletInfo() {
        return "HTMLManagerServlet, Copyright (c) 1999-2017, The Apache Software Foundation";
    }

    /**
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        super.init();

        // Set our properties from the initialization parameters
        String value = null;
        value = getServletConfig().getInitParameter("showProxySessions");
        showProxySessions = Boolean.parseBoolean(value);
    }

    // ------------------------------------------------ Sessions administration

    /**
     *
     * Extract the expiration request parameter
     *
     * @param cn Name of the application from which to expire sessions
     * @param req
     * @param smClient  StringManager for the client's locale
     */
    protected String expireSessions(ContextName cn, HttpServletRequest req,
            StringManager smClient) {
        int idle = -1;
        String idleParam = req.getParameter("idle");
        if (idleParam != null) {
            try {
                idle = Integer.parseInt(idleParam);
            } catch (NumberFormatException e) {
                log("Could not parse idle parameter to an int: " + idleParam);
            }
        }
        return sessions(cn, idle, smClient);
    }

    /**
     *
     * @param req
     * @param resp
     * @param smClient  StringManager for the client's locale
     * @throws ServletException
     * @throws IOException
     */
    protected void doSessions(ContextName cn, HttpServletRequest req,
            HttpServletResponse resp, StringManager smClient)
            throws ServletException, IOException {
        req.setAttribute("path", cn.getPath());
        req.setAttribute("version", cn.getVersion());
        String action = req.getParameter("action");
        if (debug >= 1) {
            log("sessions: Session action '" + action +
                    "' for web application '" + cn.getDisplayName() + "'");
        }
        if ("sessionDetail".equals(action)) {
            String sessionId = req.getParameter("sessionId");
            displaySessionDetailPage(req, resp, cn, sessionId, smClient);
            return;
        } else if ("invalidateSessions".equals(action)) {
            String[] sessionIds = req.getParameterValues("sessionIds");
            int i = invalidateSessions(cn, sessionIds, smClient);
            req.setAttribute(APPLICATION_MESSAGE, "" + i + " sessions invalidated.");
        } else if ("removeSessionAttribute".equals(action)) {
            String sessionId = req.getParameter("sessionId");
            String name = req.getParameter("attributeName");
            boolean removed =
                removeSessionAttribute(cn, sessionId, name, smClient);
            String outMessage = removed ? "Session attribute '" + name + "' removed." : "Session did not contain any attribute named '" + name + "'";
            req.setAttribute(APPLICATION_MESSAGE, outMessage);
            displaySessionDetailPage(req, resp, cn, sessionId, smClient);
            return;
        } // else
        displaySessionsListPage(cn, req, resp, smClient);
    }

    protected List<Session> getSessionsForName(ContextName cn,
            StringManager smClient) {
        if ((cn == null) || !(cn.getPath().startsWith("/") ||
                cn.getPath().equals(""))) {
            String path = null;
            if (cn != null) {
                path = cn.getPath();
            }
            throw new IllegalArgumentException(smClient.getString(
                    "managerServlet.invalidPath",
                    RequestUtil.filter(path)));
        }

        Context ctxt = (Context) host.findChild(cn.getName());
        if (null == ctxt) {
            throw new IllegalArgumentException(smClient.getString(
                    "managerServlet.noContext",
                    RequestUtil.filter(cn.getDisplayName())));
        }
        Manager manager = ctxt.getManager();
        List<Session> sessions = new ArrayList<Session>();
        sessions.addAll(Arrays.asList(manager.findSessions()));
        if (manager instanceof DistributedManager && showProxySessions) {
            // Add dummy proxy sessions
            Set<String> sessionIds =
                ((DistributedManager) manager).getSessionIdsFull();
            // Remove active (primary and backup) session IDs from full list
            for (Session session : sessions) {
                sessionIds.remove(session.getId());
            }
            // Left with just proxy sessions - add them
            for (String sessionId : sessionIds) {
                sessions.add(new DummyProxySession(sessionId));
            }
        }
        return sessions;
    }

    protected Session getSessionForNameAndId(ContextName cn, String id,
            StringManager smClient) {

        List<Session> sessions = getSessionsForName(cn, smClient);
        if (sessions.isEmpty()) return null;
        for(Session session : sessions) {
            if (session.getId().equals(id)) {
                return session;
            }
        }
        return null;
    }

    /**
     *
     * @param cn Name of the application for which the sessions will be listed
     * @param req
     * @param resp
     * @param smClient  StringManager for the client's locale
     * @throws ServletException
     * @throws IOException
     */
    protected void displaySessionsListPage(ContextName cn,
            HttpServletRequest req, HttpServletResponse resp,
            StringManager smClient)
            throws ServletException, IOException {
        List<Session> sessions = getSessionsForName(cn, smClient);
        String sortBy = req.getParameter("sort");
        String orderBy = null;
        if (null != sortBy && !"".equals(sortBy.trim())) {
            Comparator<Session> comparator = getComparator(sortBy);
            if (comparator != null) {
                orderBy = req.getParameter("order");
                if ("DESC".equalsIgnoreCase(orderBy)) {
                    comparator = Collections.reverseOrder(comparator);
                    orderBy = "ASC";
                } else {
                    orderBy = "DESC";
                }
                try {
                    Collections.sort(sessions, comparator);
                } catch (IllegalStateException ise) {
                    // at least 1 of the sessions is invalidated
                    req.setAttribute(APPLICATION_ERROR, "Can't sort session list: one session is invalidated");
                }
            } else {
                log("WARNING: unknown sort order: " + sortBy);
            }
        }
        // keep sort order
        req.setAttribute("sort", sortBy);
        req.setAttribute("order", orderBy);
        req.setAttribute("activeSessions", sessions);
        //strong>NOTE</strong> - This header will be overridden
        // automatically if a <code>RequestDispatcher.forward()</code> call is
        // ultimately invoked.
        resp.setHeader("Pragma", "No-cache"); // HTTP 1.0
        resp.setHeader("Cache-Control", "no-cache,no-store,max-age=0"); // HTTP 1.1
        resp.setDateHeader("Expires", 0); // 0 means now
        getServletContext().getRequestDispatcher(sessionsListJspPath).include(req, resp);
    }

    /**
     *
     * @param req
     * @param resp
     * @param smClient  StringManager for the client's locale
     * @throws ServletException
     * @throws IOException
     */
    protected void displaySessionDetailPage(HttpServletRequest req,
            HttpServletResponse resp, ContextName cn, String sessionId,
            StringManager smClient) throws ServletException, IOException {
        Session session = getSessionForNameAndId(cn, sessionId, smClient);
        //strong>NOTE</strong> - This header will be overridden
        // automatically if a <code>RequestDispatcher.forward()</code> call is
        // ultimately invoked.
        resp.setHeader("Pragma", "No-cache"); // HTTP 1.0
        resp.setHeader("Cache-Control", "no-cache,no-store,max-age=0"); // HTTP 1.1
        resp.setDateHeader("Expires", 0); // 0 means now
        req.setAttribute("currentSession", session);
        getServletContext().getRequestDispatcher(resp.encodeURL(sessionDetailJspPath)).include(req, resp);
    }

    /**
     * Invalidate HttpSessions
     * @param cn Name of the application for which sessions are to be
     *           invalidated
     * @param sessionIds
     * @param smClient  StringManager for the client's locale
     * @return number of invalidated sessions
     * @throws IOException
     */
    protected int invalidateSessions(ContextName cn, String[] sessionIds,
            StringManager smClient) throws IOException {
        if (null == sessionIds) {
            return 0;
        }
        int nbAffectedSessions = 0;
        for (int i = 0; i < sessionIds.length; ++i) {
            String sessionId = sessionIds[i];
            HttpSession session =
                getSessionForNameAndId(cn, sessionId, smClient).getSession();
            if (null == session) {
                // Shouldn't happen, but let's play nice...
                if (debug >= 1) {
                    log("WARNING: can't invalidate null session " + sessionId);
                }
                continue;
            }
            try {
                session.invalidate();
                ++nbAffectedSessions;
                if (debug >= 1) {
                    log("Invalidating session id " + sessionId);
                }
            } catch (IllegalStateException ise) {
                if (debug >= 1) {
                    log("Can't invalidate already invalidated session id " + sessionId);
                }
            }
        }
        return nbAffectedSessions;
    }

    /**
     * Removes an attribute from an HttpSession
     * @param cn Name of the application hosting the session from which the
     *           attribute is to be removed
     * @param sessionId
     * @param attributeName
     * @param smClient  StringManager for the client's locale
     * @return true if there was an attribute removed, false otherwise
     * @throws IOException
     */
    protected boolean removeSessionAttribute(ContextName cn, String sessionId,
            String attributeName, StringManager smClient) throws IOException {
        HttpSession session =
            getSessionForNameAndId(cn, sessionId, smClient).getSession();
        if (null == session) {
            // Shouldn't happen, but let's play nice...
            if (debug >= 1) {
                log("WARNING: can't remove attribute '" + attributeName + "' for null session " + sessionId);
            }
            return false;
        }
        boolean wasPresent = (null != session.getAttribute(attributeName));
        try {
            session.removeAttribute(attributeName);
        } catch (IllegalStateException ise) {
            if (debug >= 1) {
                log("Can't remote attribute '" + attributeName + "' for invalidated session id " + sessionId);
            }
        }
        return wasPresent;
    }

    protected Comparator<Session> getComparator(String sortBy) {
        Comparator<Session> comparator = null;
        if ("CreationTime".equalsIgnoreCase(sortBy)) {
            comparator = new BaseSessionComparator<Date>() {
                @Override
                public Comparable<Date> getComparableObject(Session session) {
                    return new Date(session.getCreationTime());
                }
            };
        } else if ("id".equalsIgnoreCase(sortBy)) {
            comparator = new BaseSessionComparator<String>() {
                @Override
                public Comparable<String> getComparableObject(Session session) {
                    return session.getId();
                }
            };
        } else if ("LastAccessedTime".equalsIgnoreCase(sortBy)) {
            comparator = new BaseSessionComparator<Date>() {
                @Override
                public Comparable<Date> getComparableObject(Session session) {
                    return new Date(session.getLastAccessedTime());
                }
            };
        } else if ("MaxInactiveInterval".equalsIgnoreCase(sortBy)) {
            comparator = new BaseSessionComparator<Integer>() {
                @Override
                public Comparable<Integer> getComparableObject(Session session) {
                    return Integer.valueOf(session.getMaxInactiveInterval());
                }
            };
        } else if ("new".equalsIgnoreCase(sortBy)) {
            comparator = new BaseSessionComparator<Boolean>() {
                @Override
                public Comparable<Boolean> getComparableObject(Session session) {
                    return Boolean.valueOf(session.getSession().isNew());
                }
            };
        } else if ("locale".equalsIgnoreCase(sortBy)) {
            comparator = new BaseSessionComparator<String>() {
                @Override
                public Comparable<String> getComparableObject(Session session) {
                    return JspHelper.guessDisplayLocaleFromSession(session);
                }
            };
        } else if ("user".equalsIgnoreCase(sortBy)) {
            comparator = new BaseSessionComparator<String>() {
                @Override
                public Comparable<String> getComparableObject(Session session) {
                    return JspHelper.guessDisplayUserFromSession(session);
                }
            };
        } else if ("UsedTime".equalsIgnoreCase(sortBy)) {
            comparator = new BaseSessionComparator<Date>() {
                @Override
                public Comparable<Date> getComparableObject(Session session) {
                    return new Date(SessionUtils.getUsedTimeForSession(session));
                }
            };
        } else if ("InactiveTime".equalsIgnoreCase(sortBy)) {
            comparator = new BaseSessionComparator<Date>() {
                @Override
                public Comparable<Date> getComparableObject(Session session) {
                    return new Date(SessionUtils.getInactiveTimeForSession(session));
                }
            };
        } else if ("TTL".equalsIgnoreCase(sortBy)) {
            comparator = new BaseSessionComparator<Date>() {
                @Override
                public Comparable<Date> getComparableObject(Session session) {
                    return new Date(SessionUtils.getTTLForSession(session));
                }
            };
        }
        //TODO: complete this to TTL, etc.
        return comparator;
    }

    // ------------------------------------------------------ Private Constants

    // These HTML sections are broken in relatively small sections, because of
    // limited number of substitutions MessageFormat can process
    // (maximum of 10).

    private static final String APPS_HEADER_SECTION =
        "<table border=\"1\" cellspacing=\"0\" cellpadding=\"3\">\n" +
        "<tr>\n" +
        " <td colspan=\"6\" class=\"title\">{0}</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td class=\"header-left\"><small>{1}</small></td>\n" +
        " <td class=\"header-left\"><small>{2}</small></td>\n" +
        " <td class=\"header-center\"><small>{3}</small></td>\n" +
        " <td class=\"header-center\"><small>{4}</small></td>\n" +
        " <td class=\"header-left\"><small>{5}</small></td>\n" +
        " <td class=\"header-left\"><small>{6}</small></td>\n" +
        "</tr>\n";

    private static final String APPS_ROW_DETAILS_SECTION =
        "<tr>\n" +
        " <td class=\"row-left\" bgcolor=\"{6}\" rowspan=\"2\"><small>{0}</small></td>\n" +
        " <td class=\"row-left\" bgcolor=\"{6}\" rowspan=\"2\"><small>{1}</small></td>\n" +
        " <td class=\"row-left\" bgcolor=\"{6}\" rowspan=\"2\"><small>{2}</small></td>\n" +
        " <td class=\"row-center\" bgcolor=\"{6}\" rowspan=\"2\"><small>{3}</small></td>\n" +
        " <td class=\"row-center\" bgcolor=\"{6}\" rowspan=\"2\">" +
        "<small><a href=\"{4}\">{5}</a></small></td>\n";

    private static final String MANAGER_APP_ROW_BUTTON_SECTION =
        " <td class=\"row-left\" bgcolor=\"{13}\">\n" +
        "  <small>\n" +
        "  &nbsp;{1}&nbsp;\n" +
        "  &nbsp;{3}&nbsp;\n" +
        "  &nbsp;{5}&nbsp;\n" +
        "  &nbsp;{7}&nbsp;\n" +
        "  </small>\n" +
        " </td>\n" +
        "</tr><tr>\n" +
        " <td class=\"row-left\" bgcolor=\"{13}\">\n" +
        "  <form method=\"POST\" action=\"{8}\">\n" +
        "  <small>\n" +
        "  &nbsp;<input type=\"submit\" value=\"{9}\">&nbsp;{10}&nbsp;<input type=\"text\" name=\"idle\" size=\"5\" value=\"{11}\">&nbsp;{12}&nbsp;\n" +
        "  </small>\n" +
        "  </form>\n" +
        " </td>\n" +
        "</tr>\n";

    private static final String STARTED_DEPLOYED_APPS_ROW_BUTTON_SECTION =
        " <td class=\"row-left\" bgcolor=\"{13}\">\n" +
        "  &nbsp;<small>{1}</small>&nbsp;\n" +
        "  <form class=\"inline\" method=\"POST\" action=\"{2}\">" +
        "  <small><input type=\"submit\" value=\"{3}\"></small>" +
        "  </form>\n" +
        "  <form class=\"inline\" method=\"POST\" action=\"{4}\">" +
        "  <small><input type=\"submit\" value=\"{5}\"></small>" +
        "  </form>\n" +
        "  <form class=\"inline\" method=\"POST\" action=\"{6}\">" +
        "  <small><input type=\"submit\" value=\"{7}\"></small>" +
        "  </form>\n" +
        " </td>\n" +
        " </tr><tr>\n" +
        " <td class=\"row-left\" bgcolor=\"{13}\">\n" +
        "  <form method=\"POST\" action=\"{8}\">\n" +
        "  <small>\n" +
        "  &nbsp;<input type=\"submit\" value=\"{9}\">&nbsp;{10}&nbsp;<input type=\"text\" name=\"idle\" size=\"5\" value=\"{11}\">&nbsp;{12}&nbsp;\n" +
        "  </small>\n" +
        "  </form>\n" +
        " </td>\n" +
        "</tr>\n";

    private static final String STOPPED_DEPLOYED_APPS_ROW_BUTTON_SECTION =
        " <td class=\"row-left\" bgcolor=\"{13}\" rowspan=\"2\">\n" +
        "  <form class=\"inline\" method=\"POST\" action=\"{0}\">" +
        "  <small><input type=\"submit\" value=\"{1}\"></small>" +
        "  </form>\n" +
        "  &nbsp;<small>{3}</small>&nbsp;\n" +
        "  &nbsp;<small>{5}</small>&nbsp;\n" +
        "  <form class=\"inline\" method=\"POST\" action=\"{6}\">" +
        "  <small><input type=\"submit\" value=\"{7}\"></small>" +
        "  </form>\n" +
        " </td>\n" +
        "</tr>\n<tr></tr>\n";

    private static final String STARTED_NONDEPLOYED_APPS_ROW_BUTTON_SECTION =
        " <td class=\"row-left\" bgcolor=\"{13}\">\n" +
        "  &nbsp;<small>{1}</small>&nbsp;\n" +
        "  <form class=\"inline\" method=\"POST\" action=\"{2}\">" +
        "  <small><input type=\"submit\" value=\"{3}\"></small>" +
        "  </form>\n" +
        "  <form class=\"inline\" method=\"POST\" action=\"{4}\">" +
        "  <small><input type=\"submit\" value=\"{5}\"></small>" +
        "  </form>\n" +
        "  &nbsp;<small>{7}</small>&nbsp;\n" +
        " </td>\n" +
        " </tr><tr>\n" +
        " <td class=\"row-left\" bgcolor=\"{13}\">\n" +
        "  <form method=\"POST\" action=\"{8}\">\n" +
        "  <small>\n" +
        "  &nbsp;<input type=\"submit\" value=\"{9}\">&nbsp;{10}&nbsp;<input type=\"text\" name=\"idle\" size=\"5\" value=\"{11}\">&nbsp;{12}&nbsp;\n" +
        "  </small>\n" +
        "  </form>\n" +
        " </td>\n" +
        "</tr>\n";

    private static final String STOPPED_NONDEPLOYED_APPS_ROW_BUTTON_SECTION =
        " <td class=\"row-left\" bgcolor=\"{13}\" rowspan=\"2\">\n" +
        "  <form class=\"inline\" method=\"POST\" action=\"{0}\">" +
        "  <small><input type=\"submit\" value=\"{1}\"></small>" +
        "  </form>\n" +
        "  &nbsp;<small>{3}</small>&nbsp;\n" +
        "  &nbsp;<small>{5}</small>&nbsp;\n" +
        "  &nbsp;<small>{7}</small>&nbsp;\n" +
        " </td>\n" +
        "</tr>\n<tr></tr>\n";

    private static final String DEPLOY_SECTION =
        "</table>\n" +
        "<br>\n" +
        "<table border=\"1\" cellspacing=\"0\" cellpadding=\"3\">\n" +
        "<tr>\n" +
        " <td colspan=\"2\" class=\"title\">{0}</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td colspan=\"2\" class=\"header-left\"><small>{1}</small></td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td colspan=\"2\">\n" +
        "<form method=\"post\" action=\"{2}\">\n" +
        "<table cellspacing=\"0\" cellpadding=\"3\">\n" +
        "<tr>\n" +
        " <td class=\"row-right\">\n" +
        "  <small>{3}</small>\n" +
        " </td>\n" +
        " <td class=\"row-left\">\n" +
        "  <input type=\"text\" name=\"deployPath\" size=\"20\">\n" +
        " </td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td class=\"row-right\">\n" +
        "  <small>{4}</small>\n" +
        " </td>\n" +
        " <td class=\"row-left\">\n" +
        "  <input type=\"text\" name=\"deployConfig\" size=\"20\">\n" +
        " </td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td class=\"row-right\">\n" +
        "  <small>{5}</small>\n" +
        " </td>\n" +
        " <td class=\"row-left\">\n" +
        "  <input type=\"text\" name=\"deployWar\" size=\"40\">\n" +
        " </td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td class=\"row-right\">\n" +
        "  &nbsp;\n" +
        " </td>\n" +
        " <td class=\"row-left\">\n" +
        "  <input type=\"submit\" value=\"{6}\">\n" +
        " </td>\n" +
        "</tr>\n" +
        "</table>\n" +
        "</form>\n" +
        "</td>\n" +
        "</tr>\n";

    private static final String UPLOAD_SECTION =
        "<tr>\n" +
        " <td colspan=\"2\" class=\"header-left\"><small>{0}</small></td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td colspan=\"2\">\n" +
        "<form method=\"post\" action=\"{1}\" " +
        "enctype=\"multipart/form-data\">\n" +
        "<table cellspacing=\"0\" cellpadding=\"3\">\n" +
        "<tr>\n" +
        " <td class=\"row-right\">\n" +
        "  <small>{2}</small>\n" +
        " </td>\n" +
        " <td class=\"row-left\">\n" +
        "  <input type=\"file\" name=\"deployWar\" size=\"40\">\n" +
        " </td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td class=\"row-right\">\n" +
        "  &nbsp;\n" +
        " </td>\n" +
        " <td class=\"row-left\">\n" +
        "  <input type=\"submit\" value=\"{3}\">\n" +
        " </td>\n" +
        "</tr>\n" +
        "</table>\n" +
        "</form>\n" +
        "</td>\n" +
        "</tr>\n" +
        "</table>\n" +
        "<br>\n" +
        "\n";

    private static final String DIAGNOSTICS_SECTION =
        "<table border=\"1\" cellspacing=\"0\" cellpadding=\"3\">\n" +
        "<tr>\n" +
        " <td colspan=\"2\" class=\"title\">{0}</td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td colspan=\"2\" class=\"header-left\"><small>{1}</small></td>\n" +
        "</tr>\n" +
        "<tr>\n" +
        " <td colspan=\"2\">\n" +
        "<form method=\"post\" action=\"{2}\">\n" +
        "<table cellspacing=\"0\" cellpadding=\"3\">\n" +
        "<tr>\n" +
        " <td class=\"row-left\">\n" +
        "  <input type=\"submit\" value=\"{4}\">\n" +
        " </td>\n" +
        " <td class=\"row-left\">\n" +
        "  <small>{3}</small>\n" +
        " </td>\n" +
        "</tr>\n" +
        "</table>\n" +
        "</form>\n" +
        "</td>\n" +
        "</tr>\n" +
        "</table>\n" +
        "<br>";
}
