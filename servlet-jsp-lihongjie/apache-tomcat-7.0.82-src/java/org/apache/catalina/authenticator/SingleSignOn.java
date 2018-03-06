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
package org.apache.catalina.authenticator;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Manager;
import org.apache.catalina.Realm;
import org.apache.catalina.Session;
import org.apache.catalina.SessionListener;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.tomcat.util.res.StringManager;

/**
 * A <strong>Valve</strong> that supports a "single sign on" user experience,
 * where the security identity of a user who successfully authenticates to one
 * web application is propagated to other web applications in the same
 * security domain.  For successful use, the following requirements must
 * be met:
 * <ul>
 * <li>This Valve must be configured on the Container that represents a
 *     virtual host (typically an implementation of <code>Host</code>).</li>
 * <li>The <code>Realm</code> that contains the shared user and role
 *     information must be configured on the same Container (or a higher
 *     one), and not overridden at the web application level.</li>
 * <li>The web applications themselves must use one of the standard
 *     Authenticators found in the
 *     <code>org.apache.catalina.authenticator</code> package.</li>
 * </ul>
 *
 * @author Craig R. McClanahan
 */
public class SingleSignOn extends ValveBase {

    protected static final boolean LAST_ACCESS_AT_START;

    static {
        String lastAccessAtStart = System.getProperty(
                "org.apache.catalina.session.StandardSession.LAST_ACCESS_AT_START");
        if (lastAccessAtStart == null) {
            LAST_ACCESS_AT_START = Globals.STRICT_SERVLET_COMPLIANCE;
        } else {
            LAST_ACCESS_AT_START = Boolean.parseBoolean(lastAccessAtStart);
        }
    }
    
    private static final StringManager sm = StringManager.getManager(Constants.Package);

    /* The engine at the top of the container hierarchy in which this SSO Valve
     * has been placed. It is used to get back to a session object from a
     * SingleSignOnSessionKey and is updated when the Valve starts and stops.
     */
    private Engine engine;

    //------------------------------------------------------ Constructor

    public SingleSignOn() {
        super(true);
    }


    // ----------------------------------------------------- Instance Variables

    /**
     * The cache of SingleSignOnEntry instances for authenticated Principals,
     * keyed by the cookie value that is used to select them.
     */
    protected Map<String,SingleSignOnEntry> cache =
            new ConcurrentHashMap<String,SingleSignOnEntry>();

    /**
     * Descriptive information about this Valve implementation.
     */
    protected static final String info =
        "org.apache.catalina.authenticator.SingleSignOn";


    /**
     * Indicates whether this valve should require a downstream Authenticator to
     * reauthenticate each request, or if it itself can bind a UserPrincipal
     * and AuthType object to the request.
     */
    private boolean requireReauthentication = false;

    /**
     * Optional SSO cookie domain.
     */
    private String cookieDomain;


    // ------------------------------------------------------------- Properties

    /**
     * Returns the optional cookie domain.
     * May return null.
     *
     * @return The cookie domain
     */
    public String getCookieDomain() {
        return cookieDomain;
    }


    /**
     * Sets the domain to be used for sso cookies.
     *
     * @param cookieDomain cookie domain name
     */
    public void setCookieDomain(String cookieDomain) {
        if (cookieDomain != null && cookieDomain.trim().length() == 0) {
            this.cookieDomain = null;
        } else {
            this.cookieDomain = cookieDomain;
        }
    }


    /**
     * Gets whether each request needs to be reauthenticated (by an
     * Authenticator downstream in the pipeline) to the security
     * <code>Realm</code>, or if this Valve can itself bind security info
     * to the request based on the presence of a valid SSO entry without
     * rechecking with the <code>Realm</code>.
     *
     * @return  <code>true</code> if it is required that a downstream
     *          Authenticator reauthenticate each request before calls to
     *          <code>HttpServletRequest.setUserPrincipal()</code>
     *          and <code>HttpServletRequest.setAuthType()</code> are made;
     *          <code>false</code> if the <code>Valve</code> can itself make
     *          those calls relying on the presence of a valid SingleSignOn
     *          entry associated with the request.
     *
     * @see #setRequireReauthentication
     */
    public boolean getRequireReauthentication() {
        return requireReauthentication;
    }


