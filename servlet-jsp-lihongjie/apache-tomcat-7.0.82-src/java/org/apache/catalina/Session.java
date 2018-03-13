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


package org.apache.catalina;


import java.security.Principal;
import java.util.Iterator;

import javax.servlet.http.HttpSession;


/**
 * A <b>Session</b> is the Catalina-internal facade for an
 * <code>HttpSession</code> that is used to maintain state information
 * between requests for a particular user of a web application.
 *
 * @author Craig R. McClanahan
 */
public interface Session {


    // ----------------------------------------------------- Manifest Constants


    /**
     * The SessionEvent event type when a session is created.
     */
    public static final String SESSION_CREATED_EVENT = "createSession";


    /**
     * The SessionEvent event type when a session is destroyed.
     */
    public static final String SESSION_DESTROYED_EVENT = "destroySession";


    /**
     * The SessionEvent event type when a session is activated.
     */
    public static final String SESSION_ACTIVATED_EVENT = "activateSession";


    /**
     * The SessionEvent event type when a session is passivated.
     */
    public static final String SESSION_PASSIVATED_EVENT = "passivateSession";


    // ------------------------------------------------------------- Properties


    /**
     * Return the authentication type used to authenticate our cached
     * Principal, if any.
     */
    public String getAuthType();


    /**
     * Set the authentication type used to authenticate our cached
     * Principal, if any.
     *
     * @param authType The new cached authentication type
     */
    public void setAuthType(String authType);


    /**
     * Return the creation time for this session.
     */
    public long getCreationTime();


    /**
     * Return the creation time for this session, bypassing the session validity
     * checks.
     */
    public long getCreationTimeInternal();


    /**
     * Set the creation time for this session.  This method is called by the
     * Manager when an existing Session instance is reused.
     *
     * @param time The new creation time
     */
    public void setCreationTime(long time);


    /**
     * Return the session identifier for this session.
     */
    public String getId();


    /**
     * Return the session identifier for this session.
     */
    public String getIdInternal();


    /**
     * Set the session identifier for this session and notifies any associated
     * listeners that a new session has been created.
     *
     * @param id The new session identifier
     */
    public void setId(String id);


    /**
     * Set the session identifier for this session and optionally notifies any
     * associated listeners that a new session has been created.
     *
     * @param id        The new session identifier
     * @param notify    Should any associated listeners be notified that a new
     *                      session has been created?
     */
    public void setId(String id, boolean notify);


    /**
     * Return descriptive information about this Session implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo();


    /**
     * Return the last time the client sent a request associated with this
     * session, as the number of milliseconds since midnight, January 1, 1970
     * GMT.  Actions that your application takes, such as getting or setting
     * a value associated with the session, do not affect the access time.
     * This one gets updated whenever a request starts.
     */
    public long getThisAccessedTime();

    /**
     * Return the last client access time without invalidation check
     * @see #getThisAccessedTime()
     */
    public long getThisAccessedTimeInternal();

    /**
     * Return the last time the client sent a request associated with this
     * session, as the number of milliseconds since midnight, January 1, 1970
     * GMT.  Actions that your application takes, such as getting or setting
     * a value associated with the session, do not affect the access time.
     * This one gets updated whenever a request finishes.
     */
    public long getLastAccessedTime();

    /**
     * Return the last client access time without invalidation check
     * @see #getLastAccessedTime()
     */
    public long getLastAccessedTimeInternal();

    /**
     * Return the Manager within which this Session is valid.
     */
    public Manager getManager();


    /**
     * Set the Manager within which this Session is valid.
     *
     * @param manager The new Manager
     */
    public void setManager(Manager manager);


    /**
     * Return the maximum time interval, in seconds, between client requests
     * before the servlet container will invalidate the session.  A negative
     * time indicates that the session should never time out.
     */
    public int getMaxInactiveInterval();


    /**
     * Set the maximum time interval, in seconds, between client requests
     * before the servlet container will invalidate the session.  A negative
     * time indicates that the session should never time out.
     *
     * @param interval The new maximum interval
     */
    public void setMaxInactiveInterval(int interval);


    /**
     * Set the <code>isNew</code> flag for this session.
     *
     * @param isNew The new value for the <code>isNew</code> flag
     */
    public void setNew(boolean isNew);


    /**
     * Return the authenticated Principal that is associated with this Session.
     * This provides an <code>Authenticator</code> with a means to cache a
     * previously authenticated Principal, and avoid potentially expensive
     * <code>Realm.authenticate()</code> calls on every request.  If there
     * is no current associated Principal, return <code>null</code>.
     */
    public Principal getPrincipal();


    /**
     * Set the authenticated Principal that is associated with this Session.
     * This provides an <code>Authenticator</code> with a means to cache a
     * previously authenticated Principal, and avoid potentially expensive
     * <code>Realm.authenticate()</code> calls on every request.
     *
     * @param principal The new Principal, or <code>null</code> if none
     */
    public void setPrincipal(Principal principal);


    /**
     * Return the <code>HttpSession</code> for which this object
     * is the facade.
     */
    public HttpSession getSession();


    /**
     * Set the <code>isValid</code> flag for this session.
     *
     * @param isValid The new value for the <code>isValid</code> flag
     */
    public void setValid(boolean isValid);


    /**
     * Return the <code>isValid</code> flag for this session.
     */
    public boolean isValid();


    // --------------------------------------------------------- Public Methods


    /**
     * Update the accessed time information for this session.  This method
     * should be called by the context when a request comes in for a particular
     * session, even if the application does not reference it.
     */
    public void access();


    /**
     * Add a session event listener to this component.
     */
    public void addSessionListener(SessionListener listener);


    /**
     * End access to the session.
     */
    public void endAccess();


    /**
     * Perform the internal processing required to invalidate this session,
     * without triggering an exception if the session has already expired.
     */
    public void expire();


    /**
     * Return the object bound with the specified name to the internal notes
     * for this session, or <code>null</code> if no such binding exists.
     *
     * @param name Name of the note to be returned
     */
    public Object getNote(String name);


    /**
     * Return an Iterator containing the String names of all notes bindings
     * that exist for this session.
     */
    public Iterator<String> getNoteNames();


    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    public void recycle();


    /**
     * Remove any object bound to the specified name in the internal notes
     * for this session.
     *
     * @param name Name of the note to be removed
     */
    public void removeNote(String name);


    /**
     * Remove a session event listener from this component.
     */
    public void removeSessionListener(SessionListener listener);


    /**
     * Bind an object to a specified name in the internal notes associated
     * with this session, replacing any existing binding for this name.
     *
     * @param name Name to which the object should be bound
     * @param value Object to be bound to the specified name
     */
    public void setNote(String name, Object value);


    /**
     * Does the session implementation support the distributing of the given
     * attribute? If the Manager is marked as distributable, then this method
     * must be used to check attributes before adding them to a session and
     * an {@link IllegalArgumentException} thrown if the proposed attribute is
     * not distributable.
     * <p>
     * Note that the {@link Manager} implementation may further restrict which
     * attributes are distributed but a {@link Manager} level restriction should
     * not trigger an {@link IllegalArgumentException} in
     * {@link HttpSession#setAttribute(String, Object)}
     *
     * @param name  The attribute name
     * @param value The attribute value
     *
     * @return {@code true} if distribution is supported, otherwise {@code
     *         false}
     */
    public boolean isAttributeDistributable(String name, Object value);
}
