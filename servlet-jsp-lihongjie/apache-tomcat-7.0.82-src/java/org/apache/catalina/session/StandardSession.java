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
package org.apache.catalina.session;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.WriteAbortedException;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.SessionEvent;
import org.apache.catalina.SessionListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.security.SecurityUtil;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.util.security.PrivilegedSetTccl;

/**
 * Standard implementation of the <b>Session</b> interface.  This object is
 * serializable, so that it can be stored in persistent storage or transferred
 * to a different JVM for distributable session support.
 * <p>
 * <b>IMPLEMENTATION NOTE</b>:  An instance of this class represents both the
 * internal (Session) and application level (HttpSession) view of the session.
 * However, because the class itself is not declared public, Java logic outside
 * of the <code>org.apache.catalina.session</code> package cannot cast an
 * HttpSession view of this instance back to a Session view.
 * <p>
 * <b>IMPLEMENTATION NOTE</b>:  If you add fields to this class, you must
 * make sure that you carry them over in the read/writeObject methods so
 * that this class is properly serialized.
 *
 * @author Craig R. McClanahan
 * @author Sean Legassick
 * @author <a href="mailto:jon@latchkey.com">Jon S. Stevens</a>
 */
public class StandardSession implements HttpSession, Session, Serializable {

    private static final long serialVersionUID = 1L;

    protected static final boolean STRICT_SERVLET_COMPLIANCE;

    protected static final boolean ACTIVITY_CHECK;

    protected static final boolean LAST_ACCESS_AT_START;

