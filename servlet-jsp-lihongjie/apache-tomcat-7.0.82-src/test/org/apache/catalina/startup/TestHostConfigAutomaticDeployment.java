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
package org.apache.catalina.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.util.ContextName;
import org.apache.catalina.util.IOTools;
import org.apache.tomcat.util.buf.B2CConverter;

/**
 * The purpose of this class is to test the automatic deployment features of the
 * {@link HostConfig} implementation.
 */
public class TestHostConfigAutomaticDeployment extends TomcatBaseTest {

    private static final ContextName  APP_NAME = new ContextName("myapp", false);
    private static final File XML_SOURCE =
            new File("test/deployment/context.xml");
    private static final File WAR_XML_SOURCE =
            new File("test/deployment/context.war");
    private static final File WAR_XML_COPYXML_FALSE_SOURCE =
            new File("test/deployment/contextCopyXMLFalse.war");
    private static final File WAR_XML_COPYXML_TRUE_SOURCE =
            new File("test/deployment/contextCopyXMLTrue.war");
    private static final File WAR_XML_UNPACKWAR_FALSE_SOURCE =
            new File("test/deployment/contextUnpackWARFalse.war");
    private static final File WAR_XML_UNPACKWAR_TRUE_SOURCE =
            new File("test/deployment/contextUnpackWARTrue.war");
    private static final File WAR_SOURCE =
            new File("test/deployment/noContext.war");
    private static final File WAR_BROKEN_SOURCE =
            new File("test/deployment/broken.war");
    private static final File DIR_XML_SOURCE =
            new File("test/deployment/dirContext");
    private static final File DIR_XML_SOURCE_META_INF =
            new File("test/deployment/dirContext/META-INF");
    private static final File DIR_SOURCE =
            new File("test/deployment/dirNoContext");

    private static final int XML = 1;
    private static final int EXT = 2;
    private static final int WAR = 3;
    private static final int DIR = 4;
    private static final int DIR_XML = 5;

    private static final int NONE = 1;
    private static final int RELOAD = 2;
    private static final int REDEPLOY = 3;

    private static final String XML_COOKIE_NAME = "XML_CONTEXT";
    private static final String WAR_COOKIE_NAME = "WAR_CONTEXT";
    private static final String DIR_COOKIE_NAME = "DIR_CONTEXT";
    // private static final String DEFAULT_COOKIE_NAME = "JSESSIONID";

    private File external;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        Tomcat tomcat = getTomcatInstance();

        external = new File(getTemporaryDirectory(), "external");
        if (!external.exists() && !external.mkdir()) {
            Assert.fail("Unable to create external for test");
        }

        // Disable background thread
        tomcat.getEngine().setBackgroundProcessorDelay(-1);

        // Enable deployer
        tomcat.getHost().addLifecycleListener(new HostConfig());

        // Disable deployment on start up
        tomcat.getHost().setDeployOnStartup(false);