    /**
     * Sets whether each request needs to be reauthenticated (by an
     * Authenticator downstream in the pipeline) to the security
     * <code>Realm</code>, or if this Valve can itself bind security info
     * to the request, based on the presence of a valid SSO entry, without
     * rechecking with the <code>Realm</code>.
     * <p>
     * If this property is <code>false</code> (the default), this
     * <code>Valve</code> will bind a UserPrincipal and AuthType to the request
     * if a valid SSO entry is associated with the request.  It will not notify
     * the security <code>Realm</code> of the incoming request.
     * <p>
     * This property should be set to <code>true</code> if the overall server
     * configuration requires that the <code>Realm</code> reauthenticate each
     * request thread.  An example of such a configuration would be one where
     * the <code>Realm</code> implementation provides security for both a
     * web tier and an associated EJB tier, and needs to set security
     * credentials on each request thread in order to support EJB access.
     * <p>
     * If this property is set to <code>true</code>, this Valve will set flags
     * on the request notifying the downstream Authenticator that the request
     * is associated with an SSO session.  The Authenticator will then call its
     * {@link AuthenticatorBase#reauthenticateFromSSO reauthenticateFromSSO}
     * method to attempt to reauthenticate the request to the
     * <code>Realm</code>, using any credentials that were cached with this
     * Valve.
     * <p>
     * The default value of this property is <code>false</code>, in order
     * to maintain backward compatibility with previous versions of Tomcat.
     *
     * @param required  <code>true</code> if it is required that a downstream
     *                  Authenticator reauthenticate each request before calls
     *                  to  <code>HttpServletRequest.setUserPrincipal()</code>
     *                  and <code>HttpServletRequest.setAuthType()</code> are
     *                  made; <code>false</code> if the <code>Valve</code> can
     *                  itself make those calls relying on the presence of a
     *                  valid SingleSignOn entry associated with the request.
     *
     * @see AuthenticatorBase#reauthenticateFromSSO
     */
    public void setRequireReauthentication(boolean required) {
        this.requireReauthentication = required;
    }


    // ---------------------------------------------------------- Valve Methods

    /**
     * Return descriptive information about this Valve implementation.
     */
    @Override
    public String getInfo() {
        return info;
    }