    static {
        STRICT_SERVLET_COMPLIANCE = Globals.STRICT_SERVLET_COMPLIANCE;

        String activityCheck = System.getProperty(
                "org.apache.catalina.session.StandardSession.ACTIVITY_CHECK");
        if (activityCheck == null) {
            ACTIVITY_CHECK = STRICT_SERVLET_COMPLIANCE;
        } else {
            ACTIVITY_CHECK = Boolean.parseBoolean(activityCheck);
        }

        String lastAccessAtStart = System.getProperty(
                "org.apache.catalina.session.StandardSession.LAST_ACCESS_AT_START");
        if (lastAccessAtStart == null) {
            LAST_ACCESS_AT_START = STRICT_SERVLET_COMPLIANCE;
        } else {
            LAST_ACCESS_AT_START = Boolean.parseBoolean(lastAccessAtStart);
        }
    }


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new Session associated with the specified Manager.
     *
     * @param manager The manager with which this Session is associated
     */
    public StandardSession(Manager manager) {

        super();
        this.manager = manager;

        // Initialize access count
        if (ACTIVITY_CHECK) {
            accessCount = new AtomicInteger();
        }

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Type array.
     */
    protected static final String EMPTY_ARRAY[] = new String[0];


    /**
     * The collection of user data attributes associated with this Session.
     */
    protected ConcurrentMap<String, Object> attributes = new ConcurrentHashMap<String, Object>();


    /**
     * The authentication type used to authenticate our cached Principal,
     * if any.  NOTE:  This value is not included in the serialized
     * version of this object.
     */
    protected transient String authType = null;


    /**
     * The time this session was created, in milliseconds since midnight,
     * January 1, 1970 GMT.
     */
    protected long creationTime = 0L;


    /**
     * Set of attribute names which are not allowed to be persisted.
     *
     * @deprecated Use {@link Constants#excludedAttributeNames} instead. Will be
     *             removed in Tomcat 9.
     */
    @Deprecated
    protected static final String[] excludedAttributes = {
        Globals.SUBJECT_ATTR,
        Globals.GSS_CREDENTIAL_ATTR
    };


    /**
     * We are currently processing a session expiration, so bypass
     * certain IllegalStateException tests.  NOTE:  This value is not
     * included in the serialized version of this object.
     */
    protected transient volatile boolean expiring = false;


    /**
     * The facade associated with this session.  NOTE:  This value is not
     * included in the serialized version of this object.
     */
    protected transient StandardSessionFacade facade = null;


    /**
     * The session identifier of this Session.
     */
    protected String id = null;


    /**
     * Descriptive information describing this Session implementation.
     */
    protected static final String info = "StandardSession/1.0";


    /**
     * The last accessed time for this Session.
     */
    protected volatile long lastAccessedTime = creationTime;


    /**
     * The session event listeners for this Session.
     */
    protected transient ArrayList<SessionListener> listeners =
        new ArrayList<SessionListener>();


    /**
     * The Manager with which this Session is associated.
     */
    protected transient Manager manager = null;


    /**
     * The maximum time interval, in seconds, between client requests before
     * the servlet container may invalidate this session.  A negative time
     * indicates that the session should never time out.
     */
    protected int maxInactiveInterval = -1;


    /**
     * Flag indicating whether this session is new or not.
     */
    protected boolean isNew = false;


    /**
     * Flag indicating whether this session is valid or not.
     */
    protected volatile boolean isValid = false;


    /**
     * Internal notes associated with this session by Catalina components
     * and event listeners.  <b>IMPLEMENTATION NOTE:</b> This object is
     * <em>not</em> saved and restored across session serializations!
     */
    protected transient Map<String, Object> notes = new Hashtable<String, Object>();


    /**
     * The authenticated Principal associated with this session, if any.
     * <b>IMPLEMENTATION NOTE:</b>  This object is <i>not</i> saved and
     * restored across session serializations!
     */
    protected transient Principal principal = null;


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm = StringManager.getManager(StandardSession.class);


    /**
     * The HTTP session context associated with this session.
     */
    @Deprecated
    protected static volatile
            javax.servlet.http.HttpSessionContext sessionContext = null;


    /**
     * The property change support for this component.  NOTE:  This value
     * is not included in the serialized version of this object.
     */
    protected transient PropertyChangeSupport support =
        new PropertyChangeSupport(this);


    /**
     * The current accessed time for this session.
     */
    protected volatile long thisAccessedTime = creationTime;


    /**
     * The access count for this session.
     */
    protected transient AtomicInteger accessCount = null;


    // ----------------------------------------------------- Session Properties


    /**
     * Return the authentication type used to authenticate our cached
     * Principal, if any.
     */
    @Override
    public String getAuthType() {

        return (this.authType);

    }


    /**
     * Set the authentication type used to authenticate our cached
     * Principal, if any.
     *
     * @param authType The new cached authentication type
     */
    @Override
    public void setAuthType(String authType) {

        String oldAuthType = this.authType;
        this.authType = authType;
        support.firePropertyChange("authType", oldAuthType, this.authType);

    }


    /**
     * Set the creation time for this session.  This method is called by the
     * Manager when an existing Session instance is reused.
     *
     * @param time The new creation time
     */
    @Override
    public void setCreationTime(long time) {

        this.creationTime = time;
        this.lastAccessedTime = time;
        this.thisAccessedTime = time;

    }


    /**
     * Return the session identifier for this session.
     */
    @Override
    public String getId() {

        return (this.id);

    }


    /**
     * Return the session identifier for this session.
     */
    @Override
    public String getIdInternal() {

        return (this.id);

    }


    /**
     * Set the session identifier for this session.
     *
     * @param id The new session identifier
     */
    @Override
    public void setId(String id) {
        setId(id, true);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(String id, boolean notify) {

        if ((this.id != null) && (manager != null))
            manager.remove(this);

        this.id = id;

        if (manager != null)
            manager.add(this);

        if (notify) {
            tellNew();
        }
    }


    /**
     * Inform the listeners about the new session.
     *
     */
    public void tellNew() {

        // Notify interested session event listeners
        fireSessionEvent(Session.SESSION_CREATED_EVENT, null);

        // Notify interested application event listeners
        Context context = (Context) manager.getContainer();
        Object listeners[] = context.getApplicationLifecycleListeners();
        if (listeners != null) {
            HttpSessionEvent event =
                new HttpSessionEvent(getSession());
            for (int i = 0; i < listeners.length; i++) {
                if (!(listeners[i] instanceof HttpSessionListener))
                    continue;
                HttpSessionListener listener =
                    (HttpSessionListener) listeners[i];
                try {
                    context.fireContainerEvent("beforeSessionCreated",
                            listener);
                    listener.sessionCreated(event);
                    context.fireContainerEvent("afterSessionCreated", listener);
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    try {
                        context.fireContainerEvent("afterSessionCreated",
                                listener);
                    } catch (Exception e) {
                        // Ignore
                    }
                    manager.getContainer().getLogger().error
                        (sm.getString("standardSession.sessionEvent"), t);
                }
            }
        }

    }


    /**
     * Return descriptive information about this Session implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    @Override
    public String getInfo() {

        return (info);

    }


    /**
     * Return the last time the client sent a request associated with this
     * session, as the number of milliseconds since midnight, January 1, 1970
     * GMT.  Actions that your application takes, such as getting or setting
     * a value associated with the session, do not affect the access time.
     * This one gets updated whenever a request starts.
     */
    @Override
    public long getThisAccessedTime() {

        if (!isValidInternal()) {
            throw new IllegalStateException
                (sm.getString("standardSession.getThisAccessedTime.ise"));
        }

        return (this.thisAccessedTime);
    }

    /**
     * Return the last client access time without invalidation check
     * @see #getThisAccessedTime()
     */
    @Override
    public long getThisAccessedTimeInternal() {
        return (this.thisAccessedTime);
    }

    /**
     * Return the last time the client sent a request associated with this
     * session, as the number of milliseconds since midnight, January 1, 1970
     * GMT.  Actions that your application takes, such as getting or setting
     * a value associated with the session, do not affect the access time.
     * This one gets updated whenever a request finishes.
     */
    @Override
    public long getLastAccessedTime() {

        if (!isValidInternal()) {
            throw new IllegalStateException
                (sm.getString("standardSession.getLastAccessedTime.ise"));
        }

        return (this.lastAccessedTime);
    }

    /**
     * Return the last client access time without invalidation check
     * @see #getLastAccessedTime()
     */
    @Override
    public long getLastAccessedTimeInternal() {
        return (this.lastAccessedTime);
    }

    /**
     * Return the Manager within which this Session is valid.
     */
    @Override
    public Manager getManager() {
        return this.manager;
    }


    /**
     * Set the Manager within which this Session is valid.
     *
     * @param manager The new Manager
     */
    @Override
    public void setManager(Manager manager) {
        this.manager = manager;
    }


    /**
     * Return the maximum time interval, in seconds, between client requests
     * before the servlet container will invalidate the session.  A negative
     * time indicates that the session should never time out.
     */
    @Override
    public int getMaxInactiveInterval() {

        return (this.maxInactiveInterval);

    }


    /**
     * Set the maximum time interval, in seconds, between client requests
     * before the servlet container will invalidate the session.  A zero or
     * negative time indicates that the session should never time out.
     *
     * @param interval The new maximum interval
     */
    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }


    /**
     * Set the <code>isNew</code> flag for this session.
     *
     * @param isNew The new value for the <code>isNew</code> flag
     */
    @Override
    public void setNew(boolean isNew) {

        this.isNew = isNew;

    }


    /**
     * Return the authenticated Principal that is associated with this Session.
     * This provides an <code>Authenticator</code> with a means to cache a
     * previously authenticated Principal, and avoid potentially expensive
     * <code>Realm.authenticate()</code> calls on every request.  If there
     * is no current associated Principal, return <code>null</code>.
     */
    @Override
    public Principal getPrincipal() {

        return (this.principal);

    }


    /**
     * Set the authenticated Principal that is associated with this Session.
     * This provides an <code>Authenticator</code> with a means to cache a
     * previously authenticated Principal, and avoid potentially expensive
     * <code>Realm.authenticate()</code> calls on every request.
     *
     * @param principal The new Principal, or <code>null</code> if none
     */
    @Override
    public void setPrincipal(Principal principal) {

        Principal oldPrincipal = this.principal;
        this.principal = principal;
        support.firePropertyChange("principal", oldPrincipal, this.principal);

    }


    /**
     * Return the <code>HttpSession</code> for which this object
     * is the facade.
     */
    @Override
    public HttpSession getSession() {

        if (facade == null){
            if (SecurityUtil.isPackageProtectionEnabled()){
                final StandardSession fsession = this;
                facade = AccessController.doPrivileged(
                        new PrivilegedAction<StandardSessionFacade>(){
                    @Override
                    public StandardSessionFacade run(){
                        return new StandardSessionFacade(fsession);
                    }
                });
            } else {
                facade = new StandardSessionFacade(this);
            }
        }
        return (facade);

    }


    /**
     * Return the <code>isValid</code> flag for this session.
     */
    @Override
    public boolean isValid() {

        if (!this.isValid) {
            return false;
        }

        if (this.expiring) {
            return true;
        }

        if (ACTIVITY_CHECK && accessCount.get() > 0) {
            return true;
        }

        if (maxInactiveInterval > 0) {
            long timeNow = System.currentTimeMillis();
            int timeIdle;
            if (LAST_ACCESS_AT_START) {
                timeIdle = (int) ((timeNow - lastAccessedTime) / 1000L);
            } else {
                timeIdle = (int) ((timeNow - thisAccessedTime) / 1000L);
            }
            if (timeIdle >= maxInactiveInterval) {
                expire(true);
            }
        }

        return this.isValid;
    }


    /**
     * Set the <code>isValid</code> flag for this session.
     *
     * @param isValid The new value for the <code>isValid</code> flag
     */
    @Override
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }


