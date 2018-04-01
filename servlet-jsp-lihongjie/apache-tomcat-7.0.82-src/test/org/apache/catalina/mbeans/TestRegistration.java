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
package org.apache.catalina.mbeans;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Realm;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.realm.CombinedRealm;
import org.apache.catalina.realm.NullRealm;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.modeler.Registry;

/**
 * General tests around the process of registration and de-registration that
 * don't necessarily apply to one specific Tomcat class.
 *
 */
public class TestRegistration extends TomcatBaseTest {

    private static final String contextName = "/foo";

    private static final String ADDRESS;

    static {
        String address;
        try {
            address = InetAddress.getByName("localhost").getHostAddress();
        } catch (UnknownHostException e) {
            address = "INIT_FAILED";
        }
        ADDRESS = address;
    }


    private static String[] basicMBeanNames() {
        return new String[] {
            "Tomcat:type=Engine",
            "Tomcat:type=Realm,realmPath=/realm0",
            "Tomcat:type=MBeanFactory",
            "Tomcat:type=NamingResources",
            "Tomcat:type=Server",
            "Tomcat:type=Service",
            "Tomcat:type=StringCache",
            "Tomcat:type=Valve,name=StandardEngineValve",
        };
    }

    private static String[] hostMBeanNames(String host) {
        return new String[] {
            "Tomcat:type=Host,host=" + host,
            "Tomcat:type=Valve,host=" + host + ",name=ErrorReportValve",
            "Tomcat:type=Valve,host=" + host + ",name=StandardHostValve",
        };
    }

    private String[] optionalMBeanNames(String host) {
        if (isAccessLogEnabled()) {
            return new String[] {
                "Tomcat:type=Valve,host=" + host + ",name=AccessLogValve",
            };
        } else {
            return new String[] { };
        }
    }

    private static String[] requestMBeanNames(String port, String type) {
        return new String[] {
            "Tomcat:type=RequestProcessor,worker=" +
                    ObjectName.quote("http-" + type + "-" + ADDRESS + "-" + port) +
                    ",name=HttpRequest1",
        };
    }

    private static String[] contextMBeanNames(String host, String context) {
        return new String[] {
            "Tomcat:j2eeType=WebModule,name=//" + host + context +
                ",J2EEApplication=none,J2EEServer=none",
            "Tomcat:type=Cache,host=" + host + ",context=" + context,
            "Tomcat:type=Loader,context=" + context + ",host=" + host,
            "Tomcat:type=Manager,context=" + context + ",host=" + host,
            "Tomcat:type=NamingResources,context=" + context +
                ",host=" + host,
            "Tomcat:type=Valve,context=" + context +
                ",host=" + host + ",name=NonLoginAuthenticator",
            "Tomcat:type=Valve,context=" + context +
                ",host=" + host + ",name=StandardContextValve",
            "Tomcat:type=WebappClassLoader,context=" + context +
                ",host=" + host,
            "Tomcat:type=Realm,realmPath=/realm0,context=" + context +
                ",host=" + host,
            "Tomcat:type=Realm,realmPath=/realm0/realm0,context=" + context +
                ",host=" + host,
        };
    }

    private static String[] connectorMBeanNames(String port, String type) {
        return new String[] {
        "Tomcat:type=Connector,port=" + port + ",address="
                + ObjectName.quote(ADDRESS),
        "Tomcat:type=GlobalRequestProcessor,name="
                + ObjectName.quote("http-" + type + "-" + ADDRESS + "-" + port),
        "Tomcat:type=Mapper,port=" + port + ",address="
                + ObjectName.quote(ADDRESS),
        "Tomcat:type=ProtocolHandler,port=" + port + ",address="
                + ObjectName.quote(ADDRESS),
        "Tomcat:type=ThreadPool,name="
                + ObjectName.quote("http-" + type + "-" + ADDRESS + "-" + port),
        };
    }

