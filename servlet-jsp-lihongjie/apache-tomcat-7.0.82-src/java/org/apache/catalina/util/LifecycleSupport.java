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


package org.apache.catalina.util;


import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;


/**
 * Support class to assist in firing LifecycleEvent notifications to
 * registered LifecycleListeners.
 *
 * @author Craig R. McClanahan
 */
public final class LifecycleSupport {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new LifecycleSupport object associated with the specified
     * Lifecycle component.
     *
     * @param lifecycle The Lifecycle component that will be the source
     *  of events that we fire
     */
    public LifecycleSupport(Lifecycle lifecycle) {

        super();
        this.lifecycle = lifecycle;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The source component for lifecycle events that we will fire.
     */
    private Lifecycle lifecycle = null;


    /**
     * The set of registered LifecycleListeners for event notifications.
     */
    private LifecycleListener listeners[] = new LifecycleListener[0];
    
    private final Object listenersLock = new Object(); // Lock object for changes to listeners


    // --------------------------------------------------------- Public Methods


    /**
     * Add a lifecycle event listener to this component.
     *
     * @param listener The listener to add
     */
    public void addLifecycleListener(LifecycleListener listener) {

      synchronized (listenersLock) {
          LifecycleListener results[] =
            new LifecycleListener[listeners.length + 1];
          for (int i = 0; i < listeners.length; i++)
              results[i] = listeners[i];
          results[listeners.length] = listener;
          listeners = results;
      }

    }


    /**
     * Get the lifecycle listeners associated with this lifecycle. If this 
     * Lifecycle has no listeners registered, a zero-length array is returned.
     */
    public LifecycleListener[] findLifecycleListeners() {

        return listeners;

    }


    /**
     * Notify all lifecycle event listeners that a particular event has
     * occurred for this Container.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param data Event data
     */
    public void fireLifecycleEvent(String type, Object data) {

        LifecycleEvent event = new LifecycleEvent(lifecycle, type, data);
        LifecycleListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].lifecycleEvent(event);

    }


    /**
     * Remove a lifecycle event listener from this component.
     *
     * @param listener The listener to remove
     */
    public void removeLifecycleListener(LifecycleListener listener) {

        synchronized (listenersLock) {
            int n = -1;
            for (int i = 0; i < listeners.length; i++) {
                if (listeners[i] == listener) {
                    n = i;
                    break;
                }
            }
            if (n < 0)
                return;
            LifecycleListener results[] =
              new LifecycleListener[listeners.length - 1];
            int j = 0;
            for (int i = 0; i < listeners.length; i++) {
                if (i != n)
                    results[j++] = listeners[i];
            }
            listeners = results;
        }

    }


}