    // ------------------------------------------------- Session Public Methods


    /**
     * Update the accessed time information for this session.  This method
     * should be called by the context when a request comes in for a particular
     * session, even if the application does not reference it.
     */
    @Override
    public void access() {

        this.thisAccessedTime = System.currentTimeMillis();

        if (ACTIVITY_CHECK) {
            accessCount.incrementAndGet();
        }

    }


    /**
     * End the access.
     */
    @Override
    public void endAccess() {

        isNew = false;

        /**
         * The servlet spec mandates to ignore request handling time
         * in lastAccessedTime.
         */
        if (LAST_ACCESS_AT_START) {
            this.lastAccessedTime = this.thisAccessedTime;
            this.thisAccessedTime = System.currentTimeMillis();
        } else {
            this.thisAccessedTime = System.currentTimeMillis();
            this.lastAccessedTime = this.thisAccessedTime;
        }

        if (ACTIVITY_CHECK) {
            accessCount.decrementAndGet();
        }

    }


    /**
     * Add a session event listener to this component.
     */
    @Override
    public void addSessionListener(SessionListener listener) {

        listeners.add(listener);

    }


    /**
     * Perform the internal processing required to invalidate this session,
     * without triggering an exception if the session has already expired.
     */
    @Override
    public void expire() {

        expire(true);

    }


    /**
     * Perform the internal processing required to invalidate this session,
     * without triggering an exception if the session has already expired.
     *
     * @param notify Should we notify listeners about the demise of
     *  this session?
     */
    public void expire(boolean notify) {

        // Check to see if session has already been invalidated.
        // Do not check expiring at this point as expire should not return until
        // isValid is false
        if (!isValid)
            return;

        synchronized (this) {
            // Check again, now we are inside the sync so this code only runs once
            // Double check locking - isValid needs to be volatile
            // The check of expiring is to ensure that an infinite loop is not
            // entered as per bug 56339
            if (expiring || !isValid)
                return;

            if (manager == null)
                return;

            // Mark this session as "being expired"
            expiring = true;

            // Notify interested application event listeners
            // FIXME - Assumes we call listeners in reverse order
            Context context = (Context) manager.getContainer();

            // The call to expire() may not have been triggered by the webapp.
            // Make sure the webapp's class loader is set when calling the
            // listeners
            ClassLoader oldTccl = null;
            if (context.getLoader() != null &&
                    context.getLoader().getClassLoader() != null) {
                oldTccl = Thread.currentThread().getContextClassLoader();
                if (Globals.IS_SECURITY_ENABLED) {
                    PrivilegedAction<Void> pa = new PrivilegedSetTccl(
                            context.getLoader().getClassLoader());
                    AccessController.doPrivileged(pa);
                } else {
                    Thread.currentThread().setContextClassLoader(
                            context.getLoader().getClassLoader());
                }
            }
            try {
                Object listeners[] = context.getApplicationLifecycleListeners();
                if (notify && (listeners != null)) {
                    HttpSessionEvent event =
                        new HttpSessionEvent(getSession());
                    for (int i = 0; i < listeners.length; i++) {
                        int j = (listeners.length - 1) - i;
                        if (!(listeners[j] instanceof HttpSessionListener))
                            continue;
                        HttpSessionListener listener =
                            (HttpSessionListener) listeners[j];
                        try {
                            context.fireContainerEvent("beforeSessionDestroyed",
                                    listener);
                            listener.sessionDestroyed(event);
                            context.fireContainerEvent("afterSessionDestroyed",
                                    listener);
                        } catch (Throwable t) {
                            ExceptionUtils.handleThrowable(t);
                            try {
                                context.fireContainerEvent(
                                        "afterSessionDestroyed", listener);
                            } catch (Exception e) {
                                // Ignore
                            }
                            manager.getContainer().getLogger().error
                                (sm.getString("standardSession.sessionEvent"), t);
                        }
                    }
                }
            } finally {
                if (oldTccl != null) {
                    if (Globals.IS_SECURITY_ENABLED) {
                        PrivilegedAction<Void> pa =
                            new PrivilegedSetTccl(oldTccl);
                        AccessController.doPrivileged(pa);
                    } else {
                        Thread.currentThread().setContextClassLoader(oldTccl);
                    }
                }
            }

            if (ACTIVITY_CHECK) {
                accessCount.set(0);
            }

            // Remove this session from our manager's active sessions
            manager.remove(this, true);

            // Notify interested session event listeners
            if (notify) {
                fireSessionEvent(Session.SESSION_DESTROYED_EVENT, null);
            }

            // Call the logout method
            if (principal instanceof GenericPrincipal) {
                GenericPrincipal gp = (GenericPrincipal) principal;
                try {
                    gp.logout();
                } catch (Exception e) {
                    manager.getContainer().getLogger().error(
                            sm.getString("standardSession.logoutfail"),
                            e);
                }
            }

            // We have completed expire of this session
            setValid(false);
            expiring = false;

            // Unbind any objects associated with this session
            String keys[] = keys();
            if (oldTccl != null) {
                if (Globals.IS_SECURITY_ENABLED) {
                    PrivilegedAction<Void> pa = new PrivilegedSetTccl(
                            context.getLoader().getClassLoader());
                    AccessController.doPrivileged(pa);
                } else {
                    Thread.currentThread().setContextClassLoader(
                            context.getLoader().getClassLoader());
                }
            }
            try {
                for (int i = 0; i < keys.length; i++) {
                    removeAttributeInternal(keys[i], notify);
                }
            } finally {
                if (oldTccl != null) {
                    if (Globals.IS_SECURITY_ENABLED) {
                        PrivilegedAction<Void> pa =
                            new PrivilegedSetTccl(oldTccl);
                        AccessController.doPrivileged(pa);
                    } else {
                        Thread.currentThread().setContextClassLoader(oldTccl);
                    }
                }
            }
        }
    }


