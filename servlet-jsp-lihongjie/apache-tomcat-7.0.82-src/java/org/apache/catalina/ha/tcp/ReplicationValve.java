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

package org.apache.catalina.ha.tcp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletException;

import org.apache.catalina.Cluster;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.ha.CatalinaCluster;
import org.apache.catalina.ha.ClusterManager;
import org.apache.catalina.ha.ClusterMessage;
import org.apache.catalina.ha.ClusterSession;
import org.apache.catalina.ha.ClusterValve;
import org.apache.catalina.ha.session.DeltaManager;
import org.apache.catalina.ha.session.DeltaSession;
import org.apache.catalina.valves.ValveBase;
import org.apache.tomcat.util.res.StringManager;

/**
 * <p>Implementation of a Valve that logs interesting contents from the
 * specified Request (before processing) and the corresponding Response
 * (after processing).  It is especially useful in debugging problems
 * related to headers and cookies.</p>
 *
 * <p>This Valve may be attached to any Container, depending on the granularity
 * of the logging you wish to perform.</p>
 *
 * <p>primaryIndicator=true, then the request attribute <i>org.apache.catalina.ha.tcp.isPrimarySession.</i>
 * is set true, when request processing is at sessions primary node.
 * </p>
 *
 * @author Craig R. McClanahan
 * @author Filip Hanik
 * @author Peter Rossbach
 */
