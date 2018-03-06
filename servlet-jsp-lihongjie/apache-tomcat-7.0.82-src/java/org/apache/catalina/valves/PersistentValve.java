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
package org.apache.catalina.valves;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.Store;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.session.PersistentManager;
import org.apache.tomcat.util.security.PrivilegedSetTccl;

/**
 * Valve that implements per-request session persistence. It is intended to be
 * used with non-sticky load-balancers.
 * <p>
 * <b>USAGE CONSTRAINT</b>: To work correctly it requires a  PersistentManager.
 * <p>
 * <b>USAGE CONSTRAINT</b>: To work correctly it assumes only one request exists
 *                              per session at any one time.
 *
 * @author Jean-Frederic Clere
 */
public class PersistentValve extends ValveBase {

    // Saves a couple of calls to getClassLoader() on every request. Under high
    // load these calls took just long enough to appear as a hot spot (although
    // a very minor one) in a profiler.
    private static final ClassLoader MY_CLASSLOADER = PersistentValve.class.getClassLoader();

    private volatile boolean clBindRequired;


    //------------------------------------------------------ Constructor

    public PersistentValve() {
        super(true);
    }

    
    // ----------------------------------------------------- Instance Variables

    /**
     * The descriptive information related to this implementation.
     */
    private static final String info = "org.apache.catalina.valves.PersistentValve/1.0";


    // ------------------------------------------------------------- Properties

    /**
     * Return descriptive information about this Valve implementation.
     */
    @Override
    public String getInfo() {
        return info;
    }


    @Override
    public void setContainer(Container container) {
        super.setContainer(container);
        if (container instanceof Engine || container instanceof Host) {
            clBindRequired = true;
        } else {
            clBindRequired = false;
        }
    }


    // --------------------------------------------------------- Public Methods

    /**
     * Select the appropriate child Context to process this request,
     * based on the specified request URI.  If no matching Context can
     * be found, return an appropriate HTTP error.
     *
     * @param request Request to be processed
     * @param response Response to be produced
     *
     * @exception IOException if an input/output error occurred
     * @exception ServletException if a servlet error occurred
     */
    @Override
    public void invoke(Request request, Response response)
        throws IOException, ServletException {

        // Select the Context to be used for this Request
        Context context = request.getContext();
        if (context == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    sm.getString("standardHost.noContext"));
            return;
        }

        // Update the session last access time for our session (if any)
        String sessionId = request.getRequestedSessionId();
        Manager manager = context.getManager();
        if (sessionId != null &&  manager instanceof PersistentManager) {
            Store store = ((PersistentManager) manager).getStore();
            if (store != null) {
                Session session = null;
                try {
                    session = store.load(sessionId);
                } catch (Exception e) {
                    container.getLogger().error("deserializeError");
                }
                if (session != null) {
                    if (!session.isValid() ||
                        isSessionStale(session, System.currentTimeMillis())) {
                        if (container.getLogger().isDebugEnabled()) {
                            container.getLogger().debug("session swapped in is invalid or expired");
                        }
                        session.expire();
                        store.remove(sessionId);
                    } else {
                        session.setManager(manager);
                        // session.setId(sessionId); Only if new ???
                        manager.add(session);
                        // ((StandardSession)session).activate();
                        session.access();
                        session.endAccess();
                    }
                }
            }
        }
        if (container.getLogger().isDebugEnabled()) {
            container.getLogger().debug("sessionId: " + sessionId);
        }

        // Ask the next valve to process the request.
        getNext().invoke(request, response);

        // If still processing async, don't try to store the session
        if (!request.isAsync()) {
            // Read the sessionid after the response.
            // HttpSession hsess = hreq.getSession(false);
            Session hsess;
            try {
                hsess = request.getSessionInternal(false);
            } catch (Exception ex) {
                hsess = null;
            }
            String newsessionId = null;
            if (hsess!=null) {
                newsessionId = hsess.getIdInternal();
            }

            if (container.getLogger().isDebugEnabled()) {
                container.getLogger().debug("newsessionId: " + newsessionId);
            }
            if (newsessionId!=null) {
                try {
                    bind(context);
                    /* store the session and remove it from the manager */
                    if (manager instanceof PersistentManager) {
                        Session session = manager.findSession(newsessionId);
                        Store store = ((PersistentManager) manager).getStore();
                        if (store != null && session != null && session.isValid() &&
                                !isSessionStale(session, System.currentTimeMillis())) {
                            store.save(session);
                            ((PersistentManager) manager).removeSuper(session);
                            session.recycle();
                        } else {
                            if (container.getLogger().isDebugEnabled()) {
                                container.getLogger().debug("newsessionId store: " +
                                        store + " session: " + session +
                                        " valid: " +
                                        (session == null ? "N/A" : Boolean.toString(
                                                session.isValid())) +
                                        " stale: " + isSessionStale(session,
                                                System.currentTimeMillis()));
                            }
                        }                    
                    } else {
                        if (container.getLogger().isDebugEnabled()) {
                            container.getLogger().debug("newsessionId Manager: " +
                                    manager);
                        }
                    }
                } finally {
                    unbind();
                }
            }
        }
    }


    /**
     * Indicate whether the session has been idle for longer
     * than its expiration date as of the supplied time.
     *
     * FIXME: Probably belongs in the Session class.
     */
    protected boolean isSessionStale(Session session, long timeNow) {

        if (session != null) {
            int maxInactiveInterval = session.getMaxInactiveInterval();
            if (maxInactiveInterval >= 0) {
                int timeIdle = // Truncate, do not round up
                    (int) ((timeNow - session.getThisAccessedTime()) / 1000L);
                if (timeIdle >= maxInactiveInterval) {
                    return true;
                }
            }
        }

        return false;
    }


    private void bind(Context context) {
        // Bind the context CL to the current thread
        if (clBindRequired && context.getLoader() != null) {
            if (Globals.IS_SECURITY_ENABLED) {
                PrivilegedAction<Void> pa =
                        new PrivilegedSetTccl(context.getLoader().getClassLoader());
                AccessController.doPrivileged(pa);                
            } else {
                Thread.currentThread().setContextClassLoader(context.getLoader().getClassLoader());
            }
        }
    }


    private void unbind() {
        if (clBindRequired) {
            if (Globals.IS_SECURITY_ENABLED) {
                PrivilegedAction<Void> pa = new PrivilegedSetTccl(MY_CLASSLOADER);
                AccessController.doPrivileged(pa);                
            } else {
                Thread.currentThread().setContextClassLoader(MY_CLASSLOADER);
            }
        }
    }
}