    /**
     * Perform the internal processing required to passivate
     * this session.
     */
    public void passivate() {

        // Notify interested session event listeners
        fireSessionEvent(Session.SESSION_PASSIVATED_EVENT, null);

        // Notify ActivationListeners
        HttpSessionEvent event = null;
        String keys[] = keys();
        for (int i = 0; i < keys.length; i++) {
            Object attribute = attributes.get(keys[i]);
            if (attribute instanceof HttpSessionActivationListener) {
                if (event == null)
                    event = new HttpSessionEvent(getSession());
                try {
                    ((HttpSessionActivationListener)attribute)
                        .sessionWillPassivate(event);
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    manager.getContainer().getLogger().error
                        (sm.getString("standardSession.attributeEvent"), t);
                }
            }
        }

    }


    /**
     * Perform internal processing required to activate this
     * session.
     */
    public void activate() {

        // Initialize access count
        if (ACTIVITY_CHECK) {
            accessCount = new AtomicInteger();
        }

        // Notify interested session event listeners
        fireSessionEvent(Session.SESSION_ACTIVATED_EVENT, null);

        // Notify ActivationListeners
        HttpSessionEvent event = null;
        String keys[] = keys();
        for (int i = 0; i < keys.length; i++) {
            Object attribute = attributes.get(keys[i]);
            if (attribute instanceof HttpSessionActivationListener) {
                if (event == null)
                    event = new HttpSessionEvent(getSession());
                try {
                    ((HttpSessionActivationListener)attribute)
                        .sessionDidActivate(event);
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    manager.getContainer().getLogger().error
                        (sm.getString("standardSession.attributeEvent"), t);
                }
            }
        }

    }


    /**
     * Return the object bound with the specified name to the internal notes
     * for this session, or <code>null</code> if no such binding exists.
     *
     * @param name Name of the note to be returned
     */
    @Override
    public Object getNote(String name) {

        return (notes.get(name));

    }


    /**
     * Return an Iterator containing the String names of all notes bindings
     * that exist for this session.
     */
    @Override
    public Iterator<String> getNoteNames() {

        return (notes.keySet().iterator());

    }


    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    @Override
    public void recycle() {

        // Reset the instance variables associated with this Session
        attributes.clear();
        setAuthType(null);
        creationTime = 0L;
        expiring = false;
        id = null;
        lastAccessedTime = 0L;
        maxInactiveInterval = -1;
        notes.clear();
        setPrincipal(null);
        isNew = false;
        isValid = false;
        manager = null;

    }


    /**
     * Remove any object bound to the specified name in the internal notes
     * for this session.
     *
     * @param name Name of the note to be removed
     */
    @Override
    public void removeNote(String name) {

        notes.remove(name);

    }


    /**
     * Remove a session event listener from this component.
     */
    @Override
    public void removeSessionListener(SessionListener listener) {

        listeners.remove(listener);

    }


    /**
     * Bind an object to a specified name in the internal notes associated
     * with this session, replacing any existing binding for this name.
     *
     * @param name Name to which the object should be bound
     * @param value Object to be bound to the specified name
     */
    @Override
    public void setNote(String name, Object value) {

        notes.put(name, value);

    }


    /**
     * Return a string representation of this object.
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("StandardSession[");
        sb.append(id);
        sb.append("]");
        return (sb.toString());

    }


    // ------------------------------------------------ Session Package Methods


    /**
     * Read a serialized version of the contents of this session object from
     * the specified object input stream, without requiring that the
     * StandardSession itself have been serialized.
     *
     * @param stream The object input stream to read from
     *
     * @exception ClassNotFoundException if an unknown class is specified
     * @exception IOException if an input/output error occurs
     */
    public void readObjectData(ObjectInputStream stream)
        throws ClassNotFoundException, IOException {

        readObject(stream);

    }