    /**
     * Test verifying that Tomcat correctly de-registers the MBeans it has
     * registered.
     * @author Marc Guillemot
     */
    @Test
    public void testMBeanDeregistration() throws Exception {
        final MBeanServer mbeanServer = Registry.getRegistry(null, null).getMBeanServer();
        // Verify there are no Catalina or Tomcat MBeans
        Set<ObjectName> onames = mbeanServer.queryNames(new ObjectName("Catalina:*"), null);
        log.info(MBeanDumper.dumpBeans(mbeanServer, onames));
        assertEquals("Unexpected: " + onames, 0, onames.size());
        onames = mbeanServer.queryNames(new ObjectName("Tomcat:*"), null);
        log.info(MBeanDumper.dumpBeans(mbeanServer, onames));
        assertEquals("Unexpected: " + onames, 0, onames.size());

        final Tomcat tomcat = getTomcatInstance();
        final File contextDir = new File(getTemporaryDirectory(), "webappFoo");
        addDeleteOnTearDown(contextDir);
        if (!contextDir.mkdirs() && !contextDir.isDirectory()) {
            fail("Failed to create: [" + contextDir.toString() + "]");
        }
        Context ctx = tomcat.addContext(contextName, contextDir.getAbsolutePath());

        CombinedRealm combinedRealm = new CombinedRealm();
        Realm nullRealm = new NullRealm();
        combinedRealm.addRealm(nullRealm);
        ctx.setRealm(combinedRealm);

        // Disable keep-alive otherwise request processing threads in keep-alive
        // won't shut down fast enough with BIO to de-register the processor
        // triggering a test failure
        tomcat.getConnector().setAttribute("maxKeepAliveRequests", Integer.valueOf(1));

        tomcat.start();

        getUrl("http://localhost:" + getPort());

        // Verify there are no Catalina MBeans
        onames = mbeanServer.queryNames(new ObjectName("Catalina:*"), null);
        log.info(MBeanDumper.dumpBeans(mbeanServer, onames));
        assertEquals("Found: " + onames, 0, onames.size());

        // Verify there are the correct Tomcat MBeans
        onames = mbeanServer.queryNames(new ObjectName("Tomcat:*"), null);
        ArrayList<String> found = new ArrayList<String>(onames.size());
        for (ObjectName on: onames) {
            found.add(on.toString());
        }

        // Create the list of expected MBean names
        String protocol = tomcat.getConnector().getProtocolHandlerClassName();
        if (protocol.indexOf("Nio") > 0) {
            protocol = "nio";
        } else if (protocol.indexOf("Apr") > 0) {
            protocol = "apr";
        } else {
            protocol = "bio";
        }
        String index = tomcat.getConnector().getProperty("nameIndex").toString();
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList(basicMBeanNames()));
        expected.addAll(Arrays.asList(hostMBeanNames("localhost")));
        expected.addAll(Arrays.asList(contextMBeanNames("localhost", contextName)));
        expected.addAll(Arrays.asList(connectorMBeanNames("auto-" + index, protocol)));
        expected.addAll(Arrays.asList(optionalMBeanNames("localhost")));
        expected.addAll(Arrays.asList(requestMBeanNames(
                "auto-" + index + "-" + getPort(), protocol)));

        // Did we find all expected MBeans?
        ArrayList<String> missing = new ArrayList<String>(expected);
        missing.removeAll(found);
        assertTrue("Missing Tomcat MBeans: " + missing, missing.isEmpty());

        // Did we find any unexpected MBeans?
        List<String> additional = found;
        additional.removeAll(expected);
        assertTrue("Unexpected Tomcat MBeans: " + additional, additional.isEmpty());

        tomcat.stop();

        // There should still be some Tomcat MBeans
        onames = mbeanServer.queryNames(new ObjectName("Tomcat:*"), null);
        assertTrue("No Tomcat MBeans", onames.size() > 0);

        // add a new host
        StandardHost host = new StandardHost();
        host.setName("otherhost");
        tomcat.getEngine().addChild(host);

        final File contextDir2 = new File(getTemporaryDirectory(), "webappFoo2");
        addDeleteOnTearDown(contextDir2);
        if (!contextDir2.mkdirs() && !contextDir2.isDirectory()) {
            fail("Failed to create: [" + contextDir2.toString() + "]");
        }
        tomcat.addContext(host, contextName + "2", contextDir2.getAbsolutePath());

        tomcat.start();
        tomcat.stop();
        tomcat.destroy();

        // There should be no Catalina MBeans and no Tomcat MBeans
        onames = mbeanServer.queryNames(new ObjectName("Catalina:*"), null);
        log.info(MBeanDumper.dumpBeans(mbeanServer, onames));
        assertEquals("Remaining: " + onames, 0, onames.size());
        onames = mbeanServer.queryNames(new ObjectName("Tomcat:*"), null);
        log.info(MBeanDumper.dumpBeans(mbeanServer, onames));
        assertEquals("Remaining: " + onames, 0, onames.size());
    }

}
