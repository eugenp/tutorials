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

import java.io.Externalizable;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.WriteAbortedException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.catalina.Manager;
import org.apache.catalina.SessionListener;
import org.apache.catalina.ha.CatalinaCluster;
import org.apache.catalina.ha.ClusterManager;
import org.apache.catalina.ha.ClusterMessage;
import org.apache.catalina.ha.ClusterSession;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.session.ManagerBase;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.session.StandardSession;
import org.apache.catalina.tribes.io.ReplicationStream;
import org.apache.catalina.tribes.tipis.ReplicatedMapEntry;
import org.apache.tomcat.util.res.StringManager;

/**
 *
 * Similar to the StandardSession except that this session will keep
 * track of deltas during a request.
 *
 * @author Filip Hanik
 */
public class DeltaSession extends StandardSession implements Externalizable,ClusterSession,ReplicatedMapEntry {

    public static final org.apache.juli.logging.Log log = org.apache.juli.logging.LogFactory.getLog(DeltaSession.class);

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm = StringManager.getManager(Constants.Package);

    // ----------------------------------------------------- Instance Variables

    /**
     * only the primary session will expire, or be able to expire due to
     * inactivity. This is set to false as soon as I receive this session over
     * the wire in a session message. That means that someone else has made a
     * request on another server.
     */
    private transient boolean isPrimarySession = true;

    /**
     * The delta request contains all the action info
     *
     */
    private transient DeltaRequest deltaRequest = null;

    /**
     * Last time the session was replicated, used for distributed expiring of
     * session
     */
    private transient long lastTimeReplicated = System.currentTimeMillis();


    protected final Lock diffLock = new ReentrantReadWriteLock().writeLock();

    private long version;

    // ----------------------------------------------------------- Constructors

    public DeltaSession() {
        this(null);
    }

    /**
     * Construct a new Session associated with the specified Manager.
     *
     * @param manager
     *            The manager with which this Session is associated
     */
    public DeltaSession(Manager manager) {
        super(manager);
        this.resetDeltaRequest();
    }

    // ----------------------------------------------------- ReplicatedMapEntry

    /**
     * Has the object changed since last replication
     * and is not in a locked state
     * @return boolean
     */
    @Override
    public boolean isDirty() {
        return getDeltaRequest().getSize()>0;
    }

    /**
     * If this returns true, the map will extract the diff using getDiff()
     * Otherwise it will serialize the entire object.
     * @return boolean
     */
    @Override
    public boolean isDiffable() {
        return true;
    }

    /**
     * Returns a diff and sets the dirty map to false
     * @return byte[]
     * @throws IOException
     */
    @Override
    public byte[] getDiff() throws IOException {
        try{
            lock();
            return getDeltaRequest().serialize();
        }finally{
            unlock();
        }
    }

    public ClassLoader[] getClassLoaders() {
        if ( manager instanceof BackupManager ) return ((BackupManager)manager).getClassLoaders();
        else if ( manager instanceof ClusterManagerBase ) return ((ClusterManagerBase)manager).getClassLoaders();
        else if ( manager instanceof StandardManager ) {
            StandardManager sm = (StandardManager)manager;
            return ClusterManagerBase.getClassLoaders(sm.getContainer());
        } else if ( manager instanceof ManagerBase ) {
            ManagerBase mb = (ManagerBase)manager;
            return ClusterManagerBase.getClassLoaders(mb.getContainer());
        }//end if
        return null;
    }

    /**
     * Applies a diff to an existing object.
     * @param diff byte[]
     * @param offset int
     * @param length int
     * @throws IOException
     */
    @Override
    public void applyDiff(byte[] diff, int offset, int length) throws IOException, ClassNotFoundException {
        try {
            lock();
            ReplicationStream stream = ( (ClusterManager) getManager()).getReplicationStream(diff, offset, length);
            ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
            try {
                ClassLoader[] loaders = getClassLoaders();
                if (loaders != null && loaders.length > 0)
                    Thread.currentThread().setContextClassLoader(loaders[0]);
                getDeltaRequest().readExternal(stream);
                getDeltaRequest().execute(this, ((ClusterManager)getManager()).isNotifyListenersOnReplication());
                stream.close();
            } finally {
                Thread.currentThread().setContextClassLoader(contextLoader);
            }
        }finally {
            unlock();
        }
    }

