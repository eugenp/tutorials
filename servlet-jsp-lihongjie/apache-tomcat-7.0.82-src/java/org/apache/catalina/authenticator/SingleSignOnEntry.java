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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Session;

/**
 * A class that represents entries in the cache of authenticated users.
 * This is necessary to make it available to
 * <code>AuthenticatorBase</code> subclasses that need it in order to perform
 * reauthentications when SingleSignOn is in use.
 *
 * @author  B Stansberry, based on work by Craig R. McClanahan
 *
 * @see SingleSignOn
 * @see AuthenticatorBase#reauthenticateFromSSO
 */
public class SingleSignOnEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    // ------------------------------------------------------  Instance Fields

    protected String authType = null;

    protected String password = null;

    // Marked as transient so special handling can be applied to serialization
    protected transient Principal principal = null;

    protected ConcurrentMap<SingleSignOnSessionKey,SingleSignOnSessionKey> sessionKeys =
            new ConcurrentHashMap<SingleSignOnSessionKey, SingleSignOnSessionKey>();

    protected String username = null;

    protected boolean canReauthenticate = false;

    // ---------------------------------------------------------  Constructors

    /**
     * Creates a new SingleSignOnEntry
     *
     * @param principal the <code>Principal</code> returned by the latest
     *                  call to <code>Realm.authenticate</code>.
     * @param authType  the type of authenticator used (BASIC, CLIENT_CERT,
     *                  DIGEST or FORM)
     * @param username  the username (if any) used for the authentication
     * @param password  the password (if any) used for the authentication
     */
    public SingleSignOnEntry(Principal principal, String authType,
                             String username, String password) {

        updateCredentials(principal, authType, username, password);
    }

    // ------------------------------------------------------- Package Methods

    /**
     * Adds a <code>Session</code> to the list of those associated with
     * this SSO.
     *
     * @param sso       The <code>SingleSignOn</code> valve that is managing
     *                  the SSO session.
     * @param session   The <code>Session</code> being associated with the SSO.
     */
    public void addSession(SingleSignOn sso, String ssoId, Session session) {
        SingleSignOnSessionKey key = new SingleSignOnSessionKey(session);
        SingleSignOnSessionKey currentKey = sessionKeys.putIfAbsent(key, key);
        if (currentKey == null) {
            // Session not previously added
            session.addSessionListener(sso.getSessionListener(ssoId));
        }
    }

    /**
     * Removes the given <code>Session</code> from the list of those
     * associated with this SSO.
     *
     * @param session  the <code>Session</code> to remove.
     */
    public void removeSession(Session session) {
        SingleSignOnSessionKey key = new SingleSignOnSessionKey(session);
        sessionKeys.remove(key);
    }

    /**
     * Returns the HTTP Session identifiers associated with this SSO.
     *
     * @return The identifiers for the HTTP sessions that are current associated
     *         with this SSo entry
     */
    public Set<SingleSignOnSessionKey> findSessions() {
        return sessionKeys.keySet();
    }

    /**
     * Gets the name of the authentication type originally used to authenticate
     * the user associated with the SSO.
     *
     * @return "BASIC", "CLIENT_CERT", "DIGEST", "FORM" or "NONE"
     */
    public String getAuthType() {
        return this.authType;
    }

    /**
     * Gets whether the authentication type associated with the original
     * authentication supports reauthentication.
     *
     * @return  <code>true</code> if <code>getAuthType</code> returns
     *          "BASIC" or "FORM", <code>false</code> otherwise.
     */
    public boolean getCanReauthenticate() {
        return this.canReauthenticate;
    }

    /**
     * Gets the password credential (if any) associated with the SSO.
     *
     * @return  the password credential associated with the SSO, or
     *          <code>null</code> if the original authentication type
     *          does not involve a password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets the <code>Principal</code> that has been authenticated by the SSO.
     *
     * @return The Principal that was created by the authentication that
     *         triggered the creation of the SSO entry
     */
    public Principal getPrincipal() {
        return this.principal;
    }

    /**
     * Gets the user name provided by the user as part of the authentication
     * process.
     *
     * @return The user name that was authenticated as part of the
     *         authentication that triggered the creation of the SSO entry
     */
    public String getUsername() {
        return this.username;
    }


    /**
     * Updates the SingleSignOnEntry to reflect the latest security
     * information associated with the caller.
     *
     * @param principal the <code>Principal</code> returned by the latest
     *                  call to <code>Realm.authenticate</code>.
     * @param authType  the type of authenticator used (BASIC, CLIENT_CERT,
     *                  DIGEST or FORM)
     * @param username  the username (if any) used for the authentication
     * @param password  the password (if any) used for the authentication
     */
    public synchronized void updateCredentials(Principal principal, String authType,
                                  String username, String password) {
        this.principal = principal;
        this.authType = authType;
        this.username = username;
        this.password = password;
        this.canReauthenticate = (HttpServletRequest.BASIC_AUTH.equals(authType) ||
                HttpServletRequest.FORM_AUTH.equals(authType));
    }


    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if (principal instanceof Serializable) {
            out.writeBoolean(true);
            out.writeObject(principal);
        } else {
            out.writeBoolean(false);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        boolean hasPrincipal = in.readBoolean();
        if (hasPrincipal) {
            principal = (Principal) in.readObject();
        }
    }
}