    /**
     * Perform single-sign-on support processing for this request.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void invoke(Request request, Response response)
        throws IOException, ServletException {

        request.removeNote(Constants.REQ_SSOID_NOTE);

        // Has a valid user already been authenticated?
        if (containerLog.isDebugEnabled()) {
            containerLog.debug(sm.getString("singleSignOn.debug.invoke", request.getRequestURI()));
        }
        if (request.getUserPrincipal() != null) {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.hasPrincipal",
                        request.getUserPrincipal().getName()));
            }
            getNext().invoke(request, response);
            return;
        }

        // Check for the single sign on cookie
        if (containerLog.isDebugEnabled()) {
            containerLog.debug(sm.getString("singleSignOn.debug.cookieCheck"));
        }
        Cookie cookie = null;
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (Constants.SINGLE_SIGN_ON_COOKIE.equals(cookies[i].getName())) {
                    cookie = cookies[i];
                    break;
                }
            }
        }
        if (cookie == null) {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.cookieNotFound"));
            }
            getNext().invoke(request, response);
            return;
        }

        // Look up the cached Principal associated with this cookie value
        if (containerLog.isDebugEnabled()) {
            containerLog.debug(sm.getString("singleSignOn.debug.principalCheck",
                    cookie.getValue()));
        }
        SingleSignOnEntry entry = cache.get(cookie.getValue());
        if (entry != null) {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.principalFound",
                        entry.getPrincipal() != null ? entry.getPrincipal().getName() : "",
                        entry.getAuthType()));
            }
            request.setNote(Constants.REQ_SSOID_NOTE, cookie.getValue());
            // Only set security elements if reauthentication is not required
            if (!getRequireReauthentication()) {
                request.setAuthType(entry.getAuthType());
                request.setUserPrincipal(entry.getPrincipal());
            }
        } else {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.principalNotFound",
                        cookie.getValue()));
            }
            // No need to return a valid SSO session ID
            cookie.setValue("REMOVE");
            // Age of zero will trigger removal
            cookie.setMaxAge(0);
            // Domain and path have to match the original cookie to 'replace'
            // the original cookie
            cookie.setPath("/");
            String domain = getCookieDomain();
            if (domain != null) {
                cookie.setDomain(domain);
            }
            // This is going to trigger a Set-Cookie header. While the value is
            // not security sensitive, ensure that expectations for secure and
            // httpOnly are met
            cookie.setSecure(request.isSecure());
            if (request.getServletContext().getSessionCookieConfig().isHttpOnly() ||
                    request.getContext().getUseHttpOnly()) {
                cookie.setHttpOnly(true);
            }

            response.addCookie(cookie);
        }

        // Invoke the next Valve in our pipeline
        getNext().invoke(request, response);
    }


    // ------------------------------------------------------ Protected Methods

    /**
     * Process a session destroyed event by removing references to that session
     * from the caches and - if the session destruction is the result of a
     * logout - destroy the associated SSO session.
     *
     * @param ssoId   The ID of the SSO session which which the destroyed
     *                session was associated
     * @param session The session that has been destroyed
     */
    public void sessionDestroyed(String ssoId, Session session) {

        if (!getState().isAvailable()) {
            return;
        }

        // Was the session destroyed as the result of a timeout or context stop?
        // If so, we'll just remove the expired session from the SSO. If the
        // session was logged out, we'll log out of all session associated with
        // the SSO.
        if (((session.getMaxInactiveInterval() > 0)
            && (System.currentTimeMillis() - session.getThisAccessedTimeInternal() >=
                session.getMaxInactiveInterval() * 1000))
            || (!((Context)session.getManager().getContainer()).getState().isAvailable())) {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.sessionTimeout",
                        ssoId, session));
            }
            removeSession(ssoId, session);
        } else {
            // The session was logged out.
            // Deregister this single session id, invalidating
            // associated sessions
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.sessionLogout",
                        ssoId, session));
            }
            // First remove the session that we know has expired / been logged
            // out since it has already been removed from its Manager and, if
            // we don't remove it first, deregister() will log a warning that it
            // can't be found
            removeSession(ssoId, session);
            // If the SSO session was only associated with one web app the call
            // above will have removed the SSO session from the cache
            if (cache.containsKey(ssoId)) {
                deregister(ssoId);
            }
        }
    }


    /**
     * Associate the specified single sign on identifier with the
     * specified Session.
     *
     * @param ssoId Single sign on identifier
     * @param session Session to be associated
     *
     * @return <code>true</code> if the session was associated to the given SSO
     *         session, otherwise <code>false</code>
     */
    protected boolean associate(String ssoId, Session session) {
        SingleSignOnEntry sso = cache.get(ssoId);
        if (sso == null) {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.associateFail",
                        ssoId, session));
            }
            return false;
        } else {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.associate",
                        ssoId, session));
            }
            sso.addSession(this, ssoId, session);
            return true;
        }
    }


    /**
     * Deregister the specified single sign on identifier, and invalidate
     * any associated sessions.
     *
     * @param ssoId Single sign on identifier to deregister
     */
    protected void deregister(String ssoId) {

        // Look up and remove the corresponding SingleSignOnEntry
        SingleSignOnEntry sso = cache.remove(ssoId);

        if (sso == null) {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.deregisterFail", ssoId));
            }
            return;
        }

        // Expire any associated sessions
        Set<SingleSignOnSessionKey> ssoKeys = sso.findSessions();
        if (ssoKeys.size() == 0) {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.deregisterNone", ssoId));
            }
        }
        for (SingleSignOnSessionKey ssoKey : ssoKeys) {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.deregister", ssoKey, ssoId));
            }
            // Invalidate this session
            expire(ssoKey);
        }

        // NOTE:  Clients may still possess the old single sign on cookie,
        // but it will be removed on the next request since it is no longer
        // in the cache
    }


    private void expire(SingleSignOnSessionKey key) {
        if (engine == null) {
            containerLog.warn(sm.getString("singleSignOn.sessionExpire.engineNull", key));
            return;
        }
        Container host = engine.findChild(key.getHostName());
        if (host == null) {
            containerLog.warn(sm.getString("singleSignOn.sessionExpire.hostNotFound", key));
            return;
        }
        Context context = (Context) host.findChild(key.getContextName());
        if (context == null) {
            containerLog.warn(sm.getString("singleSignOn.sessionExpire.contextNotFound", key));
            return;
        }
        Manager manager = context.getManager();
        if (manager == null) {
            containerLog.warn(sm.getString("singleSignOn.sessionExpire.managerNotFound", key));
            return;
        }
        Session session = null;
        try {
            session = manager.findSession(key.getSessionId());
        } catch (IOException e) {
            containerLog.warn(sm.getString("singleSignOn.sessionExpire.managerError", key), e);
            return;
        }
        if (session == null) {
            containerLog.warn(sm.getString("singleSignOn.sessionExpire.sessionNotFound", key));
            return;
        }
        session.expire();
    }


    /**
     * Attempts reauthentication to the given <code>Realm</code> using
     * the credentials associated with the single sign-on session
     * identified by argument <code>ssoId</code>.
     * <p>
     * If reauthentication is successful, the <code>Principal</code> and
     * authorization type associated with the SSO session will be bound
     * to the given <code>Request</code> object via calls to 
     * {@link Request#setAuthType Request.setAuthType()} and 
     * {@link Request#setUserPrincipal Request.setUserPrincipal()}
     * </p>
     *
     * @param ssoId     identifier of SingleSignOn session with which the
     *                  caller is associated
     * @param realm     Realm implementation against which the caller is to
     *                  be authenticated
     * @param request   the request that needs to be authenticated
     * 
     * @return  <code>true</code> if reauthentication was successful,
     *          <code>false</code> otherwise.
     */
    protected boolean reauthenticate(String ssoId, Realm realm,
                                     Request request) {

        if (ssoId == null || realm == null) {
            return false;
        }

        boolean reauthenticated = false;

        SingleSignOnEntry entry = cache.get(ssoId);
        if (entry != null && entry.getCanReauthenticate()) {
            
            String username = entry.getUsername();
            if (username != null) {
                Principal reauthPrincipal =
                        realm.authenticate(username, entry.getPassword());                
                if (reauthPrincipal != null) {                    
                    reauthenticated = true;                    
                    // Bind the authorization credentials to the request
                    request.setAuthType(entry.getAuthType());
                    request.setUserPrincipal(reauthPrincipal);
                }
            }
        }

        return reauthenticated;
    }


    /**
     * Register the specified Principal as being associated with the specified
     * value for the single sign on identifier.
     *
     * @param ssoId Single sign on identifier to register
     * @param principal Associated user principal that is identified
     * @param authType Authentication type used to authenticate this
     *  user principal
     * @param username Username used to authenticate this user
     * @param password Password used to authenticate this user
     */
    protected void register(String ssoId, Principal principal, String authType,
                  String username, String password) {

        if (containerLog.isDebugEnabled()) {
            containerLog.debug(sm.getString("singleSignOn.debug.register", ssoId,
                    principal != null ? principal.getName() : "", authType));
        }

        cache.put(ssoId, new SingleSignOnEntry(principal, authType, username, password));
    }


    /**
     * Updates any <code>SingleSignOnEntry</code> found under key
     * <code>ssoId</code> with the given authentication data.
     * <p>
     * The purpose of this method is to allow an SSO entry that was
     * established without a username/password combination (i.e. established
     * following DIGEST or CLIENT_CERT authentication) to be updated with
     * a username and password if one becomes available through a subsequent
     * BASIC or FORM authentication.  The SSO entry will then be usable for
     * reauthentication.
     * <p>
     * <b>NOTE:</b> Only updates the SSO entry if a call to
     * <code>SingleSignOnEntry.getCanReauthenticate()</code> returns
     * <code>false</code>; otherwise, it is assumed that the SSO entry already
     * has sufficient information to allow reauthentication and that no update
     * is needed.
     *
     * @param ssoId     identifier of Single sign to be updated
     * @param principal the <code>Principal</code> returned by the latest
     *                  call to <code>Realm.authenticate</code>.
     * @param authType  the type of authenticator used (BASIC, CLIENT_CERT,
     *                  DIGEST or FORM)
     * @param username  the username (if any) used for the authentication
     * @param password  the password (if any) used for the authentication
     *
     * @return <code>true</code> if the credentials were updated, otherwise
     *         <code>false</code>
     */
    protected boolean update(String ssoId, Principal principal, String authType,
                          String username, String password) {

        SingleSignOnEntry sso = cache.get(ssoId);
        if (sso != null && !sso.getCanReauthenticate()) {
            if (containerLog.isDebugEnabled()) {
                containerLog.debug(sm.getString("singleSignOn.debug.update", ssoId, authType));
            }

            sso.updateCredentials(principal, authType, username, password);
            return true;
        }
        return false;
    }


    /**
     * Remove a single Session from a SingleSignOn.  Called when
     * a session is timed out and no longer active.
     *
     * @param ssoId Single sign on identifier from which to remove the session.
     * @param session the session to be removed.
     */
    protected void removeSession(String ssoId, Session session) {

        if (containerLog.isDebugEnabled()) {
            containerLog.debug(sm.getString("singleSignOn.debug.removeSession", session, ssoId));
        }

        // Get a reference to the SingleSignOn
        SingleSignOnEntry entry = cache.get(ssoId);
        if (entry == null) {
            return;
        }

        // Remove the inactive session from SingleSignOnEntry
        entry.removeSession(session);

        // If there are not sessions left in the SingleSignOnEntry,
        // deregister the entry.
        if (entry.findSessions().size() == 0) {
            deregister(ssoId);
        }
    }


    protected SessionListener getSessionListener(String ssoId) {
        return new SingleSignOnListener(ssoId);
    }


    @Override
    protected synchronized void startInternal() throws LifecycleException {
        Container c = getContainer();
        while (c != null && !(c instanceof Engine)) {
            c = c.getParent();
        }
        if (c instanceof Engine) {
            engine = (Engine) c;
        }
        super.startInternal();
    }


    @Override
    protected synchronized void stopInternal() throws LifecycleException {
        super.stopInternal();
        engine = null;
    }
}