    /**
     * Resets the current diff state and resets the dirty flag
     */
    @Override
    public void resetDiff() {
        resetDeltaRequest();
    }

    /**
     * Lock during serialization
     */
    @Override
    public void lock() {
        diffLock.lock();
    }

    /**
     * Unlock after serialization
     */
    @Override
    public void unlock() {
        diffLock.unlock();
    }

    @Override
    public void setOwner(Object owner) {
        if ( owner instanceof ClusterManager && getManager()==null) {
            ClusterManager cm = (ClusterManager)owner;
            this.setManager(cm);
            this.setValid(true);
            this.setPrimarySession(false);
            this.access();
            this.resetDeltaRequest();
            this.endAccess();
        }
    }

    /**
     * If this returns true, to replicate that an object has been accessed
     * @return boolean
     */
    @Override
    public boolean isAccessReplicate() {
        long replDelta = System.currentTimeMillis() - getLastTimeReplicated();
        if (maxInactiveInterval >=0 && replDelta > (maxInactiveInterval * 1000L)) {
            return true;
        }
        return false;
    }

    /**
     * Access to an existing object.
     */
    @Override
    public void accessEntry() {
        this.access();
        this.setPrimarySession(false);
        this.endAccess();
    }

    // ----------------------------------------------------- Session Properties

    /**
     * returns true if this session is the primary session, if that is the case,
     * the manager can expire it upon timeout.
     */
    @Override
    public boolean isPrimarySession() {
        return isPrimarySession;
    }

    /**
     * Sets whether this is the primary session or not.
     *
     * @param primarySession
     *            Flag value
     */
    @Override
    public void setPrimarySession(boolean primarySession) {
        this.isPrimarySession = primarySession;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(String id, boolean notify) {
        super.setId(id, notify);
        resetDeltaRequest();
    }


    /**
     * Set the session identifier for this session.
     *
     * @param id
     *            The new session identifier
     */
    @Override
    public void setId(String id) {
        super.setId(id, true);
        resetDeltaRequest();
    }


    @Override
    public void setMaxInactiveInterval(int interval) {
        this.setMaxInactiveInterval(interval,true);
    }


    public void setMaxInactiveInterval(int interval, boolean addDeltaRequest) {
        super.maxInactiveInterval = interval;
        if (addDeltaRequest && (deltaRequest != null)) {
            try {
                lock();
                deltaRequest.setMaxInactiveInterval(interval);
            }finally{
                unlock();
            }
        }
    }

    /**
     * Set the <code>isNew</code> flag for this session.
     *
     * @param isNew
     *            The new value for the <code>isNew</code> flag
     */
    @Override
    public void setNew(boolean isNew) {
        setNew(isNew, true);
    }

    public void setNew(boolean isNew, boolean addDeltaRequest) {
        super.setNew(isNew);
        if (addDeltaRequest && (deltaRequest != null)){
            try {
                lock();
                deltaRequest.setNew(isNew);
            }finally{
                unlock();
            }
        }
    }

    /**
     * Set the authenticated Principal that is associated with this Session.
     * This provides an <code>Authenticator</code> with a means to cache a
     * previously authenticated Principal, and avoid potentially expensive
     * <code>Realm.authenticate()</code> calls on every request.
     *
     * @param principal
     *            The new Principal, or <code>null</code> if none
     */
    @Override
    public void setPrincipal(Principal principal) {
        setPrincipal(principal, true);
    }

    public void setPrincipal(Principal principal, boolean addDeltaRequest) {
        try { 
            lock();
            super.setPrincipal(principal);
            if (addDeltaRequest && (deltaRequest != null))
                deltaRequest.setPrincipal(principal);
        } finally {
            unlock();
        }
    }

    /**
     * Set the authentication type used to authenticate our cached
     * Principal, if any.
     *
     * @param authType The new cached authentication type
     */
    @Override
    public void setAuthType(String authType) {
        setAuthType(authType, true);
    }

    public void setAuthType(String authType, boolean addDeltaRequest) {
        try { 
            lock();
            super.setAuthType(authType);
            if (addDeltaRequest && (deltaRequest != null))
                deltaRequest.setAuthType(authType);
        } finally {
            unlock();
        }
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
            if (isPrimarySession()) {
                if (timeIdle >= maxInactiveInterval) {
                    expire(true);
                }
            } else {
                if (timeIdle >= (2 * maxInactiveInterval)) {
                    //if the session has been idle twice as long as allowed,
                    //the primary session has probably crashed, and no other
                    //requests are coming in. that is why we do this. otherwise
                    //we would have a memory leak
                    expire(true, false);
                }
            }
        }
        return (this.isValid);
    }

