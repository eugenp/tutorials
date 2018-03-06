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
import java.util.concurrent.Semaphore;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;


/**
 * <p>Implementation of a Valve that limits concurrency.</p>
 *
 * <p>This Valve may be attached to any Container, depending on the granularity
 * of the concurrency control you wish to perform. Note that internally, some
 * async requests may require multiple serial requests to complete what - to the
 * user - appears as a single request.</p>
 *
 * @author Remy Maucherat
 */
public class SemaphoreValve extends ValveBase {

    //------------------------------------------------------ Constructor
    public SemaphoreValve() {
        super(true);
    }

    // ----------------------------------------------------- Instance Variables


    /**
     * The descriptive information related to this implementation.
     */
    private static final String info =
        "org.apache.catalina.valves.SemaphoreValve/1.0";


    /**
     * Semaphore.
     */
    protected Semaphore semaphore = null;


    // ------------------------------------------------------------- Properties


    /**
     * Concurrency level of the semaphore.
     */
    protected int concurrency = 10;
    public int getConcurrency() { return concurrency; }
    public void setConcurrency(int concurrency) { this.concurrency = concurrency; }


    /**
     * Fairness of the semaphore.
     */
    protected boolean fairness = false;
    public boolean getFairness() { return fairness; }
    public void setFairness(boolean fairness) { this.fairness = fairness; }


    /**
     * Block until a permit is available.
     */
    protected boolean block = true;
    public boolean getBlock() { return block; }
    public void setBlock(boolean block) { this.block = block; }


    /**
     * Block interruptibly until a permit is available.
     */
    protected boolean interruptible = false;
    public boolean getInterruptible() { return interruptible; }
    public void setInterruptible(boolean interruptible) { this.interruptible = interruptible; }


    /**
     * Start this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void startInternal() throws LifecycleException {

        semaphore = new Semaphore(concurrency, fairness);

        setState(LifecycleState.STARTING);
    }


    /**
     * Stop this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void stopInternal() throws LifecycleException {

        setState(LifecycleState.STOPPING);

        semaphore = null;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return descriptive information about this Valve implementation.
     */
    @Override
    public String getInfo() {
        return (info);
    }


    /**
     * Do concurrency control on the request using the semaphore.
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be created
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void invoke(Request request, Response response)
        throws IOException, ServletException {

        if (controlConcurrency(request, response)) {
            boolean shouldRelease = true;
            try {
                if (block) {
                    if (interruptible) {
                        try {
                            semaphore.acquire();
                        } catch (InterruptedException e) {
                            shouldRelease = false;
                            permitDenied(request, response);
                            return;
                        }
                    } else {
                        semaphore.acquireUninterruptibly();
                    }
                } else {
                    if (!semaphore.tryAcquire()) {
                        shouldRelease = false;
                        permitDenied(request, response);
                        return;
                    }
                }
                getNext().invoke(request, response);
            } finally {
                if (shouldRelease) {
                    semaphore.release();
                }
            }
        } else {
            getNext().invoke(request, response);
        }

    }


    /**
     * Subclass friendly method to add conditions.
     * @param request
     * @param response
     */
    public boolean controlConcurrency(Request request, Response response) {
        return true;
    }


    /**
     * Subclass friendly method to add error handling when a permit isn't
     * granted.
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void permitDenied(Request request, Response response)
        throws IOException, ServletException {
        // NO-OP by default
    }


}