public class ReplicationValve
    extends ValveBase implements ClusterValve {
    
    private static final org.apache.juli.logging.Log log =
        org.apache.juli.logging.LogFactory.getLog( ReplicationValve.class );

    // ----------------------------------------------------- Instance Variables

    /**
     * The descriptive information related to this implementation.
     */
    private static final String info =
        "org.apache.catalina.ha.tcp.ReplicationValve/2.0";


    /**
     * The StringManager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);

    private CatalinaCluster cluster = null ;

    /**
     * Filter expression 
     */
    protected Pattern filter = null;
    
    /**
     * crossContext session container 
     */
    protected ThreadLocal<ArrayList<DeltaSession>> crossContextSessions =
        new ThreadLocal<ArrayList<DeltaSession>>() ;
    
    /**
     * doProcessingStats (default = off)
     */
    protected boolean doProcessingStats = false;
    
    protected long totalRequestTime = 0;
    protected long totalSendTime = 0;
    protected long nrOfRequests = 0;
    protected long lastSendTime = 0;
    protected long nrOfFilterRequests = 0;
    protected long nrOfSendRequests = 0;
    protected long nrOfCrossContextSendRequests = 0;
    
    /**
     * must primary change indicator set 
     */
    protected boolean primaryIndicator = false ;
    
    /**
     * Name of primary change indicator as request attribute
     */
    protected String primaryIndicatorName = "org.apache.catalina.ha.tcp.isPrimarySession";
   
    // ------------------------------------------------------------- Properties

    public ReplicationValve() {
        super(true);
    }
    
    /**
     * Return descriptive information about this Valve implementation.
     */
    @Override
    public String getInfo() {

        return (info);

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
     * @return Returns the filter
     */
    public String getFilter() {
       if (filter == null) {
           return null;
       }
       return filter.toString();
    }

    /**
     * compile filter string to regular expression
     * @see Pattern#compile(java.lang.String)
     * @param filter
     *            The filter to set.
     */
    public void setFilter(String filter) {
        if (log.isDebugEnabled())
            log.debug(sm.getString("ReplicationValve.filter.loading", filter));
        
        if (filter == null || filter.length() == 0) {
            this.filter = null;
        } else {
            try {
                this.filter = Pattern.compile(filter);
            } catch (PatternSyntaxException pse) {
                log.error(sm.getString("ReplicationValve.filter.failure",
                        filter), pse);
            }
        }
    }

    /**
     * @return Returns the primaryIndicator.
     */
    public boolean isPrimaryIndicator() {
        return primaryIndicator;
    }

    /**
     * @param primaryIndicator The primaryIndicator to set.
     */
    public void setPrimaryIndicator(boolean primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }
    
    /**
     * @return Returns the primaryIndicatorName.
     */
    public String getPrimaryIndicatorName() {
        return primaryIndicatorName;
    }
    
    /**
     * @param primaryIndicatorName The primaryIndicatorName to set.
     */
    public void setPrimaryIndicatorName(String primaryIndicatorName) {
        this.primaryIndicatorName = primaryIndicatorName;
    }
    
    /**
     * Calc processing stats
     */
    public boolean doStatistics() {
        return doProcessingStats;
    }

    /**
     * Set Calc processing stats
     * @see #resetStatistics()
     */
    public void setStatistics(boolean doProcessingStats) {
        this.doProcessingStats = doProcessingStats;
    }

    /**
     * @return Returns the lastSendTime.
     */
    public long getLastSendTime() {
        return lastSendTime;
    }
    
    /**
     * @return Returns the nrOfRequests.
     */
    public long getNrOfRequests() {
        return nrOfRequests;
    }
    
    /**
     * @return Returns the nrOfFilterRequests.
     */
    public long getNrOfFilterRequests() {
        return nrOfFilterRequests;
    }

    /**
     * @return Returns the nrOfCrossContextSendRequests.
     */
    public long getNrOfCrossContextSendRequests() {
        return nrOfCrossContextSendRequests;
    }

    /**
     * @return Returns the nrOfSendRequests.
     */
    public long getNrOfSendRequests() {
        return nrOfSendRequests;
    }

    /**
     * @return Returns the totalRequestTime.
     */
    public long getTotalRequestTime() {
        return totalRequestTime;
    }
    
    /**
     * @return Returns the totalSendTime.
     */
    public long getTotalSendTime() {
        return totalSendTime;
    }

    // --------------------------------------------------------- Public Methods
    
    /**
     * Register all cross context sessions inside endAccess.
     * Use a list with contains check, that the Portlet API can include a lot of fragments from same or
     * different applications with session changes.
     *
     * @param session cross context session
     */
    public void registerReplicationSession(DeltaSession session) {
        List<DeltaSession> sessions = crossContextSessions.get();
        if(sessions != null) {
            if(!sessions.contains(session)) {
                if(log.isDebugEnabled())
                    log.debug(sm.getString("ReplicationValve.crossContext.registerSession",
                        session.getIdInternal(),
                        session.getManager().getContainer().getName()));
                sessions.add(session);
            }
        }
    }

    /**
     * Log the interesting request parameters, invoke the next Valve in the
     * sequence, and log the interesting response parameters.
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be created
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void invoke(Request request, Response response)
        throws IOException, ServletException
    {
        long totalstart = 0;

        //this happens before the request
        if(doStatistics()) {
            totalstart = System.currentTimeMillis();
        }
        if (primaryIndicator) {
            createPrimaryIndicator(request) ;
        }
        Context context = request.getContext();
        boolean isCrossContext = context != null
                && context instanceof StandardContext
                && ((StandardContext) context).getCrossContext();
        try {
            if(isCrossContext) {
                if(log.isDebugEnabled())
                    log.debug(sm.getString("ReplicationValve.crossContext.add"));
                //FIXME add Pool of Arraylists
                crossContextSessions.set(new ArrayList<DeltaSession>());
            }
            getNext().invoke(request, response);
            if(context != null && cluster != null
                    && context.getManager() instanceof ClusterManager) {
                ClusterManager clusterManager = (ClusterManager) context.getManager();

                // valve cluster can access manager - other cluster handle replication 
                // at host level - hopefully!
                if(cluster.getManager(clusterManager.getName()) == null)
                    return ;
                if(cluster.hasMembers()) {
                    sendReplicationMessage(request, totalstart, isCrossContext, clusterManager, cluster);
                } else {
                    resetReplicationRequest(request,isCrossContext);
                }        
            }
        } finally {
            // Array must be remove: Current master request send endAccess at recycle. 
            // Don't register this request session again!
            if(isCrossContext) {
                if(log.isDebugEnabled())
                    log.debug(sm.getString("ReplicationValve.crossContext.remove"));
                // crossContextSessions.remove() only exist at Java 5
                // register ArrayList at a pool
                crossContextSessions.set(null);
            }
        }
    }

    
    /**
     * reset the active statistics 
     */
    public void resetStatistics() {
        totalRequestTime = 0 ;
        totalSendTime = 0 ;
        lastSendTime = 0 ;
        nrOfFilterRequests = 0 ;
        nrOfRequests = 0 ;
        nrOfSendRequests = 0;
        nrOfCrossContextSendRequests = 0;
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
            Cluster containerCluster = getContainer().getCluster();
            if (containerCluster instanceof CatalinaCluster) {
                setCluster((CatalinaCluster)containerCluster);
            } else {
                if (log.isWarnEnabled()) {
                    log.warn(sm.getString("ReplicationValve.nocluster"));
                }
            }
        }
        super.startInternal();
    }


    // --------------------------------------------------------- Protected Methods

    /**
     * @param request
     * @param totalstart
     * @param isCrossContext
     * @param clusterManager
     * @param containerCluster
     */
    protected void sendReplicationMessage(Request request, long totalstart, boolean isCrossContext, ClusterManager clusterManager, CatalinaCluster containerCluster) {
        //this happens after the request
        long start = 0;
        if(doStatistics()) {
            start = System.currentTimeMillis();
        }
        try {
            // send invalid sessions
            // DeltaManager returns String[0]
            if (!(clusterManager instanceof DeltaManager))
                sendInvalidSessions(clusterManager, containerCluster);
            // send replication
            sendSessionReplicationMessage(request, clusterManager, containerCluster);
            if(isCrossContext)
                sendCrossContextSession(containerCluster);
        } catch (Exception x) {
            // FIXME we have a lot of sends, but the trouble with one node stops the correct replication to other nodes!
            log.error(sm.getString("ReplicationValve.send.failure"), x);
        } finally {
            // FIXME this stats update are not cheap!!
            if(doStatistics()) {
                updateStats(totalstart,start);
            }
        }
    }

    /**
     * Send all changed cross context sessions to backups
     * @param containerCluster
     */
    protected void sendCrossContextSession(CatalinaCluster containerCluster) {
        List<DeltaSession> sessions = crossContextSessions.get();
        if(sessions != null && sessions.size() >0) {
            for(Iterator<DeltaSession> iter = sessions.iterator(); iter.hasNext() ;) {          
                Session session = iter.next();
                if(log.isDebugEnabled())
                    log.debug(sm.getString("ReplicationValve.crossContext.sendDelta",  
                            session.getManager().getContainer().getName() ));
                sendMessage(session,(ClusterManager)session.getManager(),containerCluster);
                if(doStatistics()) {
                    nrOfCrossContextSendRequests++;
                }
            }
        }
    }
  
    /**
     * Fix memory leak for long sessions with many changes, when no backup member exists!
     * @param request current request after response is generated
     * @param isCrossContext check crosscontext threadlocal
     */
    protected void resetReplicationRequest(Request request, boolean isCrossContext) {
        Session contextSession = request.getSessionInternal(false);
        if(contextSession instanceof DeltaSession){
            resetDeltaRequest(contextSession);
            ((DeltaSession)contextSession).setPrimarySession(true);
        }
        if(isCrossContext) {
            List<DeltaSession> sessions = crossContextSessions.get();
            if(sessions != null && sessions.size() >0) {
                Iterator<DeltaSession> iter = sessions.iterator();
                for(; iter.hasNext() ;) {          
                    Session session = iter.next();
                    resetDeltaRequest(session);
                    if(session instanceof DeltaSession)
                        ((DeltaSession)contextSession).setPrimarySession(true);

                }
            }
        }                     
    }

    /**
     * Reset DeltaRequest from session
     * @param session HttpSession from current request or cross context session
     */
    protected void resetDeltaRequest(Session session) {
        if(log.isDebugEnabled()) {
            log.debug(sm.getString("ReplicationValve.resetDeltaRequest" , 
                session.getManager().getContainer().getName() ));
        }
        ((DeltaSession)session).resetDeltaRequest();
    }

    /**
     * Send Cluster Replication Request
     * @param request current request
     * @param manager session manager
     * @param cluster replication cluster
     */
    protected void sendSessionReplicationMessage(Request request,
            ClusterManager manager, CatalinaCluster cluster) {
        Session session = request.getSessionInternal(false);
        if (session != null) {
            String uri = request.getDecodedRequestURI();
            // request without session change
            if (!isRequestWithoutSessionChange(uri)) {
                if (log.isDebugEnabled())
                    log.debug(sm.getString("ReplicationValve.invoke.uri", uri));
                sendMessage(session,manager,cluster);
            } else
                if(doStatistics())
                    nrOfFilterRequests++;
        }

    }

   /**
    * Send message delta message from request session 
    * @param session current session
    * @param manager session manager
    * @param cluster replication cluster
    */
    protected void sendMessage(Session session,
             ClusterManager manager, CatalinaCluster cluster) {
        String id = session.getIdInternal();
        if (id != null) {
            send(manager, cluster, id);
        }
    }

    /**
     * send manager requestCompleted message to cluster
     * @param manager SessionManager
     * @param cluster replication cluster
     * @param sessionId sessionid from the manager
     * @see DeltaManager#requestCompleted(String)
     * @see SimpleTcpCluster#send(ClusterMessage)
     */
    protected void send(ClusterManager manager, CatalinaCluster cluster, String sessionId) {
        ClusterMessage msg = manager.requestCompleted(sessionId);
        if (msg != null) {
            cluster.send(msg);
            if(doStatistics())
                nrOfSendRequests++;
        }
    }
    
    /**
     * check for session invalidations
     * @param manager
     * @param cluster
     */
    protected void sendInvalidSessions(ClusterManager manager, CatalinaCluster cluster) {
        String[] invalidIds=manager.getInvalidatedSessions();
        if ( invalidIds.length > 0 ) {
            for ( int i=0;i<invalidIds.length; i++ ) {
                try {
                    send(manager,cluster,invalidIds[i]);
                } catch ( Exception x ) {
                    log.error(sm.getString("ReplicationValve.send.invalid.failure",invalidIds[i]),x);
                }
            }
        }
    }
    
    /**
     * is request without possible session change
     * @param uri The request uri
     * @return True if no session change
     */
    protected boolean isRequestWithoutSessionChange(String uri) {
        Pattern f = filter;
        return f != null && f.matcher(uri).matches();
    }

    /**
     * Protocol cluster replications stats
     * @param requestTime
     * @param clusterTime
     */
    protected  void updateStats(long requestTime, long clusterTime) {
        // TODO: Async requests may trigger multiple replication requests. How,
        //       if at all, should the stats handle this?
        synchronized(this) {
            lastSendTime=System.currentTimeMillis();
            totalSendTime+=lastSendTime - clusterTime;
            totalRequestTime+=lastSendTime - requestTime;
            nrOfRequests++;
        }
        if(log.isInfoEnabled()) {
            if ( (nrOfRequests % 100) == 0 ) {
                 log.info(sm.getString("ReplicationValve.stats",
                     new Object[]{
                         Long.valueOf(totalRequestTime/nrOfRequests),
                         Long.valueOf(totalSendTime/nrOfRequests),
                         Long.valueOf(nrOfRequests),
                         Long.valueOf(nrOfSendRequests),
                         Long.valueOf(nrOfCrossContextSendRequests),
                         Long.valueOf(nrOfFilterRequests),
                         Long.valueOf(totalRequestTime),
                         Long.valueOf(totalSendTime)}));
             }
        }
    }


    /**
     * Mark Request that processed at primary node with attribute
     * primaryIndicatorName
     * 
     * @param request
     * @throws IOException
     */
    protected void createPrimaryIndicator(Request request) throws IOException {
        String id = request.getRequestedSessionId();
        if ((id != null) && (id.length() > 0)) {
            Manager manager = request.getContext().getManager();
            Session session = manager.findSession(id);
            if (session instanceof ClusterSession) {
                ClusterSession cses = (ClusterSession) session;
                if (log.isDebugEnabled())
                    log.debug(sm.getString(
                            "ReplicationValve.session.indicator", request.getContext().getName(),id,
                            primaryIndicatorName,
                            Boolean.valueOf(cses.isPrimarySession())));
                request.setAttribute(primaryIndicatorName, cses.isPrimarySession()?Boolean.TRUE:Boolean.FALSE);
            } else {
                if (log.isDebugEnabled()) {
                    if (session != null) {
                        log.debug(sm.getString(
                                "ReplicationValve.session.found", request.getContext().getName(),id));
                    } else {
                        log.debug(sm.getString(
                                "ReplicationValve.session.invalid", request.getContext().getName(),id));
                    }
                }
            }
        }
    }

}
