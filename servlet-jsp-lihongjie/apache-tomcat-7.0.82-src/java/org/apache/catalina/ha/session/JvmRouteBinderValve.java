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
package org.apache.catalina.ha.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.ha.CatalinaCluster;
import org.apache.catalina.ha.ClusterManager;
import org.apache.catalina.ha.ClusterValve;
import org.apache.catalina.session.ManagerBase;
import org.apache.catalina.session.PersistentManager;
import org.apache.catalina.valves.ValveBase;
import org.apache.tomcat.util.res.StringManager;

/**
 * Valve to handle Tomcat jvmRoute takeover using mod_jk module after node
 * failure. After a node crashes, subsequent requests go to other cluster nodes.
 * That incurs a drop in performance. When this Valve is enabled on a backup
 * node and sees a request, which was intended for another (thus failed) node,
 * it will rewrite the cookie jsessionid information to use the route to this
 * backup cluster node, that answered the request. After the response is
 * delivered to the client, all subsequent client requests will go directly to
 * the backup node. The change of sessionid is also sent to all other cluster
 * nodes. After all that, the session stickiness will work directly to the
 * backup node and the traffic will not go back to the failed node after it is
 * restarted!
 * 
 * <p>
 * For this valve to function correctly, so that all nodes of the cluster
 * receive the sessionid change notifications that it generates, the following
 * ClusterListener MUST be configured at all nodes of the cluster:
 * {@link org.apache.catalina.ha.session.JvmRouteSessionIDBinderListener
 * JvmRouteSessionIDBinderListener} since Tomcat 5.5.10, and both
 * JvmRouteSessionIDBinderListener and JvmRouteSessionIDBinderLifecycleListener
 * for earlier versions of Tomcat.
 * 
 * <p>
 * Add this Valve to your host definition at conf/server.xml .
 * 
 * Since 5.5.10 as direct cluster valve:<br/>
 * 
 * <pre>
 *  &lt;Cluster&gt;
 *  &lt;Valve className=&quot;org.apache.catalina.ha.session.JvmRouteBinderValve&quot; /&gt;  
 *  &lt;/Cluster&gt;
 * </pre>
 * 
 * <br />
 * Before 5.5.10 as Host element:<br/>
 * 
 * <pre>
 *  &lt;Host&gt;
 *  &lt;Valve className=&quot;org.apache.catalina.ha.session.JvmRouteBinderValve&quot; /&gt;  
 *  &lt;/Host&gt;
 * </pre>
 * 
 * <em>A Trick:</em><br/>
 * You can enable this mod_jk turnover mode via JMX before you drop a node to
 * all backup nodes! Set enable true on all JvmRouteBinderValve backups, disable
 * worker at mod_jk and then drop node and restart it! Then enable mod_jk worker
 * and disable JvmRouteBinderValves again. This use case means that only
 * requested sessions are migrated.
 * 
 * @author Peter Rossbach
 */
public class JvmRouteBinderValve extends ValveBase implements ClusterValve {

    /*--Static Variables----------------------------------------*/
    public static final org.apache.juli.logging.Log log = org.apache.juli.logging.LogFactory
            .getLog(JvmRouteBinderValve.class);

    /**
     * The descriptive information about this implementation.
     */
    protected static final String info = "org.apache.catalina.ha.session.JvmRouteBinderValve/1.2";

    //------------------------------------------------------ Constructor
    public JvmRouteBinderValve() {
        super(true);
    }

    /*--Instance Variables--------------------------------------*/

    /**
     * the cluster
     */
    protected CatalinaCluster cluster;

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm = StringManager.getManager(Constants.Package);

    /**
     * enabled this component
     */
    protected boolean enabled = true;

    /**
     * number of session that no at this tomcat instance hosted
     */
    protected long numberOfSessions = 0;

    protected String sessionIdAttribute = "org.apache.catalina.ha.session.JvmRouteOrignalSessionID";


    /*--Logic---------------------------------------------------*/

    /**
     * Return descriptive information about this implementation.
     */
    @Override
    public String getInfo() {

        return (info);

    }

    /**
     * set session id attribute to failed node for request.
     * 
     * @return Returns the sessionIdAttribute.
     */
    public String getSessionIdAttribute() {
        return sessionIdAttribute;
    }

    /**
     * get name of failed request session attribute
     * 
     * @param sessionIdAttribute
     *            The sessionIdAttribute to set.
     */
    public void setSessionIdAttribute(String sessionIdAttribute) {
        this.sessionIdAttribute = sessionIdAttribute;
    }

