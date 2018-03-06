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

package org.apache.catalina.core;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;

public class TestNamingContextListener extends TomcatBaseTest {

    private static final String BUG49132_NAME = "TestName";
    private static final String BUG49132_VALUE = "Test Value";

    private static final String BUG54096_NameA = "envA";
    private static final String BUG54096_ValueA = "valueA";
    private static final String BUG54096_NameB = "envB";
    private static final String BUG54096_ValueB = "B";

    /**
     * Test JNDI is available to ServletContextListeners.
     */
    @Test
    public void testBug49132() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        org.apache.catalina.Context ctx = tomcat.addContext("", null);

        // Enable JNDI - it is disabled by default
        tomcat.enableNaming();

        ContextEnvironment environment = new ContextEnvironment();
        environment.setType(BUG49132_VALUE.getClass().getName());
        environment.setName(BUG49132_NAME);
        environment.setValue(BUG49132_VALUE);
        ctx.getNamingResources().addEnvironment(environment);

        ctx.addApplicationListener(Bug49132Listener.class.getName());

        tomcat.start();

        assertEquals(LifecycleState.STARTED, ctx.getState());
    }

    public static final class Bug49132Listener implements ServletContextListener {

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            // NOOP
        }

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            javax.naming.Context initCtx;
            try {
                initCtx = new InitialContext();
                javax.naming.Context envCtx =
                    (javax.naming.Context) initCtx.lookup("java:comp/env");
                String value = (String) envCtx.lookup(BUG49132_NAME);
                if (!BUG49132_VALUE.equals(value)) {
                    throw new RuntimeException();
                }
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testBug54096() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        org.apache.catalina.Context ctx = tomcat.addContext("", null);

        // Enable JNDI - it is disabled by default
        tomcat.enableNaming();

        ContextEnvironment environmentA = new ContextEnvironment();
        environmentA.setType(Bug54096EnvA.class.getName());
        environmentA.setName(BUG54096_NameA);
        environmentA.setValue(BUG54096_ValueA);
        ctx.getNamingResources().addEnvironment(environmentA);

        ContextEnvironment environmentB = new ContextEnvironment();
        environmentB.setType(Bug54096EnvB.class.getName());
        environmentB.setName(BUG54096_NameB);
        environmentB.setValue(BUG54096_ValueB);
        ctx.getNamingResources().addEnvironment(environmentB);

        ctx.addApplicationListener(Bug54096Listener.class.getName());

        tomcat.start();

        assertEquals(LifecycleState.STARTED, ctx.getState());
    }

    public static class Bug54096EnvA {

        private final String value;

        public Bug54096EnvA(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class Bug54096EnvB {

        private final char value;

        public Bug54096EnvB(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }
    }

    public static final class Bug54096Listener implements
            ServletContextListener {

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            // NOOP
        }

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            javax.naming.Context initCtx;
            try {
                initCtx = new InitialContext();
                javax.naming.Context envCtx =
                    (javax.naming.Context) initCtx.lookup("java:comp/env");

                // Validate entry A
                Bug54096EnvA valueA =
                        (Bug54096EnvA) envCtx.lookup(BUG54096_NameA);
                if (!BUG54096_ValueA.equals(valueA.getValue())) {
                    throw new RuntimeException();
                }

                // Validate entry B
                Bug54096EnvB valueB =
                        (Bug54096EnvB) envCtx.lookup(BUG54096_NameB);
                if (BUG54096_ValueB.charAt(0) != valueB.getValue()) {
                    throw new RuntimeException();
                }

            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
