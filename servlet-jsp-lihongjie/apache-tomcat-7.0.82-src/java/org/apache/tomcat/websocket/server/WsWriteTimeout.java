/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.websocket.server;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tomcat.websocket.BackgroundProcess;
import org.apache.tomcat.websocket.BackgroundProcessManager;

/**
 * Provides timeouts for asynchronous web socket writes. On the server side we
 * only have access to {@link javax.servlet.ServletOutputStream} and
 * {@link javax.servlet.ServletInputStream} so there is no way to set a timeout
 * for writes to the client.
 */
public class WsWriteTimeout implements BackgroundProcess {

    private final Set<WsRemoteEndpointImplServer> endpoints =
            new ConcurrentSkipListSet<WsRemoteEndpointImplServer>(new EndpointComparator());
    private final AtomicInteger count = new AtomicInteger(0);
    private int backgroundProcessCount = 0;
    private volatile int processPeriod = 1;

    @Override
    public void backgroundProcess() {
        // This method gets called once a second.
        backgroundProcessCount ++;

        if (backgroundProcessCount >= processPeriod) {
            backgroundProcessCount = 0;

            long now = System.currentTimeMillis();
            Iterator<WsRemoteEndpointImplServer> iter = endpoints.iterator();
            while (iter.hasNext()) {
                WsRemoteEndpointImplServer endpoint = iter.next();
                if (endpoint.getTimeoutExpiry() < now) {
                    // Background thread, not the thread that triggered the
                    // write so no need to use a dispatch
                    endpoint.onTimeout(false);
                } else {
                    // Endpoints are ordered by timeout expiry so if this point
                    // is reached there is no need to check the remaining
                    // endpoints
                    break;
                }
            }
        }
    }


    @Override
    public void setProcessPeriod(int period) {
        this.processPeriod = period;
    }


    /**
     * {@inheritDoc}
     *
     * The default value is 1 which means asynchronous write timeouts are
     * processed every 1 second.
     */
    @Override
    public int getProcessPeriod() {
        return processPeriod;
    }


    public void register(WsRemoteEndpointImplServer endpoint) {
        boolean result = endpoints.add(endpoint);
        if (result) {
            int newCount = count.incrementAndGet();
            if (newCount == 1) {
                BackgroundProcessManager.getInstance().register(this);
            }
        }
    }


    public void unregister(WsRemoteEndpointImplServer endpoint) {
        boolean result = endpoints.remove(endpoint);
        if (result) {
            int newCount = count.decrementAndGet();
            if (newCount == 0) {
                BackgroundProcessManager.getInstance().unregister(this);
            }
        }
    }


    /**
     * Note: this comparator imposes orderings that are inconsistent with equals
     */
    private static class EndpointComparator implements
            Comparator<WsRemoteEndpointImplServer> {

        @Override
        public int compare(WsRemoteEndpointImplServer o1,
                WsRemoteEndpointImplServer o2) {

            long t1 = o1.getTimeoutExpiry();
            long t2 = o2.getTimeoutExpiry();

            if (t1 < t2) {
                return -1;
            } else if (t1 == t2) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
