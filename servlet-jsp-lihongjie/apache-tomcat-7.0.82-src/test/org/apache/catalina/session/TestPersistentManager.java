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

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.tomcat.unittest.TesterContext;
import org.apache.tomcat.unittest.TesterHost;

public class TestPersistentManager {

    @Test
    public void testMinIdleSwap() throws Exception {
        PersistentManager manager = new PersistentManager();
        manager.setStore(new TesterStore());

        Host host = new TesterHost();
        Context context = new TesterContext();
        context.setParent(host);

        manager.setContainer(context);

        manager.setMaxActiveSessions(2);
        manager.setMinIdleSwap(0);

        manager.start();

        // Create the maximum number of sessions
        manager.createSession(null);
        manager.createSession(null);

        // Given the minIdleSwap settings, this should swap one out to get below
        // the limit
        manager.processPersistenceChecks();
        Assert.assertEquals(1,  manager.getActiveSessions());
        Assert.assertEquals(2,  manager.getActiveSessionsFull());

        manager.createSession(null);
        Assert.assertEquals(2,  manager.getActiveSessions());
        Assert.assertEquals(3,  manager.getActiveSessionsFull());
    }
}