    /**
     * Write a serialized version of the contents of this session object to
     * the specified object output stream, without requiring that the
     * StandardSession itself have been serialized.
     *
     * @param stream The object output stream to write to
     *
     * @exception IOException if an input/output error occurs
     */
    public void writeObjectData(ObjectOutputStream stream)
        throws IOException {

        writeObject(stream);

    }


    // ------------------------------------------------- HttpSession Properties


    /**
     * Return the time when this session was created, in milliseconds since
     * midnight, January 1, 1970 GMT.
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     */
    @Override
    public long getCreationTime() {

        if (!isValidInternal())
            throw new IllegalStateException
                (sm.getString("standardSession.getCreationTime.ise"));

        return (this.creationTime);

    }


    /**
     * Return the time when this session was created, in milliseconds since
     * midnight, January 1, 1970 GMT, bypassing the session validation checks.
     */
    @Override
    public long getCreationTimeInternal() {
        return this.creationTime;
    }


    /**
     * Return the ServletContext to which this session belongs.
     */
    @Override
    public ServletContext getServletContext() {
        if (manager == null) {
            return null;
        }
        Context context = (Context) manager.getContainer();
        return context.getServletContext();
    }


    /**
     * Return the session context with which this session is associated.
     *
     * @deprecated As of Version 2.1, this method is deprecated and has no
     *  replacement.  It will be removed in a future version of the
     *  Java Servlet API.
     */
    @Override
    @Deprecated
    public javax.servlet.http.HttpSessionContext getSessionContext() {

        if (sessionContext == null)
            sessionContext = new StandardSessionContext();
        return (sessionContext);

    }


    // ----------------------------------------------HttpSession Public Methods


    /**
     * Return the object bound with the specified name in this session, or
     * <code>null</code> if no object is bound with that name.
     *
     * @param name Name of the attribute to be returned
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     */
    @Override
    public Object getAttribute(String name) {

        if (!isValidInternal())
            throw new IllegalStateException
                (sm.getString("standardSession.getAttribute.ise"));

        if (name == null) return null;

        return (attributes.get(name));

    }


    /**
     * Return an <code>Enumeration</code> of <code>String</code> objects
     * containing the names of the objects bound to this session.
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     */
    @Override
    public Enumeration<String> getAttributeNames() {

        if (!isValidInternal())
            throw new IllegalStateException
                (sm.getString("standardSession.getAttributeNames.ise"));

        Set<String> names = new HashSet<String>();
        names.addAll(attributes.keySet());
        return Collections.enumeration(names);
    }


    /**
     * Return the object bound with the specified name in this session, or
     * <code>null</code> if no object is bound with that name.
     *
     * @param name Name of the value to be returned
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     *
     * @deprecated As of Version 2.2, this method is replaced by
     *  <code>getAttribute()</code>
     */
    @Override
    @Deprecated
    public Object getValue(String name) {

        return (getAttribute(name));

    }


    /**
     * Return the set of names of objects bound to this session.  If there
     * are no such objects, a zero-length array is returned.
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     *
     * @deprecated As of Version 2.2, this method is replaced by
     *  <code>getAttributeNames()</code>
     */
    @Override
    @Deprecated
    public String[] getValueNames() {

        if (!isValidInternal())
            throw new IllegalStateException
                (sm.getString("standardSession.getValueNames.ise"));

        return (keys());

    }


    /**
     * Invalidates this session and unbinds any objects bound to it.
     *
     * @exception IllegalStateException if this method is called on
     *  an invalidated session
     */
    @Override
    public void invalidate() {

        if (!isValidInternal())
            throw new IllegalStateException
                (sm.getString("standardSession.invalidate.ise"));

        // Cause this session to expire
        expire();

    }


    /**
     * Return <code>true</code> if the client does not yet know about the
     * session, or if the client chooses not to join the session.  For
     * example, if the server used only cookie-based sessions, and the client
     * has disabled the use of cookies, then a session would be new on each
     * request.
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     */
    @Override
    public boolean isNew() {

        if (!isValidInternal())
            throw new IllegalStateException
                (sm.getString("standardSession.isNew.ise"));

        return (this.isNew);

    }


    /**
     * Bind an object to this session, using the specified name.  If an object
     * of the same name is already bound to this session, the object is
     * replaced.
     * <p>
     * After this method executes, and if the object implements
     * <code>HttpSessionBindingListener</code>, the container calls
     * <code>valueBound()</code> on the object.
     *
     * @param name Name to which the object is bound, cannot be null
     * @param value Object to be bound, cannot be null
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     *
     * @deprecated As of Version 2.2, this method is replaced by
     *  <code>setAttribute()</code>
     */
    @Override
    @Deprecated
    public void putValue(String name, Object value) {

        setAttribute(name, value);

    }


    /**
     * Remove the object bound with the specified name from this session.  If
     * the session does not have an object bound with this name, this method
     * does nothing.
     * <p>
     * After this method executes, and if the object implements
     * <code>HttpSessionBindingListener</code>, the container calls
     * <code>valueUnbound()</code> on the object.
     *
     * @param name Name of the object to remove from this session.
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     */
    @Override
    public void removeAttribute(String name) {

        removeAttribute(name, true);

    }


    /**
     * Remove the object bound with the specified name from this session.  If
     * the session does not have an object bound with this name, this method
     * does nothing.
     * <p>
     * After this method executes, and if the object implements
     * <code>HttpSessionBindingListener</code>, the container calls
     * <code>valueUnbound()</code> on the object.
     *
     * @param name Name of the object to remove from this session.
     * @param notify Should we notify interested listeners that this
     *  attribute is being removed?
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     */
    public void removeAttribute(String name, boolean notify) {

        // Validate our current state
        if (!isValidInternal())
            throw new IllegalStateException
                (sm.getString("standardSession.removeAttribute.ise"));

        removeAttributeInternal(name, notify);

    }