    /**
     * End the access and register to ReplicationValve (crossContext support)
     */
    @Override
    public void endAccess() {
        super.endAccess() ;
        if(manager instanceof ClusterManagerBase) {
            ((ClusterManagerBase)manager).registerSessionAtReplicationValve(this);
        }
    }
    
    // ------------------------------------------------- Session Public Methods

    /**
     * Perform the internal processing required to invalidate this session,
     * without triggering an exception if the session has already expired.
     *
     * @param notify
     *            Should we notify listeners about the demise of this session?
     */
    @Override
    public void expire(boolean notify) {
        expire(notify, true);
    }

    public void expire(boolean notify, boolean notifyCluster) {

        // Check to see if session has already been invalidated.
        // Do not check expiring at this point as expire should not return until
        // isValid is false
        if (!isValid)
            return;

        synchronized (this) {
            // Check again, now we are inside the sync so this code only runs once
            // Double check locking - isValid needs to be volatile
            if (!isValid)
                return;

            if (manager == null)
                return;

            String expiredId = getIdInternal();

            if(notifyCluster && expiredId != null &&
                    manager instanceof DeltaManager) {
                DeltaManager dmanager = (DeltaManager)manager;
                CatalinaCluster cluster = dmanager.getCluster();
                ClusterMessage msg = dmanager.requestCompleted(expiredId, true);
                if (msg != null) {
                    cluster.send(msg);
                }
            }

            super.expire(notify);

            if (notifyCluster) {
                if (log.isDebugEnabled())
                    log.debug(sm.getString("deltaSession.notifying",
                                           ((ClusterManager)manager).getName(),
                                           Boolean.valueOf(isPrimarySession()),
                                           expiredId));
                if ( manager instanceof DeltaManager ) {
                    ( (DeltaManager) manager).sessionExpired(expiredId);
                }
            }
        }
    }

    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    @Override
    public void recycle() {
        try {
            lock();
            super.recycle();
            deltaRequest.clear();
        }finally{
            unlock();
        }
    }


