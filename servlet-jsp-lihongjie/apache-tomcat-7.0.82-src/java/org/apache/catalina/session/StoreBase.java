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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Manager;
import org.apache.catalina.Store;
import org.apache.catalina.util.CustomObjectInputStream;
import org.apache.catalina.util.LifecycleBase;
import org.apache.tomcat.util.res.StringManager;

/**
 * Abstract implementation of the {@link Store} interface to
 * support most of the functionality required by a {@link Store}.
 *
 * @author Bip Thelin
 */
public abstract class StoreBase extends LifecycleBase implements Store {

    // ----------------------------------------------------- Instance Variables

    /**
     * The descriptive information about this implementation.
     */
    protected static final String info = "StoreBase/1.0";

    /**
     * Name to register for this Store, used for logging.
     */
    protected static String storeName = "StoreBase";

    /**
     * The property change support for this component.
     */
    protected PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm = StringManager.getManager(StoreBase.class);

    /**
     * The Manager with which this Store is associated.
     */
    protected Manager manager;

    // ------------------------------------------------------------- Properties

    /**
     * @return the info for this Store.
     */
    @Override
    public String getInfo() {
        return info;
    }


    /**
     * @return the name for this Store, used for logging.
     */
    public String getStoreName() {
        return storeName;
    }


    /**
     * Set the Manager with which this Store is associated.
     *
     * @param manager The newly associated Manager
     */
    @Override
    public void setManager(Manager manager) {
        Manager oldManager = this.manager;
        this.manager = manager;
        support.firePropertyChange("manager", oldManager, this.manager);
    }

    /**
     * @return the Manager with which the Store is associated.
     */
    @Override
    public Manager getManager() {
        return this.manager;
    }


    // --------------------------------------------------------- Public Methods

    /**
     * Add a property change listener to this component.
     *
     * @param listener a value of type {@link PropertyChangeListener}
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Remove a property change listener from this component.
     *
     * @param listener The listener to remove
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Get only those keys of sessions, that are saved in the Store and are to
     * be expired.
     *
     * @return list of session keys, that are to be expired
     * @throws IOException
     *             if an input-/output error occurred
     */
    public String[] expiredKeys() throws IOException {
        return keys();
    }

    /**
     * Called by our background reaper thread to check if Sessions
     * saved in our store are subject of being expired. If so expire
     * the Session and remove it from the Store.
     *
     */
    public void processExpires() {
        String[] keys = null;

        if(!getState().isAvailable()) {
            return;
        }

        try {
            keys = expiredKeys();
        } catch (IOException e) {
            manager.getContainer().getLogger().error("Error getting keys", e);
            return;
        }
        if (manager.getContainer().getLogger().isDebugEnabled()) {
            manager.getContainer().getLogger().debug(getStoreName()+ ": processExpires check number of " + keys.length + " sessions" );
        }

        long timeNow = System.currentTimeMillis();

        for (int i = 0; i < keys.length; i++) {
            try {
                StandardSession session = (StandardSession) load(keys[i]);
                if (session == null) {
                    continue;
                }
                int timeIdle = (int) ((timeNow - session.getThisAccessedTime()) / 1000L);
                if (timeIdle < session.getMaxInactiveInterval()) {
                    continue;
                }
                if (manager.getContainer().getLogger().isDebugEnabled()) {
                    manager.getContainer().getLogger().debug(getStoreName()+ ": processExpires expire store session " + keys[i] );
                }
                boolean isLoaded = false;
                if (manager instanceof PersistentManagerBase) {
                    isLoaded = ((PersistentManagerBase) manager).isLoaded(keys[i]);
                } else {
                    try {
                        if (manager.findSession(keys[i]) != null) {
                            isLoaded = true;
                        }
                    } catch (IOException ioe) {
                        // Ignore - session will be expired
                    }
                }
                if (isLoaded) {
                    // recycle old backup session
                    session.recycle();
                } else {
                    // expire swapped out session
                    session.expire();
                }
                remove(keys[i]);
            } catch (Exception e) {
                manager.getContainer().getLogger().error("Session: "+keys[i]+"; ", e);
                try {
                    remove(keys[i]);
                } catch (IOException e2) {
                    manager.getContainer().getLogger().error("Error removing key", e2);
                }
            }
        }
    }


    // --------------------------------------------------------- Protected Methods

    /**
     * Create the object input stream to use to read a session from the store.
     * Sub-classes <b>must</b> have set the thread context class loader before
     * calling this method.
     *
     * @param is The input stream provided by the sub-class that will provide
     *           the data for a session
     *
     * @return An appropriately configured ObjectInputStream from which the
     *         session can be read.
     *
     * @throws IOException if a problem occurs creating the ObjectInputStream
     */
    protected ObjectInputStream getObjectInputStream(InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);

        CustomObjectInputStream ois;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (manager instanceof ManagerBase) {
            ManagerBase managerBase = (ManagerBase) manager;
            ois = new CustomObjectInputStream(bis, classLoader, manager.getContainer().getLogger(),
                    managerBase.getSessionAttributeValueClassNamePattern(),
                    managerBase.getWarnOnSessionAttributeFilterFailure());
        } else {
            ois = new CustomObjectInputStream(bis, classLoader);
        }

        return ois;
    }


    @Override
    protected void initInternal() {
        // NOOP
    }
    
    
    /**
     * Start this component and implement the requirements
     * of {@link LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void startInternal() throws LifecycleException {
        
        setState(LifecycleState.STARTING);
    }


    /**
     * Stop this component and implement the requirements
     * of {@link LifecycleBase#stopInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void stopInternal() throws LifecycleException {

        setState(LifecycleState.STOPPING);
    }
    
    
    @Override
    protected void destroyInternal() {
        // NOOP
    }
    
    
    /**
     * @return a String rendering of this object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());
        sb.append('[');
        if (manager == null) {
            sb.append("Manager is null");
        } else {
            sb.append(manager);
        }
        sb.append(']');
        return sb.toString();
    }
}