    /**
     * Remove the object bound with the specified name from this session.  If
     * the session does not have an object bound with this name, this method
     * does nothing.
     * <p>
     * After this method executes, and if the object implements
     * <code>HttpSessionBindingListener</code>, the container calls
     * <code>valueUnbound()</code> on the object.
     *
     * @param name Name of the object to remove from this session.
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     *
     * @deprecated As of Version 2.2, this method is replaced by
     *  <code>removeAttribute()</code>
     */
    @Override
    @Deprecated
    public void removeValue(String name) {

        removeAttribute(name);

    }


    /**
     * Bind an object to this session, using the specified name.  If an object
     * of the same name is already bound to this session, the object is
     * replaced.
     * <p>
     * After this method executes, and if the object implements
     * <code>HttpSessionBindingListener</code>, the container calls
     * <code>valueBound()</code> on the object.
     *
     * @param name Name to which the object is bound, cannot be null
     * @param value Object to be bound, cannot be null
     *
     * @exception IllegalArgumentException if an attempt is made to add a
     *  non-serializable object in an environment marked distributable.
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     */
    @Override
    public void setAttribute(String name, Object value) {
        setAttribute(name,value,true);
    }
    /**
     * Bind an object to this session, using the specified name.  If an object
     * of the same name is already bound to this session, the object is
     * replaced.
     * <p>
     * After this method executes, and if the object implements
     * <code>HttpSessionBindingListener</code>, the container calls
     * <code>valueBound()</code> on the object.
     *
     * @param name Name to which the object is bound, cannot be null
     * @param value Object to be bound, cannot be null
     * @param notify whether to notify session listeners
     * @exception IllegalArgumentException if an attempt is made to add a
     *  non-serializable object in an environment marked distributable.
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     */

    public void setAttribute(String name, Object value, boolean notify) {

        // Name cannot be null
        if (name == null)
            throw new IllegalArgumentException
                (sm.getString("standardSession.setAttribute.namenull"));

        // Null value is the same as removeAttribute()
        if (value == null) {
            removeAttribute(name);
            return;
        }

        // Validate our current state
        if (!isValidInternal()) {
            throw new IllegalStateException(sm.getString(
                    "standardSession.setAttribute.ise", getIdInternal()));
        }
        if ((manager != null) && ((Context) manager.getContainer()).getDistributable() &&
                !isAttributeDistributable(name, value) && !exclude(name, value)) {
            throw new IllegalArgumentException(sm.getString(
                    "standardSession.setAttribute.iae", name));
        }
        // Construct an event with the new value
        HttpSessionBindingEvent event = null;

        // Call the valueBound() method if necessary
        if (notify && value instanceof HttpSessionBindingListener) {
            // Don't call any notification if replacing with the same value
            Object oldValue = attributes.get(name);
            if (value != oldValue) {
                event = new HttpSessionBindingEvent(getSession(), name, value);
                try {
                    ((HttpSessionBindingListener) value).valueBound(event);
                } catch (Throwable t){
                    manager.getContainer().getLogger().error
                    (sm.getString("standardSession.bindingEvent"), t);
                }
            }
        }

        // Replace or add this attribute
        Object unbound = attributes.put(name, value);

        // Call the valueUnbound() method if necessary
        if (notify && (unbound != null) && (unbound != value) &&
            (unbound instanceof HttpSessionBindingListener)) {
            try {
                ((HttpSessionBindingListener) unbound).valueUnbound
                    (new HttpSessionBindingEvent(getSession(), name));
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                manager.getContainer().getLogger().error
                    (sm.getString("standardSession.bindingEvent"), t);
            }
        }

        if ( !notify ) return;

        // Notify interested application event listeners
        Context context = (Context) manager.getContainer();
        Object listeners[] = context.getApplicationEventListeners();
        if (listeners == null)
            return;
        for (int i = 0; i < listeners.length; i++) {
            if (!(listeners[i] instanceof HttpSessionAttributeListener))
                continue;
            HttpSessionAttributeListener listener =
                (HttpSessionAttributeListener) listeners[i];
            try {
                if (unbound != null) {
                    context.fireContainerEvent("beforeSessionAttributeReplaced",
                            listener);
                    if (event == null) {
                        event = new HttpSessionBindingEvent
                            (getSession(), name, unbound);
                    }
                    listener.attributeReplaced(event);
                    context.fireContainerEvent("afterSessionAttributeReplaced",
                            listener);
                } else {
                    context.fireContainerEvent("beforeSessionAttributeAdded",
                            listener);
                    if (event == null) {
                        event = new HttpSessionBindingEvent
                            (getSession(), name, value);
                    }
                    listener.attributeAdded(event);
                    context.fireContainerEvent("afterSessionAttributeAdded",
                            listener);
                }
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                try {
                    if (unbound != null) {
                        context.fireContainerEvent(
                                "afterSessionAttributeReplaced", listener);
                    } else {
                        context.fireContainerEvent("afterSessionAttributeAdded",
                                listener);
                    }
                } catch (Exception e) {
                    // Ignore
                }
                manager.getContainer().getLogger().error
                    (sm.getString("standardSession.attributeEvent"), t);
            }
        }

    }


    // ------------------------------------------ HttpSession Protected Methods