    /**
     * Return a string representation of this object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DeltaSession[");
        sb.append(id);
        sb.append("]");
        return (sb.toString());
    }

    @Override
    public void addSessionListener(SessionListener listener) {
        addSessionListener(listener, true);
    }

    public void addSessionListener(SessionListener listener, boolean addDeltaRequest) {
        lock();
        try {
            super.addSessionListener(listener);
            if (addDeltaRequest && deltaRequest != null && listener instanceof ReplicatedSessionListener) {
                deltaRequest.addSessionListener(listener);
            }
        } finally {
            unlock();
        }
    }

    @Override
    public void removeSessionListener(SessionListener listener) {
        removeSessionListener(listener, true);
    }

    public void removeSessionListener(SessionListener listener, boolean addDeltaRequest) {
        lock();
        try {
            super.removeSessionListener(listener);
            if (addDeltaRequest && deltaRequest != null && listener instanceof ReplicatedSessionListener) {
                deltaRequest.removeSessionListener(listener);
            }
        } finally {
            unlock();
        }
    }


    // ------------------------------------------------ Session Package Methods

    @Override
    public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException {
        try {
            lock();
            readObjectData(in);
        }finally{
            unlock();
        }
    }


    /**
     * Read a serialized version of the contents of this session object from the
     * specified object input stream, without requiring that the StandardSession
     * itself have been serialized.
     *
     * @param stream
     *            The object input stream to read from
     *
     * @exception ClassNotFoundException
     *                if an unknown class is specified
     * @exception IOException
     *                if an input/output error occurs
     */
    @Override
    public void readObjectData(ObjectInputStream stream) throws ClassNotFoundException, IOException {
        readObject((ObjectInput)stream);
    }
    public void readObjectData(ObjectInput stream) throws ClassNotFoundException, IOException {
        readObject(stream);
    }

    /**
     * Write a serialized version of the contents of this session object to the
     * specified object output stream, without requiring that the
     * StandardSession itself have been serialized.
     *
     * @param stream
     *            The object output stream to write to
     *
     * @exception IOException
     *                if an input/output error occurs
     */
    @Override
    public void writeObjectData(ObjectOutputStream stream) throws IOException {
        writeObjectData((ObjectOutput)stream);
    }
    public void writeObjectData(ObjectOutput stream) throws IOException {
        writeObject(stream);
    }

    public void resetDeltaRequest() {
        try {
            lock();
            if (deltaRequest == null) {
                boolean recordAllActions = manager instanceof ClusterManagerBase &&
                        ((ClusterManagerBase)manager).isRecordAllActions();
                deltaRequest = new DeltaRequest(getIdInternal(), recordAllActions);
            } else {
                deltaRequest.reset();
                deltaRequest.setSessionId(getIdInternal());
            }
        }finally{
            unlock();
        }
    }

    public DeltaRequest getDeltaRequest() {
        if (deltaRequest == null) resetDeltaRequest();
        return deltaRequest;
    }


    // ------------------------------------------------- HttpSession Properties

    // ----------------------------------------------HttpSession Public Methods

    /**
     * Remove the object bound with the specified name from this session. If the
     * session does not have an object bound with this name, this method does
     * nothing.
     * <p>
     * After this method executes, and if the object implements
     * <code>HttpSessionBindingListener</code>, the container calls
     * <code>valueUnbound()</code> on the object.
     *
     * @param name
     *            Name of the object to remove from this session.
     * @param notify
     *            Should we notify interested listeners that this attribute is
     *            being removed?
     *
     * @exception IllegalStateException
     *                if this method is called on an invalidated session
     */
    @Override
    public void removeAttribute(String name, boolean notify) {
        removeAttribute(name, notify, true);
    }

    public void removeAttribute(String name, boolean notify,boolean addDeltaRequest) {
        // Validate our current state
        if (!isValid()) throw new IllegalStateException(sm.getString("standardSession.removeAttribute.ise"));
        removeAttributeInternal(name, notify, addDeltaRequest);
    }

    /**
     * Bind an object to this session, using the specified name. If an object of
     * the same name is already bound to this session, the object is replaced.
     * <p>
     * After this method executes, and if the object implements
     * <code>HttpSessionBindingListener</code>, the container calls
     * <code>valueBound()</code> on the object.
     *
     * @param name
     *            Name to which the object is bound, cannot be null
     * @param value
     *            Object to be bound, cannot be null
     *
     * @exception IllegalArgumentException
     *                if an attempt is made to add a non-serializable object in
     *                an environment marked distributable.
     * @exception IllegalStateException
     *                if this method is called on an invalidated session
     */
    @Override
    public void setAttribute(String name, Object value) {
        setAttribute(name, value, true, true);
    }