        // Clean-up after test
        addDeleteOnTearDown(new File(tomcat.basedir, "/conf"));
        addDeleteOnTearDown(external);
    }


    /*
     * Expected behaviour for deployment of an XML file.
     * deployXML  copyXML  unpackWARs      XML  WAR  DIR
     *    Y/N       Y/N       Y/N           Y    N    N
     *
     * Note: Context will fail to start because no valid docBase is present.
     */
    @Test
    public void testDeploymentXmlFFF() throws Exception {
        createXmlInConfigBaseForAppbase();
        doTestDeployment(false, false, false,
                LifecycleState.FAILED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlFFT() throws Exception {
        createXmlInConfigBaseForAppbase();
        doTestDeployment(false, false, true,
                LifecycleState.FAILED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlFTF() throws Exception {
        createXmlInConfigBaseForAppbase();
        doTestDeployment(false, true, false,
                LifecycleState.FAILED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlFTT() throws Exception {
        createXmlInConfigBaseForAppbase();
        doTestDeployment(false, true, true,
                LifecycleState.FAILED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlTFF() throws Exception {
        createXmlInConfigBaseForAppbase();
        doTestDeployment(true, false, false,
                LifecycleState.FAILED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlTFT() throws Exception {
        createXmlInConfigBaseForAppbase();
        doTestDeployment(true, false, true,
                LifecycleState.FAILED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlTTF() throws Exception {
        createXmlInConfigBaseForAppbase();
        doTestDeployment(true, true, false,
                LifecycleState.FAILED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlTTT() throws Exception {
        createXmlInConfigBaseForAppbase();
        doTestDeployment(true, true, true,
                LifecycleState.FAILED, XML_COOKIE_NAME, true, false, false);
    }


    /*
     * Expected behaviour for deployment of an XML file that points to an
     * external WAR.
     * deployXML  copyXML  unpackWARs      XML  WAR  DIR
     *    Y/N       Y/N        Y            Y    N    Y
     *    Y/N       Y/N        N            Y    N    N
     *
     * Notes: No WAR file is present in the appBase because it is an external
     *        WAR.
     *        Any context.xml file embedded in the external WAR file is ignored.
     */
    @Test
    public void testDeploymentXmlExternalWarXmlFFF() throws Exception {
        File war = createWar(WAR_XML_SOURCE, false);
        createXmlInConfigBaseForExternal(war);
        doTestDeployment(false, false, false,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalWarXmlFFT() throws Exception {
        File war = createWar(WAR_XML_SOURCE, false);
        createXmlInConfigBaseForExternal(war);
        doTestDeployment(false, false, true,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, true);
    }

    @Test
    public void testDeploymentXmlExternalWarXmlFTF() throws Exception {
        File war = createWar(WAR_XML_SOURCE, false);
        createXmlInConfigBaseForExternal(war);
        doTestDeployment(false, true, false,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalWarXmlFTT() throws Exception {
        File war = createWar(WAR_XML_SOURCE, false);
        createXmlInConfigBaseForExternal(war);
        doTestDeployment(false, true, true,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, true);
    }

    @Test
    public void testDeploymentXmlExternalWarXmlTFF() throws Exception {
        File war = createWar(WAR_XML_SOURCE, false);
        createXmlInConfigBaseForExternal(war);
        doTestDeployment(true, false, false,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalWarXmlTFT() throws Exception {
        File war = createWar(WAR_XML_SOURCE, false);
        createXmlInConfigBaseForExternal(war);
        doTestDeployment(true, false, true,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, true);
    }

    @Test
    public void testDeploymentXmlExternalWarXmlTTF() throws Exception {
        File war = createWar(WAR_XML_SOURCE, false);
        createXmlInConfigBaseForExternal(war);
        doTestDeployment(true, true, false,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalWarXmlTTT() throws Exception {
        File war = createWar(WAR_XML_SOURCE, false);
        createXmlInConfigBaseForExternal(war);
        doTestDeployment(true, true, true,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, true);
    }


    /*
     * Expected behaviour for deployment of an XML file that points to an
     * external DIR.
     * deployXML  copyXML  unpackWARs      XML  WAR  DIR
     *    Y/N       Y/N       Y/N           Y    N    N
     *
     * Notes: Any context.xml file embedded in the external DIR file is ignored.
     */
    @Test
    public void testDeploymentXmlExternalDirXmlFFF() throws Exception {
        File dir = createDirInExternal(true);
        createXmlInConfigBaseForExternal(dir);
        doTestDeployment(false, false, false,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalDirXmlFFT() throws Exception {
        File dir = createDirInExternal(true);
        createXmlInConfigBaseForExternal(dir);
        doTestDeployment(false, false, true,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalDirXmlFTF() throws Exception {
        File dir = createDirInExternal(true);
        createXmlInConfigBaseForExternal(dir);
        doTestDeployment(false, true, false,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalDirXmlFTT() throws Exception {
        File dir = createDirInExternal(true);
        createXmlInConfigBaseForExternal(dir);
        doTestDeployment(false, true, true,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalDirXmlTFF() throws Exception {
        File dir = createDirInExternal(true);
        createXmlInConfigBaseForExternal(dir);
        doTestDeployment(true, false, false,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalDirXmlTFT() throws Exception {
        File dir = createDirInExternal(true);
        createXmlInConfigBaseForExternal(dir);
        doTestDeployment(true, false, true,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalDirXmlTTF() throws Exception {
        File dir = createDirInExternal(true);
        createXmlInConfigBaseForExternal(dir);
        doTestDeployment(true, true, false,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }

    @Test
    public void testDeploymentXmlExternalDirXmlTTT() throws Exception {
        File dir = createDirInExternal(true);
        createXmlInConfigBaseForExternal(dir);
        doTestDeployment(true, true, true,
                LifecycleState.STARTED, XML_COOKIE_NAME, true, false, false);
    }


    /*
     * Expected behaviour for deployment of a WAR with an embedded XML file.
     * deployXML  copyXML  unpackWARs      XML  WAR  DIR
     *     N        Y/N        N            N    Y    N
     *     N        Y/N        Y            N    Y    Y
     *     Y         N         N            N    Y    N
     *     Y         N         Y            N    Y    Y
     *     Y         Y         N            Y    Y    N
     *     Y         Y         Y            Y    Y    Y
     */
    @Test
    public void testDeploymentWarXmlFFF() throws Exception {
        createWar(WAR_XML_SOURCE, true);
        doTestDeployment(false, false, false,
                LifecycleState.FAILED, null, false, true, false);
    }

    @Test
    public void testDeploymentWarXmlFFT() throws Exception {
        createWar(WAR_XML_SOURCE, true);
        doTestDeployment(false, false, true,
                LifecycleState.FAILED, null, false, true, true);
    }

    @Test
    public void testDeploymentWarXmlFTF() throws Exception {
        createWar(WAR_XML_SOURCE, true);
        doTestDeployment(false, true, false,
                LifecycleState.FAILED, null, false, true, false);
    }

    @Test
    public void testDeploymentWarXmlFTT() throws Exception {
        createWar(WAR_XML_SOURCE, true);
        doTestDeployment(false, true, true,
                LifecycleState.FAILED, null, false, true, true);
    }

    @Test
    public void testDeploymentWarXmlTFF() throws Exception {
        createWar(WAR_XML_SOURCE, true);
        doTestDeployment(true, false, false,
                LifecycleState.STARTED, WAR_COOKIE_NAME, false, true, false);
    }

    @Test
    public void testDeploymentWarXmlTFT() throws Exception {
        createWar(WAR_XML_SOURCE, true);
        doTestDeployment(true, false, true,
                LifecycleState.STARTED, WAR_COOKIE_NAME, false, true, true);
    }

    @Test
    public void testDeploymentWarXmlTTF() throws Exception {
        createWar(WAR_XML_SOURCE, true);
        doTestDeployment(true, true, false,
                LifecycleState.STARTED, WAR_COOKIE_NAME, true, true, false);
    }

    @Test
    public void testDeploymentWarXmlTTT() throws Exception {
        createWar(WAR_XML_SOURCE, true);
        doTestDeployment(true, true, true,
                LifecycleState.STARTED, WAR_COOKIE_NAME, true, true, true);
    }


    /*
     * Expected behaviour for deployment of a WAR without an embedded XML file.
     * deployXML  copyXML  unpackWARs      XML  WAR  DIR
     *    Y/N       Y/N        N            N    Y    N
     *    Y/N       Y/N        Y            N    Y    Y
     */
    @Test
    public void testDeploymentWarFFF() throws Exception {
        createWar(WAR_SOURCE, true);
        doTestDeployment(false, false, false,
                LifecycleState.STARTED, null, false, true, false);
    }

    @Test
    public void testDeploymentWarFFT() throws Exception {
        createWar(WAR_SOURCE, true);
        doTestDeployment(false, false, true,
                LifecycleState.STARTED, null, false, true, true);
    }

    @Test
    public void testDeploymentWarFTF() throws Exception {
        createWar(WAR_SOURCE, true);
        doTestDeployment(false, true, false,
                LifecycleState.STARTED, null, false, true, false);
    }

    @Test
    public void testDeploymentWarFTT() throws Exception {
        createWar(WAR_SOURCE, true);
        doTestDeployment(false, true, true,
                LifecycleState.STARTED, null, false, true, true);
    }

    @Test
    public void testDeploymentWarTFF() throws Exception {
        createWar(WAR_SOURCE, true);
        doTestDeployment(true, false, false,
                LifecycleState.STARTED, null, false, true, false);
    }

    @Test
    public void testDeploymentWarTFT() throws Exception {
        createWar(WAR_SOURCE, true);
        doTestDeployment(true, false, true,
                LifecycleState.STARTED, null, false, true, true);
    }

    @Test
    public void testDeploymentWarTTF() throws Exception {
        createWar(WAR_SOURCE, true);
        doTestDeployment(true, true, false,
                LifecycleState.STARTED, null, false, true, false);
    }

    @Test
    public void testDeploymentWarTTT() throws Exception {
        createWar(WAR_SOURCE, true);
        doTestDeployment(true, true, true,
                LifecycleState.STARTED, null, false, true, true);
    }


    /*
     * Expected behaviour for deployment of a DIR with an embedded XML file.
     * deployXML  copyXML  unpackWARs      XML  WAR  DIR
     *     N        Y/N       Y/N           N    N    Y
     *     Y         N        Y/N           N    N    Y
     *     Y         Y        Y/N           Y    N    Y
     */
    @Test
    public void testDeploymentDirXmlFFF() throws Exception {
        createDirInAppbase(true);
        doTestDeployment(false, false, false,
                LifecycleState.FAILED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirXmlFFT() throws Exception {
        createDirInAppbase(true);
        doTestDeployment(false, false, true,
                LifecycleState.FAILED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirXmlFTF() throws Exception {
        createDirInAppbase(true);
        doTestDeployment(false, true, false,
                LifecycleState.FAILED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirXmlFTT() throws Exception {
        createDirInAppbase(true);
        doTestDeployment(false, true, true,
                LifecycleState.FAILED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirXmlTFF() throws Exception {
        createDirInAppbase(true);
        doTestDeployment(true, false, false,
                LifecycleState.STARTED, DIR_COOKIE_NAME, false, false, true);
    }

    @Test
    public void testDeploymentDirXmlTFT() throws Exception {
        createDirInAppbase(true);
        doTestDeployment(true, false, true,
                LifecycleState.STARTED, DIR_COOKIE_NAME, false, false, true);
    }

    @Test
    public void testDeploymentDirXmlTTF() throws Exception {
        createDirInAppbase(true);
        doTestDeployment(true, true, false,
                LifecycleState.STARTED, DIR_COOKIE_NAME, true, false, true);
    }

    @Test
    public void testDeploymentDirXmlTTT() throws Exception {
        createDirInAppbase(true);
        doTestDeployment(true, true, true,
                LifecycleState.STARTED, DIR_COOKIE_NAME, true, false, true);
    }


    /*
     * Expected behaviour for deployment of a DIR without an embedded XML file.
     * deployXML  copyXML  unpackWARs      XML  WAR  DIR
     *    Y/N       Y/N       Y/N           N    N    Y
     */
    @Test
    public void testDeploymentDirFFF() throws Exception {
        createDirInAppbase(false);
        doTestDeployment(false, false, false,
                LifecycleState.STARTED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirFFT() throws Exception {
        createDirInAppbase(false);
        doTestDeployment(false, false, true,
                LifecycleState.STARTED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirFTF() throws Exception {
        createDirInAppbase(false);
        doTestDeployment(false, true, false,
                LifecycleState.STARTED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirFTT() throws Exception {
        createDirInAppbase(false);
        doTestDeployment(false, true, true,
                LifecycleState.STARTED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirTFF() throws Exception {
        createDirInAppbase(false);
        doTestDeployment(true, false, false,
                LifecycleState.STARTED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirTFT() throws Exception {
        createDirInAppbase(false);
        doTestDeployment(true, false, true,
                LifecycleState.STARTED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirTTF() throws Exception {
        createDirInAppbase(false);
        doTestDeployment(true, true, false,
                LifecycleState.STARTED, null, false, false, true);
    }

    @Test
    public void testDeploymentDirTTT() throws Exception {
        createDirInAppbase(false);
        doTestDeployment(true, true, true,
                LifecycleState.STARTED, null, false, false, true);
    }

    private void doTestDeployment(boolean deployXML, boolean copyXML,
            boolean unpackWARs, LifecycleState resultState, String cookieName,
            boolean resultXml, boolean resultWar, boolean resultDir)
            throws Exception {

        Tomcat tomcat = getTomcatInstance();

        // Start the instance
        tomcat.start();

        // Set the attributes
        StandardHost host = (StandardHost) tomcat.getHost();
        host.setDeployXML(deployXML);
        host.setCopyXML(copyXML);
        host.setUnpackWARs(unpackWARs);

        // Trigger automatic deployment
        host.backgroundProcess();

        // Test the results
        Context ctxt = (Context) tomcat.getHost().findChild(APP_NAME.getPath());
        if (resultState == null) {
            Assert.assertNull(ctxt);
        } else {
            Assert.assertNotNull(ctxt);
            Assert.assertEquals(resultState, ctxt.getState());
            Assert.assertEquals(cookieName, ctxt.getSessionCookieName());
        }

        File xml = new File(
                getConfigBaseFile(host), APP_NAME.getBaseName() + ".xml");
        Assert.assertEquals(
                Boolean.valueOf(resultXml), Boolean.valueOf(xml.isFile()));

        File war = new File(
                getAppBaseFile(host), APP_NAME.getBaseName() + ".war");
        Assert.assertEquals(
                Boolean.valueOf(resultWar), Boolean.valueOf(war.isFile()));

        File dir = new File(host.getAppBase(), APP_NAME.getBaseName());
        Assert.assertEquals(
                Boolean.valueOf(resultDir), Boolean.valueOf(dir.isDirectory()));
    }


    /*
     * Expected behaviour for the deletion of files.
     *
     * Artifacts present     Artifact     Artifacts remaining
     *  XML  WAR  EXT  DIR    Removed     XML  WAR  EXT DIR    Notes
     *   N    N    N    Y       DIR        -    -    -   N
     *   N    Y    N    N       WAR        -    N    -   -
     *   N    Y    N    Y       DIR        -    Y    -   R     1
     *   N    Y    N    Y       WAR        -    N    -   N
     *   Y    N    N    N       XML        N    -    -   -
     *   Y    N    N    Y       DIR        N    -    -   N
     *   Y    N    N    Y       XML        R    -    -   Y     2
     *   Y    N    Y    N       EXT        Y    -    N   -
     *   Y    N    Y    N       XML        N    -    Y   -
     *   Y    N    Y    Y       DIR        R    -    Y   R     1,2
     *   Y    N    Y    Y       EXT        Y    -    N   N
     *   Y    N    Y    Y       XML        N    -    Y   N
     *   Y    Y    N    N       WAR        N    N    -   -
     *   Y    Y    N    N       XML        N    N    -   -
     *   Y    Y    N    Y       DIR        R    Y    -   R     1,2
     *   Y    Y    N    Y       WAR        N    N    -   -
     *   Y    Y    N    Y       XML        R    Y    -   Y
     *
     * Notes: 1. The DIR will be re-created since unpackWARs is true.
     *        2. The XML will be extracted from the WAR/DIR if deployXML and
     *           copyXML are true.
     */
    @Test
    public void testDeleteDirRemoveDir() throws Exception {
        doTestDelete(false, false, false, false, true, DIR, false, false, false,
                null);
    }

    @Test
    public void testDeleteWarRemoveWar() throws Exception {
        doTestDelete(false, false, false, true, false, WAR, false, false, false,
                null);
    }

    @Test
    public void testDeleteWarDirRemoveDir() throws Exception {
        doTestDelete(false, false, false, true, true, DIR, false, true, true,
                WAR_COOKIE_NAME);
    }

    @Test
    public void testDeleteWarDirRemoveWar() throws Exception {
        doTestDelete(false, false, false, true, true, WAR, false, false, false,
                null);
    }

    @Test
    public void testDeleteXmlRemoveXml() throws Exception {
        doTestDelete(true, false, false, false, false, XML, false, false, false,
                null);
    }

    @Test
    public void testDeleteXmlDirRemoveDir() throws Exception {
        doTestDelete(true, false, false, false, true, DIR, false, false, false,
                null);
    }

    @Test
    public void testDeleteXmlDirRemoveXml() throws Exception {
        doTestDelete(true, false, false, false, true, XML, false, false, true,
                DIR_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlDirRemoveXmlCopyXml() throws Exception {
        ((StandardHost) getTomcatInstance().getHost()).setCopyXML(true);
        doTestDelete(true, false, false, false, true, XML, true, false, true,
                DIR_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlExtwarRemoveExt() throws Exception {
        doTestDelete(true, true, false, false, false, EXT, true, false, false,
                XML_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlExtdirRemoveExt() throws Exception {
        doTestDelete(true, false, true, false, false, EXT, true, false, false,
                XML_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlExtwarRemoveXml() throws Exception {
        doTestDelete(true, true, false, false, false, XML, false, false, false,
                null);
    }

    @Test
    public void testDeleteXmlExtdirRemoveXml() throws Exception {
        doTestDelete(true, false, true, false, false, XML, false, false, false,
                null);
    }

    @Test
    public void testDeleteXmlExtwarDirRemoveDir() throws Exception {
        doTestDelete(true, true, false, false, true, DIR, true, false, true,
                XML_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlExtwarDirRemoveExt() throws Exception {
        doTestDelete(true, true, false, false, true, EXT, true, false, false,
                XML_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlExtwarDirRemoveXml() throws Exception {
        doTestDelete(true, true, false, false, true, XML, false, false, false,
                null);
    }

    @Test
    public void testDeleteXmlWarRemoveWar() throws Exception {
        doTestDelete(true, false, false, true, false, WAR, false, false, false,
                null);
    }

    @Test
    public void testDeleteXmlWarRemoveXml() throws Exception {
        doTestDelete(true, false, false, true, false, XML, false, true, false,
                WAR_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlWarRemoveXmlCopyXml() throws Exception {
        ((StandardHost) getTomcatInstance().getHost()).setCopyXML(true);
        doTestDelete(true, false, false, true, false, XML, true, true, false,
                WAR_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlWarDirRemoveDir() throws Exception {
        doTestDelete(true, false, false, true, true, DIR, false, true, true,
                WAR_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlWarDirRemoveDirCopyXml() throws Exception {
        ((StandardHost) getTomcatInstance().getHost()).setCopyXML(true);
        doTestDelete(true, false, false, true, true, DIR, true, true, true,
                WAR_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlWarDirRemoveWar() throws Exception {
        doTestDelete(true, false, false, true, true, WAR, false, false, false,
                null);
    }

    @Test
    public void testDeleteXmlWarDirRemoveWarCopyXml() throws Exception {
        ((StandardHost) getTomcatInstance().getHost()).setCopyXML(true);
        doTestDelete(true, false, false, true, true, WAR, false, false, false,
                null);
    }

    @Test
    public void testDeleteXmlWarDirRemoveXml() throws Exception {
        doTestDelete(true, false, false, true, true, XML, false, true, true,
                DIR_COOKIE_NAME);
    }

    @Test
    public void testDeleteXmlWarDirRemoveXmlCopyXml() throws Exception {
        ((StandardHost) getTomcatInstance().getHost()).setCopyXML(true);
        doTestDelete(true, false, false, true, true, XML, true, true, true,
                WAR_COOKIE_NAME);
    }

    private void doTestDelete(boolean startXml, boolean startExternalWar,
            boolean startExternalDir, boolean startWar, boolean startDir,
            int toDelete, boolean resultXml, boolean resultWar,
            boolean resultDir, String resultCookieName) throws Exception {

        Tomcat tomcat = getTomcatInstance();
        StandardHost host = (StandardHost) tomcat.getHost();

        // Init
        File xml = null;
        File ext = null;
        File war = null;
        File dir = null;

        if (startXml && !startExternalWar && !startExternalDir) {
            xml = createXmlInConfigBaseForAppbase();
        }
        if (startExternalWar) {
            ext = createWar(WAR_XML_SOURCE, false);
            xml = createXmlInConfigBaseForExternal(ext);
        }
        if (startExternalDir) {
            ext = createDirInExternal(true);
            xml = createXmlInConfigBaseForExternal(ext);
        }
        if (startWar) {
            war = createWar(WAR_XML_SOURCE, true);
        }
        if (startDir) {
            dir = createDirInAppbase(true);
        }

        if ((startWar || startExternalWar) && !startDir) {
            host.setUnpackWARs(false);
            // WARDirContext always locks the WAR file so need to use
            // anti-resource locking to enable the WAR to be deleted
            host.setContextClass(AntiResourceLockingContext.class.getName());
        }

        // Deploy the files we copied
        tomcat.start();
        host.backgroundProcess();

        // Remove the specified file
        switch (toDelete) {
            case XML:
                ExpandWar.delete(xml);
                break;
            case EXT:
                ExpandWar.delete(ext);
                break;
            case WAR:
                ExpandWar.delete(war);
                break;
            case DIR:
                ExpandWar.delete(dir);
                break;
            default:
                Assert.fail();
        }

        // Trigger an auto-deployment cycle
        host.backgroundProcess();

        Context ctxt = (Context) host.findChild(APP_NAME.getName());

        // Check the results
        // External WAR and DIR should only be deleted if the test is testing
        // behaviour when the external resource is deleted
        if (toDelete == EXT) {
            if (ext == null) {
                Assert.fail();
            } else {
                Assert.assertFalse(ext.exists());
            }
        } else {
            if (startExternalWar) {
                if (ext == null) {
                    Assert.fail();
                } else {
                    Assert.assertTrue(ext.isFile());
                }
            }
            if (startExternalDir) {
                if (ext == null) {
                    Assert.fail();
                } else {
                    Assert.assertTrue(ext.isDirectory());
                }
            }
        }

        if (resultXml) {
            if (xml == null) {
                Assert.fail();
            } else {
                Assert.assertTrue(xml.isFile());
            }
        }
        if (resultWar) {
            if (war == null) {
                Assert.fail();
            } else {
                Assert.assertTrue(war.isFile());
            }
        }
        if (resultDir) {
            if (dir == null) {
                Assert.fail();
            } else {
                Assert.assertTrue(dir.isDirectory());
            }
        }

        if (!resultXml && (startExternalWar || startExternalDir)) {
            Assert.assertNull(ctxt);
        }
        if (!resultWar && !resultDir) {
            if (resultXml) {
                Assert.assertNotNull(ctxt);
                Assert.assertEquals(LifecycleState.FAILED, ctxt.getState());
            } else {
                Assert.assertNull(ctxt);
            }
        }

        if (ctxt != null) {
            Assert.assertEquals(resultCookieName, ctxt.getSessionCookieName());
        }
    }


    /*
     * Expected behaviour for modification of files.
     *
     * Artifacts present      Artifact   Artifacts remaining
     * XML  WAR  EXT  DIR    Modified    XML  WAR  EXT DIR   Action
     *  N    N    N    Y       DIR        -    -    -   M     None
     *  N    Y    N    N       WAR        -    M    -   -   Redeploy
     *  N    Y    N    Y       DIR        -    Y    -   M     None
     *  N    Y    N    Y       WAR        -    M    -   R   Redeploy
     *  Y    N    N    N       XML        M    -    -   -   Redeploy
     *  Y    N    N    Y       DIR        Y    -    -   M     None
     *  Y    N    N    Y       XML        M    -    -   Y   Redeploy
     *  Y    N    Y    N       EXT        Y    -    M   -   Reload if WAR
     *  Y    N    Y    N       XML        M    -    Y   -   Redeploy
     *  Y    N    Y    Y       DIR        Y    -    Y   M     None
     *  Y    N    Y    Y       EXT        Y    -    M   R    Reload
     *  Y    N    Y    Y       XML        M    -    Y   Y   Redeploy
     *  Y    Y    N    N       WAR        Y    M    -   -    Reload
     *  Y    Y    N    N       XML        M    Y    -   -   Redeploy
     *  Y    Y    N    Y       DIR        Y    Y    -   M     None
     *  Y    Y    N    Y       WAR        Y    M    -   -    Reload
     *  Y    Y    N    Y       XML        M    Y    -   Y   Redeploy
     */
    @Test
    public void testModifyDirUpdateDir() throws Exception {
        doTestModify(false, false, false, false, true, DIR,
                false, false, true, DIR_COOKIE_NAME, NONE);
    }

    @Test
    public void testModifyWarUpdateWar() throws Exception {
        doTestModify(false, false, false, true, false, WAR,
                false, true, false, WAR_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testModifyWarDirUpdateDir() throws Exception {
        // DIR_COOKIE_NAME since Tomcat is going to assume DIR is expanded WAR
        doTestModify(false, false, false, true, true, DIR,
                false, true, true, DIR_COOKIE_NAME, NONE);
    }

    @Test
    public void testModifyWarDirUpdateWar() throws Exception {
        doTestModify(false, false, false, true, true, WAR,
                false, true, true, WAR_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testModifyXmlUpdateXml() throws Exception {
        doTestModify(true, false, false, false, false, XML,
                true, false, false, XML_COOKIE_NAME, REDEPLOY,
                LifecycleState.FAILED);
    }

    @Test
    public void testModifyXmlDirUpdateDir() throws Exception {
        doTestModify(true, false, false, false, true, DIR,
                true, false, true, XML_COOKIE_NAME, NONE);
    }

    @Test
    public void testModifyXmlDirUpdateXml() throws Exception {
        doTestModify(true, false, false, false, true, XML,
                true, false, true, XML_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testModifyXmlExtwarUpdateExtwar() throws Exception {
        doTestModify(true, true, false, false, false, EXT,
                true, false, false, XML_COOKIE_NAME, RELOAD);
    }

    @Test
    public void testModifyXmlExtdirUpdateExtdir() throws Exception {
        doTestModify(true, false, true, false, false, EXT,
                true, false, false, XML_COOKIE_NAME, NONE);
    }

    @Test
    public void testModifyXmlExtwarUpdateXml() throws Exception {
        doTestModify(true, true, false, false, false, XML,
                true, false, false, XML_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testModifyXmlExtdirUpdateXml() throws Exception {
        doTestModify(true, false, true, false, false, XML,
                true, false, false, XML_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testModifyXmlExtwarDirUpdateDir() throws Exception {
        doTestModify(true, true, false, false, true, DIR,
                true, false, false, XML_COOKIE_NAME, NONE);
    }

    @Test
    public void testModifyXmlExtwarDirUpdateExt() throws Exception {
        doTestModify(true, true, false, false, true, EXT,
                true, false, true, XML_COOKIE_NAME, RELOAD);
    }

    @Test
    public void testModifyXmlExtwarDirUpdateXml() throws Exception {
        doTestModify(true, true, false, false, true, XML,
                true, false, false, XML_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testModifyXmlWarUpdateWar() throws Exception {
        doTestModify(true, false, false, true, false, WAR,
                true, true, false, XML_COOKIE_NAME, RELOAD);
    }

    @Test
    public void testModifyXmlWarUpdateXml() throws Exception {
        doTestModify(true, false, false, true, false, XML,
                true, true, false, XML_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testModifyXmlWarDirUpdateDir() throws Exception {
        doTestModify(true, false, false, true, true, DIR,
                true, true, true, XML_COOKIE_NAME, NONE);
    }

    @Test
    public void testModifyXmlWarDirUpdateWar() throws Exception {
        doTestModify(true, false, false, true, true, WAR,
                true, true, true, XML_COOKIE_NAME, RELOAD);
    }

    @Test
    public void testModifyXmlWarDirUpdateXml() throws Exception {
        doTestModify(true, false, false, true, true, XML,
                true, true, true, XML_COOKIE_NAME, REDEPLOY);
    }

    private void doTestModify(boolean startXml, boolean startExternalWar,
            boolean startExternalDir, boolean startWar, boolean startDir,
            int toModify, boolean resultXml, boolean resultWar,
            boolean resultDir, String resultCookieName, int resultAction)
            throws Exception {
        doTestModify(startXml, startExternalWar, startExternalDir, startWar,
                startDir, toModify, resultXml, resultWar, resultDir,
                resultCookieName, resultAction, LifecycleState.STARTED);
    }

    private void doTestModify(boolean startXml, boolean startExternalWar,
            boolean startExternalDir, boolean startWar, boolean startDir,
            int toModify, boolean resultXml, boolean resultWar,
            boolean resultDir, String resultCookieName, int resultAction,
            LifecycleState resultState) throws Exception {

        Tomcat tomcat = getTomcatInstance();
        StandardHost host = (StandardHost) tomcat.getHost();

        // Init
        File xml = null;
        File ext = null;
        File war = null;
        File dir = null;

        if (startXml && !startExternalWar && !startExternalDir) {
            xml = createXmlInConfigBaseForAppbase();
        }
        if (startExternalWar) {
            ext = createWar(WAR_XML_SOURCE, false);
            xml = createXmlInConfigBaseForExternal(ext);
        }
        if (startExternalDir) {
            ext = createDirInAppbase(true);
            xml = createXmlInConfigBaseForExternal(ext);
        }
        if (startWar) {
            war = createWar(WAR_XML_SOURCE, true);
        }
        if (startDir) {
            dir = createDirInAppbase(true);
        }

        if ((startWar || startExternalWar) && !startDir) {
            host.setUnpackWARs(false);
        }

        // Deploy the files we copied
        tomcat.start();
        host.backgroundProcess();

        // Update the last modified time. Make sure that the OS reports a change
        // in modification time that HostConfig can detect.
        switch (toModify) {
            case XML:
                if (xml == null) {
                    Assert.fail();
                } else {
                    xml.setLastModified(System.currentTimeMillis() -
                            10 * HostConfig.FILE_MODIFICATION_RESOLUTION_MS);
                }
                break;
            case EXT:
                if (ext == null) {
                    Assert.fail();
                } else {
                    if (!ext.setLastModified(System.currentTimeMillis() -
                            10 * HostConfig.FILE_MODIFICATION_RESOLUTION_MS)){
                        Assert.fail("Failed to set last modified time for " +
                                "external WAR file. This is expected (due to " +
                                "a JVM bug) with Java 6 on Windows.");
                    }
                }
                break;
            case WAR:
                if (war == null) {
                    Assert.fail();
                } else {
                    if (!war.setLastModified(System.currentTimeMillis() -
                            10 * HostConfig.FILE_MODIFICATION_RESOLUTION_MS)) {
                        Assert.fail("Failed to set last modified time for WAR " +
                                "file. This is expected (due to a JVM bug) " +
                                "with Java 6 on Windows.");
                    }
                }
                break;
            case DIR:
                if (dir == null) {
                    Assert.fail();
                } else {
                    dir.setLastModified(System.currentTimeMillis() -
                            10 * HostConfig.FILE_MODIFICATION_RESOLUTION_MS);
                }
                break;
            default:
                Assert.fail();
        }

        Context oldContext = (Context) host.findChild(APP_NAME.getName());
        StateTracker tracker = new StateTracker();
        oldContext.addLifecycleListener(tracker);

        // Trigger an auto-deployment cycle
        host.backgroundProcess();

        Context newContext = (Context) host.findChild(APP_NAME.getName());

        // Check the results
        if (resultXml) {
            if (xml == null) {
                Assert.fail();
            } else {
                Assert.assertTrue(xml.isFile());
            }
        }
        if (resultWar) {
            if (war == null) {
                Assert.fail();
            } else {
                Assert.assertTrue(war.isFile());
            }
        }
        if (resultDir) {
            if (dir == null) {
                Assert.fail();
            } else {
                Assert.assertTrue(dir.isDirectory());
            }
        }

        if (!resultXml && (startExternalWar || startExternalDir)) {
            Assert.assertNull(newContext);
        }
        if (!resultWar && !resultDir) {
            if (resultXml) {
                Assert.assertNotNull(newContext);
                if (!startExternalWar && !startExternalDir) {
                    Assert.assertEquals(LifecycleState.FAILED,
                            newContext.getState());
                } else {
                    Assert.assertEquals(LifecycleState.STARTED,
                            newContext.getState());
                }
            } else {
                Assert.assertNull(newContext);
            }
        }

        if (newContext != null) {
            Assert.assertEquals(resultCookieName,
                    newContext.getSessionCookieName());
            Assert.assertEquals(resultState, newContext.getState());
        }

        if (resultAction == NONE) {
            Assert.assertSame(oldContext, newContext);
            Assert.assertEquals("", tracker.getHistory());
        } else if (resultAction == RELOAD) {
            Assert.assertSame(oldContext, newContext);
            Assert.assertEquals("stopstart", tracker.getHistory());
        } else if (resultAction == REDEPLOY) {
            Assert.assertNotSame(oldContext, newContext);
            // No init or start as that will be in a new context object
            Assert.assertEquals("stopafter_destroy", tracker.getHistory());
        } else {
            Assert.fail();
        }
    }


    /*
     * Expected behaviour for the addition of files.
     *
     * Artifacts present   copyXML  deployXML  Artifact   Artifacts remaining
     * XML  WAR  EXT  DIR                       Added      XML  WAR  EXT DIR   Action
     *  N    Y    N    N      N        Y         DIR        -    Y    -   A     None
     *  N    N    N    Y      N        Y         WAR        -    A    -   R   Redeploy
     *  Y    N    N    N      N        Y         DIR        Y    -    -   A     None
     *  N    N    N    Y      N        Y         XML        A    -    -   Y   Redeploy
     *  Y    N    N    N      N        Y         WAR        Y    A    -   -    Reload
     *  N    Y    N    N      N        Y         XML        A    Y    -   -   Redeploy
     *  Y    Y    N    N      N        Y         DIR        Y    Y    -   A     None
     *  Y    N    N    Y      N        Y         WAR        Y    A    -   N    Reload
     *  N    Y    N    Y      N        Y         XML        A    Y    -   Y   Redeploy
     *  Y    N    Y    N      N        Y         DIR        Y    -    Y   A     None
     *  Y    N    Y    N      N        Y         WAR        Y    A    Y   -     None
     *  N    N    N    Y      N        Y         EXT        A    -    A   R   Redeploy
     *  N    Y    N    N      N        Y         EXT        A    Y    A   -   Redeploy
     *
     *  N    N    N    Y     Y/N       N       DIR+XML      -    -    -   Y   Redeploy (failed)
     *  N    N    N    Y      Y        Y       DIR+XML      A    -    -   Y   Redeploy
     *  N    N    N    Y      N        Y       DIR+XML      -    -    -   Y   Redeploy
     *
     * Addition of a file  is treated as if the added file has been modified
     * with the following additional actions:
     * - If a WAR is added, any DIR is removed and may be recreated depending on
     *   unpackWARs.
     * - If an XML file is added that refers to an external docBase any WAR or
     *   DIR in the appBase will be removed. The DIR may be recreated if the
     *   external resource is a WAR and unpackWARs is true.
     * - If a DIR is added when a WAR already exists and unpackWARs is false,
     *   the DIR will be ignored but a warning will be logged when the DIR is
     *   first detected. If the WAR is removed, the DIR will be left and may be
     *   deployed via automatic deployment.
     * - If a WAR is added when an external WAR already exists for the same
     *   context, the WAR will be treated the same way as a DIR is treated in
     *   the previous bullet point.
     */
    @Test
    public void testAdditionWarAddDir() throws Exception {
        doTestAddition(false, false, false, true, false, DIR,
                false, true, true, WAR_COOKIE_NAME, NONE);
    }

    @Test
    public void testAdditionDirAddWar() throws Exception {
        doTestAddition(false, false, false, false, true, WAR,
                false, true, true, WAR_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testAdditionXmlAddDir() throws Exception {
        doTestAddition(true, false, false, false, false, DIR,
                true, false, true, XML_COOKIE_NAME, NONE);
    }

    @Test
    public void testAdditionDirAddXml() throws Exception {
        doTestAddition(false, false, false, false, true, XML,
                true, false, true, XML_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testAdditionXmlAddWar() throws Exception {
        doTestAddition(true, false, false, false, false, WAR,
                true, true, false, XML_COOKIE_NAME, RELOAD);
    }

    @Test
    public void testAdditionWarAddXml() throws Exception {
        doTestAddition(false, false, false, true, false, XML,
                true, true, false, XML_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testAdditionXmlWarAddDir() throws Exception {
        doTestAddition(true, false, false, true, false, DIR,
                true, true, true, XML_COOKIE_NAME, NONE);
    }

    @Test
    public void testAdditionXmlDirAddWar() throws Exception {
        doTestAddition(true, false, false, false, true, WAR,
                true, true, false, XML_COOKIE_NAME, RELOAD);
    }

    @Test
    public void testAdditionWarDirAddXml() throws Exception {
        doTestAddition(false, false, false, true, true, XML,
                true, true, true, XML_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testAdditionXmlExtwarAddDir() throws Exception {
        doTestAddition(true, true, false, false, false, DIR,
                true, false, true, XML_COOKIE_NAME, NONE);
    }

    @Test
    public void testAdditionXmlExtdirAddDir() throws Exception {
        doTestAddition(true, false, true, false, false, DIR,
                true, false, true, XML_COOKIE_NAME, NONE);
    }

    @Test
    public void testAdditionXmlExtwarAddWar() throws Exception {
        doTestAddition(true, true, false, false, false, WAR,
                true, true, false, XML_COOKIE_NAME, NONE);
    }

    @Test
    public void testAdditionXmlExtdirAddWar() throws Exception {
        doTestAddition(true, false, true, false, false, WAR,
                true, true, false, XML_COOKIE_NAME, NONE);
    }

    @Test
    public void testAdditionDirAddXmlExtwar() throws Exception {
        doTestAddition(false, false, false, false, true, EXT,
                true, false, true, XML_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testAdditionWarAddXmlExtwar() throws Exception {
        doTestAddition(false, false, false, true, false, EXT,
                true, true, false, XML_COOKIE_NAME, REDEPLOY);
    }

    @Test
    public void testAdditionDirAddDirXmlTF() throws Exception {
        doTestAddition(false, false, false, false, true, true, false, DIR_XML,
                false, false, true, null, REDEPLOY, LifecycleState.FAILED);
    }

    @Test
    public void testAdditionDirAddDirXmlFF() throws Exception {
        doTestAddition(false, false, false, false, true, false, false, DIR_XML,
                false, false, true, null, REDEPLOY, LifecycleState.FAILED);
    }

    @Test
    public void testAdditionDirAddDirXmlTT() throws Exception {
        doTestAddition(false, false, false, false, true, true, true, DIR_XML,
                true, false, true, DIR_COOKIE_NAME, REDEPLOY,
                LifecycleState.STARTED);
    }

    @Test
    public void testAdditionDirAddDirXmlFT() throws Exception {
        doTestAddition(false, false, false, false, true, false, true, DIR_XML,
                false, false, true, DIR_COOKIE_NAME, REDEPLOY,
                LifecycleState.STARTED);
    }

    private void doTestAddition(boolean startXml, boolean startExternalWar,
            boolean startExternalDir, boolean startWar, boolean startDir,
            int toAdd, boolean resultXml, boolean resultWar,
            boolean resultDir, String resultCookieName, int resultAction)
            throws Exception {

        doTestAddition(startXml, startExternalWar, startExternalDir, startWar,
                startDir, false, true, toAdd, resultXml, resultWar, resultDir,
                resultCookieName, resultAction, LifecycleState.STARTED);
    }

    private void doTestAddition(boolean startXml, boolean startExternalWar,
            boolean startExternalDir, boolean startWar, boolean startDir,
            boolean copyXML, boolean deployXML, int toAdd, boolean resultXml,
            boolean resultWar, boolean resultDir, String resultCookieName,
            int resultAction, LifecycleState state)
            throws Exception {

        Tomcat tomcat = getTomcatInstance();
        StandardHost host = (StandardHost) tomcat.getHost();

        // Init
        File xml = null;
        File ext = null;
        File war = null;
        File dir = null;

        if (startXml && !startExternalWar && !startExternalDir) {
            xml = createXmlInConfigBaseForAppbase();
        }
        if (startExternalWar) {
            ext = createWar(WAR_XML_SOURCE, false);
            xml = createXmlInConfigBaseForExternal(ext);
        }
        if (startExternalDir) {
            ext = createDirInExternal(true);
            xml = createXmlInConfigBaseForExternal(ext);
        }
        if (startWar) {
            war = createWar(WAR_XML_SOURCE, true);
        }
        if (startDir) {
            dir = createDirInAppbase(toAdd != DIR_XML);
        }

        if ((startWar || startExternalWar) && !startDir) {
            host.setUnpackWARs(false);
        }

        host.setCopyXML(copyXML);
        host.setDeployXML(deployXML);

        // Deploy the files we copied
        tomcat.start();
        host.backgroundProcess();

        // Change the specified file
        switch (toAdd) {
            case XML:
                if (xml == null) {
                    xml = createXmlInConfigBaseForAppbase();
                } else {
                    Assert.fail();
                }
                break;
            case EXT:
                if (ext == null && xml == null) {
                    ext = createWar(WAR_XML_SOURCE, false);
                    xml = createXmlInConfigBaseForExternal(ext);
                } else {
                    Assert.fail();
                }
                break;
            case WAR:
                if (war == null) {
                    war = createWar(WAR_XML_SOURCE, true);
                } else {
                    Assert.fail();
                }
                break;
            case DIR:
                if (dir == null) {
                    dir = createDirInAppbase(true);
                } else {
                    Assert.fail();
                }
                break;
            case DIR_XML:
                dir = createDirXmlInAppbase();
                xml = getXmlInConfigBaseForAppbase();
                break;
            default:
                Assert.fail();
        }

        Context oldContext = (Context) host.findChild(APP_NAME.getName());
        StateTracker tracker = new StateTracker();
        oldContext.addLifecycleListener(tracker);

        // Trigger an auto-deployment cycle
        host.backgroundProcess();

        Context newContext = (Context) host.findChild(APP_NAME.getName());

        // Check the results
        if (resultXml) {
            if (xml == null) {
                Assert.fail();
            } else {
                Assert.assertTrue(xml.isFile());
            }
        }
        if (resultWar) {
            if (war == null) {
                Assert.fail();
            } else {
                Assert.assertTrue(war.isFile());
            }
        }
        if (resultDir) {
            if (dir == null) {
                Assert.fail();
            } else {
                Assert.assertTrue(dir.isDirectory());
            }
        }

        if (!resultXml && (startExternalWar || startExternalDir)) {
            Assert.assertNull(newContext);
        }
        if (!resultWar && !resultDir) {
            if (resultXml) {
                Assert.assertNotNull(newContext);
                if (!startExternalWar && !startExternalDir) {
                    Assert.assertEquals(LifecycleState.FAILED,
                            newContext.getState());
                } else {
                    Assert.assertEquals(LifecycleState.STARTED,
                            newContext.getState());
                }
            } else {
                Assert.assertNull(newContext);
            }
        }

        if (newContext != null) {
            Assert.assertEquals(resultCookieName,
                    newContext.getSessionCookieName());
        }

        if (resultAction == NONE) {
            Assert.assertSame(oldContext, newContext);
            Assert.assertEquals("", tracker.getHistory());
        } else if (resultAction == RELOAD) {
            Assert.assertSame(oldContext, newContext);
            Assert.assertEquals("stopstart", tracker.getHistory());
        } else if (resultAction == REDEPLOY) {
            if (newContext == null) {
                Assert.fail();
            } else {
                Assert.assertEquals(state, newContext.getState());
            }
            Assert.assertNotSame(oldContext, newContext);
            // No init or start as that will be in a new context object
            Assert.assertEquals("stopafter_destroy", tracker.getHistory());
        } else {
            Assert.fail();
        }
    }


    /*
     * Test context unpackWAR setting.
     * If context.getUnpackWAR != Host.getUnpackWARs the Host wins.
     */
    @Test
    public void testUnpackWARFFF() throws Exception  {
        doTestUnpackWAR(false, false, false, false);
    }

    @Test
    public void testUnpackWARFFT() throws Exception  {
        doTestUnpackWAR(false, false, true, false);
    }

    @Test
    public void testUnpackWARFTF() throws Exception  {
        doTestUnpackWAR(false, true, false, false);
    }

    @Test
    public void testUnpackWARFTT() throws Exception  {
        doTestUnpackWAR(false, true, true, false);
    }

    @Test
    public void testUnpackWARTFF() throws Exception  {
        doTestUnpackWAR(true, false, false, false);
    }

    @Test
    public void testUnpackWARTFT() throws Exception  {
        // External WAR - therefore XML in WAR will be ignored
        doTestUnpackWAR(true, false, true, true);
    }

    @Test
    public void testUnpackWARTTF() throws Exception  {
        doTestUnpackWAR(true, true, false, true);
    }

    @Test
    public void testUnpackWARTTT() throws Exception  {
        doTestUnpackWAR(true, true, true, true);
    }

    private void doTestUnpackWAR(boolean unpackWARs, boolean unpackWAR,
            boolean external, boolean resultDir) throws Exception {

        Tomcat tomcat = getTomcatInstance();
        StandardHost host = (StandardHost) tomcat.getHost();

        host.setUnpackWARs(unpackWARs);

        tomcat.start();

        File war;
        if (unpackWAR) {
            war = createWar(WAR_XML_UNPACKWAR_TRUE_SOURCE, !external);
        } else {
            war = createWar(WAR_XML_UNPACKWAR_FALSE_SOURCE, !external);
        }
        if (external) {
            createXmlInConfigBaseForExternal(war);
        }

        host.backgroundProcess();

        File dir = new File(host.getAppBase(), APP_NAME.getBaseName());
        Assert.assertEquals(
                Boolean.valueOf(resultDir), Boolean.valueOf(dir.isDirectory()));
    }


    @Test
    public void testBrokenAppWithAntiLockingF() throws Exception {
        testBrokenAppWithAntiLocking(false);
    }

    @Test
    public void testBrokenAppWithAntiLockingT() throws Exception {
        testBrokenAppWithAntiLocking(true);
    }

    private void testBrokenAppWithAntiLocking(boolean unpackWARs)
            throws Exception {

        Tomcat tomcat = getTomcatInstance();
        StandardHost host = (StandardHost) tomcat.getHost();

        host.setUnpackWARs(unpackWARs);

        File war = createWar(WAR_BROKEN_SOURCE, false);
        createXmlInConfigBaseForExternal(war, true);

        File dir = new File(getAppBaseFile(host), APP_NAME.getBaseName());

        tomcat.start();

        // Simulate deploy on start-up
        tomcat.getHost().backgroundProcess();

        Assert.assertTrue(war.isFile());
        if (unpackWARs) {
            Assert.assertTrue(dir.isDirectory());
        }
    }

    private File createDirInAppbase(boolean withXml) throws IOException {
        File dir = new File(getAppBaseFile(getTomcatInstance().getHost()),
                APP_NAME.getBaseName());
        if (withXml) {
            recursiveCopy(DIR_XML_SOURCE, dir);
        } else {
            recursiveCopy(DIR_SOURCE, dir);
        }
        return dir;
    }

    private File createDirXmlInAppbase() throws IOException {
        File dir = new File(getAppBaseFile(getTomcatInstance().getHost()),
                APP_NAME.getBaseName() + "/META-INF");
        recursiveCopy(DIR_XML_SOURCE_META_INF, dir);
        return dir;
    }

    private File createDirInExternal(boolean withXml) throws IOException {
        File ext = new File(external, "external" + ".war");
        if (withXml) {
            recursiveCopy(DIR_XML_SOURCE, ext);
        } else {
            recursiveCopy(DIR_SOURCE, ext);
        }
        return ext;
    }

    private File createWar(File src, boolean useAppbase) throws IOException {
        File dest;
        if (useAppbase) {
            dest = new File(getAppBaseFile(getTomcatInstance().getHost()),
                APP_NAME.getBaseName() + ".war");
        } else {
            dest = new File(external, "external" + ".war");
        }
        copy(src, dest);
        // Make sure that HostConfig thinks the WAR has been modified.
        dest.setLastModified(
                System.currentTimeMillis() - 2 * HostConfig.FILE_MODIFICATION_RESOLUTION_MS);
        return dest;
    }

    private File createXmlInConfigBaseForAppbase() throws IOException {
        File xml = getXmlInConfigBaseForAppbase();
        File parent = xml.getParentFile();
        if (!parent.isDirectory()) {
            Assert.assertTrue(parent.mkdirs());
        }
        copy(XML_SOURCE, xml);
        // Make sure that HostConfig thinks the xml has been modified.
        xml.setLastModified(
                System.currentTimeMillis() - 2 * HostConfig.FILE_MODIFICATION_RESOLUTION_MS);
        return xml;
    }

    private File getXmlInConfigBaseForAppbase() {
        Host host = getTomcatInstance().getHost();
        return new File(getConfigBaseFile(host), APP_NAME + ".xml");
    }

    private File createXmlInConfigBaseForExternal(File ext) throws IOException {
        return createXmlInConfigBaseForExternal(ext, false);
    }

    private File createXmlInConfigBaseForExternal(File ext, boolean antiLocking)
            throws IOException {
        File xml = new File(getConfigBaseFile(getTomcatInstance().getHost()),
                APP_NAME + ".xml");
        File parent = xml.getParentFile();
        if (!parent.isDirectory()) {
            Assert.assertTrue(parent.mkdirs());
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(xml);
            StringBuilder context = new StringBuilder();
            context.append("<Context sessionCookieName=\"");
            context.append(XML_COOKIE_NAME);
            context.append("\" docBase=\"");
            context.append(ext.getAbsolutePath());
            if (antiLocking) {
                context.append("\" antiResourceLocking=\"true");
            }
            context.append("\" />");
            fos.write(context.toString().getBytes(B2CConverter.ISO_8859_1));
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        // Make sure that HostConfig thinks the xml has been modified.
        xml.setLastModified(
                System.currentTimeMillis() - 2 * HostConfig.FILE_MODIFICATION_RESOLUTION_MS);
        return xml;
    }


    @Test
    public void testSetContextClassName() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        Host host = tomcat.getHost();
        if (host instanceof StandardHost) {
            StandardHost standardHost = (StandardHost) host;
            standardHost.setContextClass(TesterContext.class.getName());
        }

        // Copy the WAR file
        File war = new File(getAppBaseFile(host),
                APP_NAME.getBaseName() + ".war");
        copy(WAR_XML_SOURCE, war);

        // Deploy the copied war
        tomcat.start();
        host.backgroundProcess();

        // Check the Context class
        Context ctxt = (Context) host.findChild(APP_NAME.getName());

        Assert.assertTrue(ctxt instanceof TesterContext);
    }


    public static class AntiResourceLockingContext extends StandardContext {

        @Override
        public boolean getAntiResourceLocking() {
            return true;
        }
    }


    public static class TesterContext extends StandardContext {
        // No functional change
    }


    private static class StateTracker implements LifecycleListener {

        private StringBuilder stateHistory = new StringBuilder();

        @Override
        public void lifecycleEvent(LifecycleEvent event) {

            String type = event.getType();

            if (type.equals(Lifecycle.START_EVENT) ||
                    type.equals(Lifecycle.STOP_EVENT) ||
                    type.equals(Lifecycle.AFTER_DESTROY_EVENT)) {
                stateHistory.append(type);
            }
        }


        public String getHistory() {
            return stateHistory.toString();
        }
    }


    /*
     * Test context copyXML setting.
     * If context.copyXML != Host.copyXML the Host wins.
     * For external WARs, a context.xml must always already exist
     */
    @Test
    public void testCopyXMLFFF() throws Exception  {
        doTestCopyXML(false, false, false, false);
    }

    @Test
    public void testCopyXMLFFT() throws Exception  {
        doTestCopyXML(false, false, true, true);
    }

    @Test
    public void testCopyXMLFTF() throws Exception  {
        doTestCopyXML(false, true, false, true);
    }

    @Test
    public void testCopyXMLFTT() throws Exception  {
        doTestCopyXML(false, true, true, true);
    }

    @Test
    public void testCopyXMLTFF() throws Exception  {
        doTestCopyXML(true, false, false, true);
    }

    @Test
    public void testCopyXMLTFT() throws Exception  {
        doTestCopyXML(true, false, true, true);
    }

    @Test
    public void testCopyXMLTTF() throws Exception  {
        doTestCopyXML(true, true, false, true);
    }

    @Test
    public void testCopyXMLTTT() throws Exception  {
        doTestCopyXML(true, true, true, true);
    }

    private void doTestCopyXML(boolean copyXmlHost, boolean copyXmlWar,
            boolean external, boolean resultXml) throws Exception {

        Tomcat tomcat = getTomcatInstance();
        StandardHost host = (StandardHost) tomcat.getHost();

        host.setCopyXML(copyXmlHost);

        tomcat.start();

        File war;
        if (copyXmlWar) {
            war = createWar(WAR_XML_COPYXML_TRUE_SOURCE, !external);
        } else {
            war = createWar(WAR_XML_COPYXML_FALSE_SOURCE, !external);
        }
        if (external) {
            createXmlInConfigBaseForExternal(war);
        }

        host.backgroundProcess();

        File xml = new File(getConfigBaseFile(host),
                APP_NAME.getBaseName() + ".xml");
        Assert.assertEquals(
                Boolean.valueOf(resultXml), Boolean.valueOf(xml.isFile()));

        Context context = (Context) host.findChild(APP_NAME.getName());
        if (external) {
            Assert.assertEquals(XML_COOKIE_NAME,
                    context.getSessionCookieName());
        } else {
            Assert.assertEquals(WAR_COOKIE_NAME,
                    context.getSessionCookieName());
        }
    }


    // Static methods to compensate for methods that are present in 8.0.x but
    // not in 7.0.x

    private static File getConfigBaseFile(Host host) {
        String path = null;
        if (host.getXmlBase() != null) {
            path = host.getXmlBase();
        } else {
            StringBuilder xmlDir = new StringBuilder("conf");
            Container parent = host.getParent();
            if (parent instanceof Engine) {
                xmlDir.append('/');
                xmlDir.append(parent.getName());
            }
            xmlDir.append('/');
            xmlDir.append(host.getName());
            path = xmlDir.toString();
        }

        return getCanonicalPath(path);
    }

    private static File getAppBaseFile(Host host) {
        return getCanonicalPath(host.getAppBase());
    }

    private static File getCanonicalPath(String path) {
        File file = new File(path);
        File base = new File(System.getProperty(Globals.CATALINA_BASE_PROP));
        if (!file.isAbsolute())
            file = new File(base,path);
        try {
            return file.getCanonicalFile();
        } catch (IOException e) {
            return file;
        }
    }


    // Static methods to replace the java.nio.file.Files methods used in Tomcat
    // 8 that aren't available in Tomcat 7. These methods are not intended to be
    // 100% robust - just good enough for the unit tests to pass.

    private static void copy(File src, File dest) throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dest);
            IOTools.flow(in, out);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
        dest.setLastModified(
                System.currentTimeMillis() - 2 * HostConfig.FILE_MODIFICATION_RESOLUTION_MS);
    }


    private static void recursiveCopy(File src, File dest) throws IOException {
        dest.mkdirs();
        File[] files = src.listFiles();
        if (files != null) {
            for (File file : files) {
                File newFile = new File(dest, file.getName());
                if (file.isDirectory()) {
                    recursiveCopy(file, newFile);
                } else {
                    copy(file, newFile);
                }
            }
        }
    }
}
