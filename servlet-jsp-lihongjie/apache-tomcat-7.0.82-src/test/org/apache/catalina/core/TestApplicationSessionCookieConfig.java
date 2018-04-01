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

package org.apache.catalina.core;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.LifecycleState;

public class TestApplicationSessionCookieConfig {
    private ApplicationSessionCookieConfig applicationSessionCookieConfig;
    private final CustomContext context = new CustomContext();

    @Before
    public void setUp() throws Exception {
        applicationSessionCookieConfig = new ApplicationSessionCookieConfig(
                context);
    }

    @Test
    public void testSetCommentInitPhase() {
        context.setState(LifecycleState.STARTING_PREP);
        applicationSessionCookieConfig.setComment("test");
        assertTrue(applicationSessionCookieConfig.getComment().equals("test"));
    }

    @Test(expected = IllegalStateException.class)
    public void testSetCommentNotInitPhase() {
        context.setState(LifecycleState.STARTED);
        applicationSessionCookieConfig.setComment("test");
    }

    @Test
    public void testSetDomainInitPhase() {
        context.setState(LifecycleState.STARTING_PREP);
        applicationSessionCookieConfig.setDomain("test");
        assertTrue(applicationSessionCookieConfig.getDomain().equals("test"));
    }

    @Test(expected = IllegalStateException.class)
    public void testSetDomainNotInitPhase() {
        context.setState(LifecycleState.STARTED);
        applicationSessionCookieConfig.setDomain("test");
    }

    @Test
    public void testSetHttpOnlyInitPhase() {
        context.setState(LifecycleState.STARTING_PREP);
        applicationSessionCookieConfig.setHttpOnly(true);
        assertTrue(applicationSessionCookieConfig.isHttpOnly());
    }

    @Test(expected = IllegalStateException.class)
    public void testSetHttpOnlyNotInitPhase() {
        context.setState(LifecycleState.STARTED);
        applicationSessionCookieConfig.setHttpOnly(true);
    }

    @Test
    public void testSetMaxAgeInitPhase() {
        context.setState(LifecycleState.STARTING_PREP);
        applicationSessionCookieConfig.setMaxAge(1);
        assertTrue(applicationSessionCookieConfig.getMaxAge() == 1);
    }

    @Test(expected = IllegalStateException.class)
    public void testSetMaxAgeNotInitPhase() {
        context.setState(LifecycleState.STARTED);
        applicationSessionCookieConfig.setMaxAge(1);
    }

    @Test
    public void testSetNameInitPhase() {
        context.setState(LifecycleState.STARTING_PREP);
        applicationSessionCookieConfig.setName("test");
        assertTrue(applicationSessionCookieConfig.getName().equals("test"));
    }

    @Test(expected = IllegalStateException.class)
    public void testSetNameNotInitPhase() {
        context.setState(LifecycleState.STARTED);
        applicationSessionCookieConfig.setName("test");
    }

    @Test
    public void testSetPathInitPhase() {
        context.setState(LifecycleState.STARTING_PREP);
        applicationSessionCookieConfig.setPath("test");
        assertTrue(applicationSessionCookieConfig.getPath().equals("test"));
    }

    @Test(expected = IllegalStateException.class)
    public void testSetPathNotInitPhase() {
        context.setState(LifecycleState.STARTED);
        applicationSessionCookieConfig.setPath("test");
    }

    @Test
    public void testSetSecureInitPhase() {
        context.setState(LifecycleState.STARTING_PREP);
        applicationSessionCookieConfig.setSecure(true);
        assertTrue(applicationSessionCookieConfig.isSecure());
    }

    @Test(expected = IllegalStateException.class)
    public void testSetSecureNotInitPhase() {
        context.setState(LifecycleState.STARTED);
        applicationSessionCookieConfig.setSecure(true);
    }

    private static class CustomContext extends StandardContext {
        private volatile LifecycleState state;

        @Override
        public LifecycleState getState() {
            return state;
        }

        @Override
        public synchronized void setState(LifecycleState state) {
            this.state = state;
        }
    }
}