    public void setAttribute(String name, Object value, boolean notify,boolean addDeltaRequest) {

        // Name cannot be null
        if (name == null) throw new IllegalArgumentException(sm.getString("standardSession.setAttribute.namenull"));

        // Null value is the same as removeAttribute()
        if (value == null) {
            removeAttribute(name);
            return;
        }

        try {
            lock();
            super.setAttribute(name,value, notify);
            if (addDeltaRequest && deltaRequest != null && !exclude(name, value)) {
                deltaRequest.setAttribute(name, value);
            }
        } finally {
            unlock();
        }
    }

    // -------------------------------------------- HttpSession Private Methods


    /**
     * Read a serialized version of this session object from the specified
     * object input stream.
     * <p>
     * <b>IMPLEMENTATION NOTE </b>: The reference to the owning Manager is not
     * restored by this method, and must be set explicitly.
     *
     * @param stream
     *            The input stream to read from
     *
     * @exception ClassNotFoundException
     *                if an unknown class is specified
     * @exception IOException
     *                if an input/output error occurs
     */
    @Override
    protected void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
        readObject((ObjectInput)stream);
    }

    private void readObject(ObjectInput stream) throws ClassNotFoundException, IOException {

        // Deserialize the scalar instance variables (except Manager)
        authType = null; // Transient only
        creationTime = ( (Long) stream.readObject()).longValue();
        lastAccessedTime = ( (Long) stream.readObject()).longValue();
        maxInactiveInterval = ( (Integer) stream.readObject()).intValue();
        isNew = ( (Boolean) stream.readObject()).booleanValue();
        isValid = ( (Boolean) stream.readObject()).booleanValue();
        thisAccessedTime = ( (Long) stream.readObject()).longValue();
        version = ( (Long) stream.readObject()).longValue();
        boolean hasPrincipal = stream.readBoolean();
        principal = null;
        if (hasPrincipal) {
            principal = SerializablePrincipal.readPrincipal(stream);
        }

        //        setId((String) stream.readObject());
        id = (String) stream.readObject();
        if (log.isDebugEnabled()) log.debug(sm.getString("deltaSession.readSession", id));

        // Deserialize the attribute count and attribute values
        if (attributes == null) attributes = new ConcurrentHashMap<String, Object>();
        int n = ( (Integer) stream.readObject()).intValue();
        boolean isValidSave = isValid;
        isValid = true;
        for (int i = 0; i < n; i++) {
            String name = (String) stream.readObject();
            final Object value;
            try {
                value = stream.readObject();
            } catch (WriteAbortedException wae) {
                if (wae.getCause() instanceof NotSerializableException) {
                    // Skip non serializable attributes
                    continue;
                }
                throw wae;
            }
            // Handle the case where the filter configuration was changed while
            // the web application was stopped.
            if (exclude(name, value)) {
                continue;
            }
            attributes.put(name, value);
        }
        isValid = isValidSave;

        // Session listeners
        n = ((Integer) stream.readObject()).intValue();
        if (listeners == null || n > 0) {
            listeners = new ArrayList<SessionListener>();
        }
        for (int i = 0; i < n; i++) {
            SessionListener listener = (SessionListener) stream.readObject();
            listeners.add(listener);
        }

        if (notes == null) {
            notes = new Hashtable<String,Object>();
        }
        activate();
    }

    @Override
    public void writeExternal(ObjectOutput out ) throws java.io.IOException {
        try {
            lock();
            writeObject(out);
        }finally {
            unlock();
        }
    }


    /**
     * Write a serialized version of this session object to the specified object
     * output stream.
     * <p>
     * <b>IMPLEMENTATION NOTE </b>: The owning Manager will not be stored in the
     * serialized representation of this Session. After calling
     * <code>readObject()</code>, you must set the associated Manager
     * explicitly.
     * <p>
     * <b>IMPLEMENTATION NOTE </b>: Any attribute that is not Serializable will
     * be unbound from the session, with appropriate actions if it implements
     * HttpSessionBindingListener. If you do not want any such attributes, be
     * sure the <code>distributable</code> property of the associated Manager
     * is set to <code>true</code>.
     *
     * @param stream
     *            The output stream to write to
     *
     * @exception IOException
     *                if an input/output error occurs
     */
    @Override
    protected void writeObject(ObjectOutputStream stream) throws IOException {
        writeObject((ObjectOutput)stream);
    }
    
    private void writeObject(ObjectOutput stream) throws IOException {
        // Write the scalar instance variables (except Manager)
        stream.writeObject(Long.valueOf(creationTime));
        stream.writeObject(Long.valueOf(lastAccessedTime));
        stream.writeObject(Integer.valueOf(maxInactiveInterval));
        stream.writeObject(Boolean.valueOf(isNew));
        stream.writeObject(Boolean.valueOf(isValid));
        stream.writeObject(Long.valueOf(thisAccessedTime));
        stream.writeObject(Long.valueOf(version));
        stream.writeBoolean(getPrincipal() != null);
        if (getPrincipal() != null) {
            SerializablePrincipal.writePrincipal((GenericPrincipal) principal,stream);
        }

        stream.writeObject(id);
        if (log.isDebugEnabled()) log.debug(sm.getString("deltaSession.writeSession", id));

        // Accumulate the names of serializable and non-serializable attributes
        String keys[] = keys();
        ArrayList<String> saveNames = new ArrayList<String>();
        ArrayList<Object> saveValues = new ArrayList<Object>();
        for (int i = 0; i < keys.length; i++) {
            Object value = null;
            value = attributes.get(keys[i]);
            if (value != null && !exclude(keys[i], value) &&
                    isAttributeDistributable(keys[i], value)) {
                saveNames.add(keys[i]);
                saveValues.add(value);
            }
        }

        // Serialize the attribute count and the Serializable attributes
        int n = saveNames.size();
        stream.writeObject(Integer.valueOf(n));
        for (int i = 0; i < n; i++) {
            stream.writeObject( saveNames.get(i));
            try {
                stream.writeObject(saveValues.get(i));
            } catch (NotSerializableException e) {
                log.error(sm.getString("standardSession.notSerializable", saveNames.get(i), id), e);
            }
        }

        // Serializable listeners
        ArrayList<SessionListener> saveListeners = new ArrayList<SessionListener>();
        for (SessionListener listener : listeners) {
            if (listener instanceof ReplicatedSessionListener) {
                saveListeners.add(listener);
            }
        }
        stream.writeObject(Integer.valueOf(saveListeners.size()));
        for (SessionListener listener : saveListeners) {
            stream.writeObject(listener);
        }
    }


    // -------------------------------------------------------- Private Methods


    /**
     * Return the value of an attribute without a check for validity.
     */
    protected Object getAttributeInternal(String name) {
        return (attributes.get(name));
    }

    protected void removeAttributeInternal(String name, boolean notify,
                                           boolean addDeltaRequest) {
        try {
            lock();
            // Remove this attribute from our collection
            Object value = attributes.get(name);
            if (value == null) return;

            super.removeAttributeInternal(name,notify);
            if (addDeltaRequest && deltaRequest != null && !exclude(name, null)) {
                deltaRequest.removeAttribute(name);
            }

        }finally {
            unlock();
        }
    }

    @Override
    public long getLastTimeReplicated() {
        return lastTimeReplicated;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public void setLastTimeReplicated(long lastTimeReplicated) {
        this.lastTimeReplicated = lastTimeReplicated;
    }

    @Override
    public void setVersion(long version) {
        this.version = version;
    }

    protected void setAccessCount(int count) {
        if ( accessCount == null && ACTIVITY_CHECK ) accessCount = new AtomicInteger();
        if ( accessCount != null ) super.accessCount.set(count);
    }
}
