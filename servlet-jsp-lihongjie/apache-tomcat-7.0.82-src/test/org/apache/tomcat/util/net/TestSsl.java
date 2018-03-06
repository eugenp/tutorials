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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assume;
import org.junit.Test;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

/**
 * The keys and certificates used in this file are all available in svn and were
 * generated using a test CA the files for which are in the Tomcat PMC private
 * repository since not all of them are AL2 licensed.
 */
public class TestSsl extends TomcatBaseTest {

    @Test
    public void testSimpleSsl() throws Exception {
        TesterSupport.configureClientSsl();

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File(getBuildDirectory(), "webapps/examples");
        tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());

        TesterSupport.initSsl(tomcat);

        tomcat.start();
        ByteChunk res = getUrl("https://localhost:" + getPort() +
            "/examples/servlets/servlet/HelloWorldExample");
        assertTrue(res.toString().indexOf("<a href=\"../helloworld.html\">") > 0);
    }

    @Test
    public void testKeyPass() throws Exception {
        TesterSupport.configureClientSsl();

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File(getBuildDirectory(), "webapps/examples");
        tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());

        TesterSupport.initSsl(tomcat, TesterSupport.LOCALHOST_KEYPASS_JKS,
                TesterSupport.JKS_PASS, TesterSupport.JKS_KEY_PASS);

        tomcat.start();
        ByteChunk res = getUrl("https://localhost:" + getPort() +
            "/examples/servlets/servlet/HelloWorldExample");
        assertTrue(res.toString().indexOf("<a href=\"../helloworld.html\">") > 0);
    }


    boolean handshakeDone = false;

    @Test
    public void testRenegotiateFail() throws Exception {

        // If RFC5746 is supported, renegotiation will always work (and will
        // always be secure)
        if (TesterSupport.RFC_5746_SUPPORTED) {
            return;
        }

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File(getBuildDirectory(), "webapps/examples");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());

        TesterSupport.initSsl(tomcat);

        // Default - MITM attack prevented

        tomcat.start();
        SSLContext sslCtx = SSLContext.getInstance("TLS");
        sslCtx.init(null, TesterSupport.getTrustManagers(), null);
        SSLSocketFactory socketFactory = sslCtx.getSocketFactory();
        SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", getPort());

        socket.addHandshakeCompletedListener(new HandshakeCompletedListener() {
            @Override
            public void handshakeCompleted(HandshakeCompletedEvent event) {
                handshakeDone = true;
            }
        });

        OutputStream os = socket.getOutputStream();
        os.write("GET /examples/servlets/servlet/HelloWorldExample HTTP/1.0\n".getBytes());
        os.flush();


        InputStream is = socket.getInputStream();

        // Make sure the NIO connector has read the request before the handshake
        Thread.sleep(100);

        socket.startHandshake();

        os = socket.getOutputStream();

        try {
            os.write("Host: localhost\n\n".getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
            fail("Re-negotiation failed");
        }
        Reader r = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(r);
        String line = br.readLine();
        while (line != null) {
            // For testing System.out.println(line);
            line = br.readLine();
        }

        if (!handshakeDone) {
            // success - we timed-out without handshake
            return;
        }

        fail("Re-negotiation worked");
    }

    @Test
    public void testRenegotiateWorks() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        Assume.assumeTrue("SSL renegotiation has to be supported for this test",
                TesterSupport.isRenegotiationSupported(getTomcatInstance()));

        File appDir = new File(getBuildDirectory(), "webapps/examples");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());

        TesterSupport.initSsl(tomcat);

        tomcat.start();

        SSLContext sslCtx = SSLContext.getInstance("TLS");
        sslCtx.init(null, TesterSupport.getTrustManagers(), null);
        SSLSocketFactory socketFactory = 
                new TesterSupport.NoSSLv2SocketFactory(sslCtx.getSocketFactory());
        SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost",
                getPort());

        OutputStream os = socket.getOutputStream();

        os.write("GET /examples/servlets/servlet/HelloWorldExample HTTP/1.1\n".getBytes());
        os.flush();

        socket.startHandshake();

        try {
            os.write("Host: localhost\n\n".getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
            fail("Re-negotiation failed");
        }

        InputStream is = socket.getInputStream();
        Reader r = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(r);
        String line = br.readLine();
        while (line != null) {
            // For testing System.out.println(line);
            line = br.readLine();
        }
    }

    @Override
    public void setUp() throws Exception {
        if (!TesterSupport.RFC_5746_SUPPORTED) {
            // Make sure SSL renegotiation is not disabled in the JVM
            System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        }
        super.setUp();
    }
}
