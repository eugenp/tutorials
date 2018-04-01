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

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.SessionIdGenerator;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.res.StringManager;

public abstract class SessionIdGeneratorBase extends LifecycleBase
        implements SessionIdGenerator {

    private static final Log log = LogFactory.getLog(SessionIdGeneratorBase.class);


    private static final StringManager sm =
        StringManager.getManager("org.apache.catalina.util");


    /**
     * Queue of random number generator objects to be used when creating session
     * identifiers. If the queue is empty when a random number generator is
     * required, a new random number generator object is created. This is
     * designed this way since random number generators use a sync to make them
     * thread-safe and the sync makes using a a single object slow(er).
     */
    private final Queue<SecureRandom> randoms = new ConcurrentLinkedQueue<SecureRandom>();


    /**
     * The Java class name of the secure random number generator class to be
     * used when generating session identifiers. The random number generator
     * class must be self-seeding and have a zero-argument constructor. If not
     * specified, an instance of {@link SecureRandom} will be generated.
     */
    private String secureRandomClass = null;


    /**
     * The name of the algorithm to use to create instances of
     * {@link SecureRandom} which are used to generate session IDs. If no
     * algorithm is specified, SHA1PRNG is used. To use the platform default
     * (which may be SHA1PRNG), specify the empty string. If an invalid
     * algorithm and/or provider is specified the {@link SecureRandom} instances
     * will be created using the defaults. If that fails, the {@link
     * SecureRandom} instances will be created using platform defaults.
     */
    private String secureRandomAlgorithm = "SHA1PRNG";


    /**
     * The name of the provider to use to create instances of
     * {@link SecureRandom} which are used to generate session IDs. If
     * no algorithm is specified the of SHA1PRNG default is used. If an invalid
     * algorithm and/or provider is specified the {@link SecureRandom} instances
     * will be created using the defaults. If that fails, the {@link
     * SecureRandom} instances will be created using platform defaults.
     */
    private String secureRandomProvider = null;


    /** Node identifier when in a cluster. Defaults to the empty string. */
    private String jvmRoute = "";


    /** Number of bytes in a session ID. Defaults to 16. */
    private int sessionIdLength = 16;


    /**
     * Specify a non-default @{link {@link SecureRandom} implementation to use.
     *
     * @param secureRandomClass The fully-qualified class name
     */
    public void setSecureRandomClass(String secureRandomClass) {
        this.secureRandomClass = secureRandomClass;
    }


    /**
     * Specify a non-default algorithm to use to generate random numbers.
     *
     * @param secureRandomAlgorithm The name of the algorithm
     */
    public void setSecureRandomAlgorithm(String secureRandomAlgorithm) {
        this.secureRandomAlgorithm = secureRandomAlgorithm;
    }


    /**
     * Specify a non-default provider to use to generate random numbers.
     *
     * @param secureRandomProvider  The name of the provider
     */
    public void setSecureRandomProvider(String secureRandomProvider) {
        this.secureRandomProvider = secureRandomProvider;
    }


    /**
     * Return the node identifier associated with this node which will be
     * included in the generated session ID.
     */
    @Override
    public String getJvmRoute() {
        return jvmRoute;
    }


    /**
     * Specify the node identifier associated with this node which will be
     * included in the generated session ID.
     *
     * @param jvmRoute  The node identifier
     */
    @Override
    public void setJvmRoute(String jvmRoute) {
        this.jvmRoute = jvmRoute;
    }


    /**
     * Return the number of bytes for a session ID
     */
    @Override
    public int getSessionIdLength() {
        return sessionIdLength;
    }
    /**
     * Specify the number of bytes for a session ID
     *
     * @param sessionIdLength   Number of bytes
     */
    @Override
    public void setSessionIdLength(int sessionIdLength) {
        this.sessionIdLength = sessionIdLength;
    }


    /**
     * Generate and return a new session identifier.
     */
    @Override
    public String generateSessionId() {
        return generateSessionId(jvmRoute);
    }

    protected void getRandomBytes(byte bytes[]) {

        SecureRandom random = randoms.poll();
        if (random == null) {
            random = createSecureRandom();
        }
        random.nextBytes(bytes);
        randoms.add(random);
    }


    /**
     * Create a new random number generator instance we should use for
     * generating session identifiers.
     */
    private SecureRandom createSecureRandom() {

        SecureRandom result = null;

        long t1 = System.currentTimeMillis();
        if (secureRandomClass != null) {
            try {
                // Construct and seed a new random number generator
                Class<?> clazz = Class.forName(secureRandomClass);
                result = (SecureRandom) clazz.newInstance();
            } catch (Exception e) {
                log.error(sm.getString("sessionIdGeneratorBase.random",
                        secureRandomClass), e);
            }
        }

        boolean error = false;
        if (result == null) {
            // No secureRandomClass or creation failed. Use SecureRandom.
            try {
                if (secureRandomProvider != null &&
                        secureRandomProvider.length() > 0) {
                    result = SecureRandom.getInstance(secureRandomAlgorithm,
                            secureRandomProvider);
                } else if (secureRandomAlgorithm != null &&
                        secureRandomAlgorithm.length() > 0) {
                    result = SecureRandom.getInstance(secureRandomAlgorithm);
                }
            } catch (NoSuchAlgorithmException e) {
                error = true;
                log.error(sm.getString("sessionIdGeneratorBase.randomAlgorithm",
                        secureRandomAlgorithm), e);
            } catch (NoSuchProviderException e) {
                error = true;
                log.error(sm.getString("sessionIdGeneratorBase.randomProvider",
                        secureRandomProvider), e);
            }
        }

        if (result == null && error) {
            // Invalid provider / algorithm
            try {
                result = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException e) {
                log.error(sm.getString("sessionIdGeneratorBase.randomAlgorithm",
                        secureRandomAlgorithm), e);
            }
        }

        if (result == null) {
            // Nothing works - use platform default
            result = new SecureRandom();
        }

        // Force seeding to take place
        result.nextInt();

        long t2 = System.currentTimeMillis();
        if ((t2 - t1) > 100) {
            log.warn(sm.getString("sessionIdGeneratorBase.createRandom",
                    result.getAlgorithm(), Long.valueOf(t2 - t1)));
        }
        return result;
    }


    @Override
    protected void initInternal() throws LifecycleException {
        // NO-OP
    }


    @Override
    protected void startInternal() throws LifecycleException {
        // Ensure SecureRandom has been initialised
        generateSessionId();

        setState(LifecycleState.STARTING);
    }


    @Override
    protected void stopInternal() throws LifecycleException {
        setState(LifecycleState.STOPPING);
        randoms.clear();
    }


    @Override
    protected void destroyInternal() throws LifecycleException {
        // NO-OP
    }
}