    /**
     * @return Returns the number of migrated sessions.
     */
    public long getNumberOfSessions() {
        return numberOfSessions;
    }

    /**
     * @return Returns the enabled.
     */
    public boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            The enabled to set.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Detect possible the JVMRoute change at cluster backup node..
     * 
     * @param request
     *            tomcat request being processed
     * @param response
     *            tomcat response being processed
     * @exception IOException
     *                if an input/output error has occurred
     * @exception ServletException
     *                if a servlet error has occurred
     */
    @Override
    public void invoke(Request request, Response response) throws IOException,
            ServletException {

         if (getEnabled() &&
                 request.getContext() != null &&
                 request.getContext().getDistributable() &&
                 !request.isAsyncDispatching()) {
             // valve cluster can access manager - other cluster handle turnover 
             // at host level - hopefully!
             Manager manager = request.getContext().getManager();

             if (manager != null && (
                     (manager instanceof ClusterManager
                       && getCluster() != null
                       && getCluster().getManager(((ClusterManager)manager).getName()) != null)
                     ||
                     (manager instanceof PersistentManager)))
                 handlePossibleTurnover(request);
        }
        // Pass this request on to the next valve in our pipeline
        getNext().invoke(request, response);
    }

    /**
     * handle possible session turn over.
     * 
     * @see JvmRouteBinderValve#handleJvmRoute(Request, String, String)
     * @param request current request
     */
    protected void handlePossibleTurnover(Request request) {
        String sessionID = request.getRequestedSessionId() ;
        if (sessionID != null) {
            long t1 = System.currentTimeMillis();
            String jvmRoute = getLocalJvmRoute(request);
            if (jvmRoute == null) {
                if (log.isDebugEnabled())
                    log.debug(sm.getString("jvmRoute.missingJvmRouteAttribute"));
                return;
            }
            handleJvmRoute( request, sessionID, jvmRoute);
            if (log.isDebugEnabled()) {
                long t2 = System.currentTimeMillis();
                long time = t2 - t1;
                log.debug(sm.getString("jvmRoute.turnoverInfo", Long.valueOf(time)));
            }
        }
    }

    /**
     * get jvmroute from engine
     * 
     * @param request current request
     * @return return jvmRoute from ManagerBase or null
     */
    protected String getLocalJvmRoute(Request request) {
        Manager manager = getManager(request);
        if(manager instanceof ManagerBase)
            return ((ManagerBase) manager).getJvmRoute();
        return null ;
    }

    /**
     * get ClusterManager
     * 
     * @param request current request
     * @return manager or null
     */
    protected Manager getManager(Request request) {
        Manager manager = request.getContext().getManager();
        if (log.isDebugEnabled()) {
            if(manager != null)
                log.debug(sm.getString("jvmRoute.foundManager", manager,  request.getContext().getName()));
            else 
                log.debug(sm.getString("jvmRoute.notFoundManager", request.getContext().getName()));
        }
        return manager;
    }

    /**
     * @return Returns the cluster.
     */
    @Override
    public CatalinaCluster getCluster() {
        return cluster;
    }
    
    /**
     * @param cluster The cluster to set.
     */
    @Override
    public void setCluster(CatalinaCluster cluster) {
        this.cluster = cluster;
    }
    
