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
package org.apache.tomcat.util.net;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

import org.junit.Assume;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

/**
 * The keys and certificates used in this file are all available in svn and were
 * generated using a test CA the files for which are in the Tomcat PMC private
 * repository since not all of them are AL2 licensed.
 */
public class TestClientCert extends TomcatBaseTest {

    @Test
    public void testClientCertGetWithoutPreemptive() throws Exception {
        doTestClientCertGet(false);
    }

    @Test
    public void testClientCertGetWithPreemptive() throws Exception {
        doTestClientCertGet(true);
    }

    private void doTestClientCertGet(boolean preemptive) throws Exception {
        Assume.assumeTrue("SSL renegotiation has to be supported for this test",
                TesterSupport.isRenegotiationSupported(getTomcatInstance()));

        if (preemptive) {
            Tomcat tomcat = getTomcatInstance();
            // Only one context deployed
            Context c = (Context) tomcat.getHost().findChildren()[0];
            // Enable pre-emptive auth
            c.setPreemptiveAuthentication(true);
        }

        getTomcatInstance().start();

        // Unprotected resource
        ByteChunk res =
                getUrl("https://localhost:" + getPort() + "/unprotected");
        if (preemptive) {
            assertEquals("OK-" + TesterSupport.ROLE, res.toString());
        } else {
            assertEquals("OK", res.toString());
        }

        // Protected resource
        res = getUrl("https://localhost:" + getPort() + "/protected");
        assertEquals("OK-" + TesterSupport.ROLE, res.toString());
    }

    @Test
    public void testClientCertPostSmaller() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        int bodySize = tomcat.getConnector().getMaxSavePostSize() / 2;
        doTestClientCertPost(bodySize, false);
    }

    @Test
    public void testClientCertPostSame() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        int bodySize = tomcat.getConnector().getMaxSavePostSize();
        doTestClientCertPost(bodySize, false);
    }

    @Test
    public void testClientCertPostLarger() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        int bodySize = tomcat.getConnector().getMaxSavePostSize() * 2;
        doTestClientCertPost(bodySize, true);
    }

    private void doTestClientCertPost(int bodySize, boolean expectProtectedFail)
            throws Exception {
        Assume.assumeTrue("SSL renegotiation has to be supported for this test",
                TesterSupport.isRenegotiationSupported(getTomcatInstance()));

        getTomcatInstance().start();

        byte[] body = new byte[bodySize];
        Arrays.fill(body, TesterSupport.DATA);

        // Unprotected resource
        ByteChunk res = postUrl(body,
                "https://localhost:" + getPort() + "/unprotected");
        assertEquals("OK-" + bodySize, res.toString());

        // Protected resource
        res.recycle();
        int rc = postUrl(body, "https://localhost:" + getPort() + "/protected",
                res, null);
        if (expectProtectedFail) {
            assertEquals(401, rc);
        } else {
            assertEquals("OK-" + bodySize, res.toString());
        }
    }

    @Override
    public void setUp() throws Exception {
        if (!TesterSupport.RFC_5746_SUPPORTED) {
            // Make sure SSL renegotiation is not disabled in the JVM
            System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        }

        super.setUp();

        Tomcat tomcat = getTomcatInstance();

        TesterSupport.configureClientCertContext(tomcat);

        TesterSupport.configureClientSsl();
    }
}
