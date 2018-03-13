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
package org.apache.tomcat.websocket;

import org.junit.After;
import org.junit.Assert;

import org.apache.catalina.startup.TomcatBaseTest;

public abstract class WebSocketBaseTest extends TomcatBaseTest {

    @After
    public void checkBackgroundProcessHasStopped() throws Exception {
        // Need to stop Tomcat to ensure background processed have been stopped.
        getTomcatInstance().stop();

        // Make sure the background process has stopped. In some test
        // environments it will continue to run and break other tests that check
        // it has stopped.
        int count = 0;
        // 5s should be plenty here but Gump can be a lot slower so allow 60s.
        while (count < 600) {
            if (BackgroundProcessManager.getInstance().getProcessCount() == 0) {
                break;
            }
            Thread.sleep(100);
            count++;
        }

        try {
            Assert.assertEquals(0, BackgroundProcessManager.getInstance().getProcessCount());
        } finally {
            // Ensure the next test is not affected
            BackgroundProcessManager.getInstance().shutdown();
        }
    }
}