    /**
     * Handle jvmRoute stickiness after tomcat instance failed. After this
     * correction a new Cookie send to client with new jvmRoute and the
     * SessionID change propagate to the other cluster nodes.
     * 
     * @param request current request
     * @param sessionId
     *            request SessionID from Cookie
     * @param localJvmRoute
     *            local jvmRoute
     */
    protected void handleJvmRoute(
            Request request, String sessionId, String localJvmRoute) {
        // get requested jvmRoute.
        String requestJvmRoute = null;
        int index = sessionId.indexOf('.');
        if (index > 0) {
            requestJvmRoute = sessionId
                    .substring(index + 1, sessionId.length());
        }
        if (requestJvmRoute != null && !requestJvmRoute.equals(localJvmRoute)) {
            if (log.isDebugEnabled()) {
                log.debug(sm.getString("jvmRoute.failover", requestJvmRoute,
                        localJvmRoute, sessionId));
            }
            Session catalinaSession = null;
            try {
                catalinaSession = getManager(request).findSession(sessionId);
            } catch (IOException e) {
                // Hups!
            }
            String id = sessionId.substring(0, index);
            String newSessionID = id + "." + localJvmRoute;
            // OK - turnover the session and inform other cluster nodes
            if (catalinaSession != null) {
                changeSessionID(request, sessionId, newSessionID,
                        catalinaSession);
                numberOfSessions++;
            } else {
                try {
                    catalinaSession = getManager(request).findSession(newSessionID);
                } catch (IOException e) {
                    // Hups!
                }
                if (catalinaSession != null) {
                    // session is rewrite at other request, rewrite this also
                    changeRequestSessionID(request, sessionId, newSessionID);
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug(sm.getString("jvmRoute.cannotFindSession",sessionId));
                    }
                }
            }
        }
    }

    /**
     * change session id and send to all cluster nodes
     * 
     * @param request current request
     * @param sessionId
     *            original session id
     * @param newSessionID
     *            new session id for node migration
     * @param catalinaSession
     *            current session with original session id
     */
    protected void changeSessionID(Request request, String sessionId,
            String newSessionID, Session catalinaSession) {
        fireLifecycleEvent("Before session migration", catalinaSession);
        catalinaSession.setId(newSessionID, false);
        // FIXME: Why we remove change data from other running request?
        // setId also trigger resetDeltaRequest!!
        if (catalinaSession instanceof DeltaSession)
            ((DeltaSession) catalinaSession).resetDeltaRequest();
        changeRequestSessionID(request, sessionId, newSessionID);

        // now sending the change to all other clusternodes!
        sendSessionIDClusterBackup(request,sessionId, newSessionID);

        fireLifecycleEvent("After session migration", catalinaSession);
        if (log.isDebugEnabled()) {
            log.debug(sm.getString("jvmRoute.changeSession", sessionId,
                    newSessionID));
        }   
    }

    /**
     * Change Request Session id
     * @param request current request
     * @param sessionId
     *            original session id
     * @param newSessionID
     *            new session id for node migration
     */
    protected void changeRequestSessionID(Request request, String sessionId, String newSessionID) {
        request.changeSessionId(newSessionID);

        // set original sessionid at request, to allow application detect the
        // change
        if (sessionIdAttribute != null && !"".equals(sessionIdAttribute)) {
            if (log.isDebugEnabled()) {
                log.debug(sm.getString("jvmRoute.set.orignalsessionid",sessionIdAttribute,sessionId));
            }
            request.setAttribute(sessionIdAttribute, sessionId);
        }
    }
    
    /**
     * Send the changed Sessionid to all clusternodes.
     * 
     * @see JvmRouteSessionIDBinderListener#messageReceived(
     *            org.apache.catalina.ha.ClusterMessage)
     * @param sessionId
     *            current failed sessionid
     * @param newSessionID
     *            new session id, bind to the new cluster node
     */
    protected void sendSessionIDClusterBackup(Request request, String sessionId,
            String newSessionID) {
        CatalinaCluster c = getCluster();
        if (c != null && !(getManager(request) instanceof BackupManager)) {
            SessionIDMessage msg = new SessionIDMessage();
            msg.setOrignalSessionID(sessionId);
            msg.setBackupSessionID(newSessionID);
            Context context = request.getContext();
            msg.setContextName(context.getName());
            msg.setHost(context.getParent().getName());
            c.send(msg);
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
        
        if (cluster == null) {
            Container hostContainer = getContainer();
            // compatibility with JvmRouteBinderValve version 1.1
            // ( setup at context.xml or context.xml.default )
            if (!(hostContainer instanceof Host)) {
                if (log.isWarnEnabled())
                    log.warn(sm.getString("jvmRoute.configure.warn"));
                hostContainer = hostContainer.getParent();
            }
            if (hostContainer instanceof Host
                    && ((Host) hostContainer).getCluster() != null) {
                cluster = (CatalinaCluster) ((Host) hostContainer).getCluster();
            } else {
                Container engine = hostContainer.getParent() ;
                if (engine instanceof Engine
                        && ((Engine) engine).getCluster() != null) {
                    cluster = (CatalinaCluster) ((Engine) engine).getCluster();
                }
            }
        }
        
        if (log.isInfoEnabled()) {
            log.info(sm.getString("jvmRoute.valve.started"));
            if (cluster == null)
                log.info(sm.getString("jvmRoute.noCluster"));
        }

        super.startInternal();
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

        super.stopInternal();

        cluster = null;
        numberOfSessions = 0;
        if (log.isInfoEnabled())
            log.info(sm.getString("jvmRoute.valve.stopped"));

    }

}