    /**
     * Return the <code>isValid</code> flag for this session without any expiration
     * check.
     */
    protected boolean isValidInternal() {
        return this.isValid;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation simply checks the value for serializability.
     * Sub-classes might use other distribution technology not based on
     * serialization and can override this check.
     */
    @Override
    public boolean isAttributeDistributable(String name, Object value) {
        return value instanceof Serializable;
    }


    /**
     * Read a serialized version of this session object from the specified
     * object input stream.
     * <p>
     * <b>IMPLEMENTATION NOTE</b>:  The reference to the owning Manager
     * is not restored by this method, and must be set explicitly.
     *
     * @param stream The input stream to read from
     *
     * @exception ClassNotFoundException if an unknown class is specified
     * @exception IOException if an input/output error occurs
     */
    protected void readObject(ObjectInputStream stream)
        throws ClassNotFoundException, IOException {

        // Deserialize the scalar instance variables (except Manager)
        authType = null;        // Transient only
        creationTime = ((Long) stream.readObject()).longValue();
        lastAccessedTime = ((Long) stream.readObject()).longValue();
        maxInactiveInterval = ((Integer) stream.readObject()).intValue();
        isNew = ((Boolean) stream.readObject()).booleanValue();
        isValid = ((Boolean) stream.readObject()).booleanValue();
        thisAccessedTime = ((Long) stream.readObject()).longValue();
        principal = null;        // Transient only
        //        setId((String) stream.readObject());
        id = (String) stream.readObject();
        if (manager.getContainer().getLogger().isDebugEnabled())
            manager.getContainer().getLogger().debug
                ("readObject() loading session " + id);

        // Deserialize the attribute count and attribute values
        if (attributes == null)
            attributes = new ConcurrentHashMap<String, Object>();
        int n = ((Integer) stream.readObject()).intValue();
        boolean isValidSave = isValid;
        isValid = true;
        for (int i = 0; i < n; i++) {
            String name = (String) stream.readObject();
            final Object value;
            try {
                value = stream.readObject();
            } catch (WriteAbortedException wae) {
                if (wae.getCause() instanceof NotSerializableException) {
                    String msg = sm.getString("standardSession.notDeserializable", name, id);
                    if (manager.getContainer().getLogger().isDebugEnabled()) {
                        manager.getContainer().getLogger().debug(msg, wae);
                    } else {
                        manager.getContainer().getLogger().warn(msg);
                    }
                    // Skip non serializable attributes
                    continue;
                }
                throw wae;
            }
            if (manager.getContainer().getLogger().isDebugEnabled())
                manager.getContainer().getLogger().debug("  loading attribute '" + name +
                    "' with value '" + value + "'");
            // Handle the case where the filter configuration was changed while
            // the web application was stopped.
            if (exclude(name, value)) {
                continue;
            }
            attributes.put(name, value);
        }
        isValid = isValidSave;

        if (listeners == null) {
            listeners = new ArrayList<SessionListener>();
        }

        if (notes == null) {
            notes = new Hashtable<String, Object>();
        }
    }


    /**
     * Write a serialized version of this session object to the specified
     * object output stream.
     * <p>
     * <b>IMPLEMENTATION NOTE</b>:  The owning Manager will not be stored
     * in the serialized representation of this Session.  After calling
     * <code>readObject()</code>, you must set the associated Manager
     * explicitly.
     * <p>
     * <b>IMPLEMENTATION NOTE</b>:  Any attribute that is not Serializable
     * will be unbound from the session, with appropriate actions if it
     * implements HttpSessionBindingListener.  If you do not want any such
     * attributes, be sure the <code>distributable</code> property of the
     * associated Manager is set to <code>true</code>.
     *
     * @param stream The output stream to write to
     *
     * @exception IOException if an input/output error occurs
     */
    protected void writeObject(ObjectOutputStream stream) throws IOException {

        // Write the scalar instance variables (except Manager)
        stream.writeObject(Long.valueOf(creationTime));
        stream.writeObject(Long.valueOf(lastAccessedTime));
        stream.writeObject(Integer.valueOf(maxInactiveInterval));
        stream.writeObject(Boolean.valueOf(isNew));
        stream.writeObject(Boolean.valueOf(isValid));
        stream.writeObject(Long.valueOf(thisAccessedTime));
        stream.writeObject(id);
        if (manager.getContainer().getLogger().isDebugEnabled())
            manager.getContainer().getLogger().debug
                ("writeObject() storing session " + id);

        // Accumulate the names of serializable and non-serializable attributes
        String keys[] = keys();
        ArrayList<String> saveNames = new ArrayList<String>();
        ArrayList<Object> saveValues = new ArrayList<Object>();
        for (int i = 0; i < keys.length; i++) {
            Object value = attributes.get(keys[i]);
            if (value == null) {
                continue;
            } else if (isAttributeDistributable(keys[i], value) && !exclude(keys[i], value)) {
                saveNames.add(keys[i]);
                saveValues.add(value);
            } else {
                removeAttributeInternal(keys[i], true);
            }
        }

        // Serialize the attribute count and the Serializable attributes
        int n = saveNames.size();
        stream.writeObject(Integer.valueOf(n));
        for (int i = 0; i < n; i++) {
            stream.writeObject(saveNames.get(i));
            try {
                stream.writeObject(saveValues.get(i));
                if (manager.getContainer().getLogger().isDebugEnabled())
                    manager.getContainer().getLogger().debug(
                            "  storing attribute '" + saveNames.get(i) + "' with value '" + saveValues.get(i) + "'");
            } catch (NotSerializableException e) {
                manager.getContainer().getLogger().warn(
                        sm.getString("standardSession.notSerializable", saveNames.get(i), id), e);
            }
        }

    }


    /**
     * Exclude standard attributes that cannot be serialized.
     * @param name the attribute's name
     *
     * @deprecated Use {@link #exclude(String, Object)}. Will be removed in
     *             Tomcat 9.0.x.
     */
    @Deprecated
    protected boolean exclude(String name){
        return exclude(name, null);
    }


    /**
     * Should the given session attribute be excluded? This implementation
     * checks:
     * <ul>
     * <li>{@link Constants#excludedAttributeNames}</li>
     * <li>{@link Manager#willAttributeDistribute(String, Object)}</li>
     * </ul>
     * Note: This method deliberately does not check
     *       {@link #isAttributeDistributable(String, Object)} which is kept
     *       separate to support the checks required in
     *       {@link #setAttribute(String, Object, boolean)}
     *
     * @param name  The attribute name
     * @param value The attribute value
     *
     * @return {@code true} if the attribute should be excluded from
     *         distribution, otherwise {@code false}
     */
    protected boolean exclude(String name, Object value) {
        if (Constants.excludedAttributeNames.contains(name)) {
            return true;
        }

        // Manager is required for remaining check
        Manager manager = getManager();
        if (manager == null) {
            // Manager may be null during replication of new sessions in a
            // cluster. Avoid the NPE.
            return false;
        }

        // Last check so use a short-cut
        return !manager.willAttributeDistribute(name, value);
    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Fire container events if the Context implementation is the
     * <code>org.apache.catalina.core.StandardContext</code>.
     *
     * @param context Context for which to fire events
     * @param type Event type
     * @param data Event data
     *
     * @exception Exception occurred during event firing
     *
     * @deprecated  No longer necessary since {@link StandardContext} implements
     *              the {@link org.apache.catalina.Container} interface.
     *
     */
    @Deprecated
    protected void fireContainerEvent(Context context,
                                    String type, Object data)
        throws Exception {

        if (context instanceof StandardContext) {
            ((StandardContext) context).fireContainerEvent(type, data);
        }
    }


    /**
     * Notify all session event listeners that a particular event has
     * occurred for this Session.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param data Event data
     */
    public void fireSessionEvent(String type, Object data) {
        if (listeners.size() < 1)
            return;
        SessionEvent event = new SessionEvent(this, type, data);
        SessionListener list[] = new SessionListener[0];
        synchronized (listeners) {
            list = listeners.toArray(list);
        }

        for (int i = 0; i < list.length; i++){
            (list[i]).sessionEvent(event);
        }

    }


    /**
     * Return the names of all currently defined session attributes
     * as an array of Strings.  If there are no defined attributes, a
     * zero-length array is returned.
     */
    protected String[] keys() {

        return attributes.keySet().toArray(EMPTY_ARRAY);

    }


    /**
     * Remove the object bound with the specified name from this session.  If
     * the session does not have an object bound with this name, this method
     * does nothing.
     * <p>
     * After this method executes, and if the object implements
     * <code>HttpSessionBindingListener</code>, the container calls
     * <code>valueUnbound()</code> on the object.
     *
     * @param name Name of the object to remove from this session.
     * @param notify Should we notify interested listeners that this
     *  attribute is being removed?
     */
    protected void removeAttributeInternal(String name, boolean notify) {

        // Avoid NPE
        if (name == null) return;

        // Remove this attribute from our collection
        Object value = attributes.remove(name);

        // Do we need to do valueUnbound() and attributeRemoved() notification?
        if (!notify || (value == null)) {
            return;
        }

        // Call the valueUnbound() method if necessary
        HttpSessionBindingEvent event = null;
        if (value instanceof HttpSessionBindingListener) {
            event = new HttpSessionBindingEvent(getSession(), name, value);
            ((HttpSessionBindingListener) value).valueUnbound(event);
        }

        // Notify interested application event listeners
        Context context = (Context) manager.getContainer();
        Object listeners[] = context.getApplicationEventListeners();
        if (listeners == null)
            return;
        for (int i = 0; i < listeners.length; i++) {
            if (!(listeners[i] instanceof HttpSessionAttributeListener))
                continue;
            HttpSessionAttributeListener listener =
                (HttpSessionAttributeListener) listeners[i];
            try {
                context.fireContainerEvent("beforeSessionAttributeRemoved",
                        listener);
                if (event == null) {
                    event = new HttpSessionBindingEvent
                        (getSession(), name, value);
                }
                listener.attributeRemoved(event);
                context.fireContainerEvent("afterSessionAttributeRemoved",
                        listener);
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                try {
                    context.fireContainerEvent("afterSessionAttributeRemoved",
                            listener);
                } catch (Exception e) {
                    // Ignore
                }
                manager.getContainer().getLogger().error
                    (sm.getString("standardSession.attributeEvent"), t);
            }
        }
    }
}

// ------------------------------------------------------------ Protected Class


/**
 * This class is a dummy implementation of the <code>HttpSessionContext</code>
 * interface, to conform to the requirement that such an object be returned
 * when <code>HttpSession.getSessionContext()</code> is called.
 *
 * @author Craig R. McClanahan
 *
 * @deprecated As of Java Servlet API 2.1 with no replacement.  The
 *  interface will be removed in a future version of this API.
 */

@Deprecated
final class StandardSessionContext
        implements javax.servlet.http.HttpSessionContext {

    private static final List<String> emptyString = Collections.emptyList();

    /**
     * Return the session identifiers of all sessions defined
     * within this context.
     *
     * @deprecated As of Java Servlet API 2.1 with no replacement.
     *  This method must return an empty <code>Enumeration</code>
     *  and will be removed in a future version of the API.
     */
    @Override
    @Deprecated
    public Enumeration<String> getIds() {
        return Collections.enumeration(emptyString);
    }


    /**
     * Return the <code>HttpSession</code> associated with the
     * specified session identifier.
     *
     * @param id Session identifier for which to look up a session
     *
     * @deprecated As of Java Servlet API 2.1 with no replacement.
     *  This method must return null and will be removed in a
     *  future version of the API.
     */
    @Override
    @Deprecated
    public HttpSession getSession(String id) {
        return (null);
    }
}
