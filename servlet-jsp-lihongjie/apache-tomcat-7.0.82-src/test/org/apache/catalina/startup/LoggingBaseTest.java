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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Base class that provides logging support for test cases that respects the
 * standard conf/logging.properties configuration file.
 *
 * <p>
 * It also provides support for cleaning up temporary files after shutdown. See
 * {@link #addDeleteOnTearDown(File)}.
 *
 * <p>
 * <em>Note</em> that the logging configuration uses
 * <code>${catalina.base}</code> value and thus we take care about that property
 * even if the tests do not use Tomcat.
 */
public abstract class LoggingBaseTest {

    protected Log log;

    private File tempDir;

    private List<File> deleteOnTearDown = new ArrayList<File>();

    /**
     * Provides name of the currently executing test method.
     */
    @Rule
    public final TestName testName = new TestName();

    /**
     * Helper method that returns the directory where Tomcat build resides. It
     * is used to access resources that are part of default Tomcat deployment.
     * E.g. the examples webapp.
     */
    public static File getBuildDirectory() {
        return new File(System.getProperty("tomcat.test.tomcatbuild",
                "output/build"));
    }

    /**
     * Helper method that returns the path of the temporary directory used by
     * the test runs. The directory is configured during {@link #setUp()}.
     *
     * <p>
     * It is used as <code>${catalina.base}</code> for the instance of Tomcat
     * that is being started, but can be used to store other temporary files as
     * well. Its <code>work</code> and <code>webapps</code> subdirectories are
     * deleted at {@link #tearDown()}. If you have other files or directories
     * that have to be deleted on cleanup, register them with
     * {@link #addDeleteOnTearDown(File)}.
     */
    public File getTemporaryDirectory() {
        return tempDir;
    }

    /**
     * Schedule the given file or directory to be deleted during after-test
     * cleanup.
     *
     * @param file
     *            File or directory
     */
    public void addDeleteOnTearDown(File file) {
        deleteOnTearDown.add(file);
    }

    @BeforeClass
    public static void setUpPerTestClass() throws Exception {
        // Configure logging
        System.setProperty("java.util.logging.manager",
                "org.apache.juli.ClassLoaderLogManager");
        System.setProperty("java.util.logging.config.file", new File(
                getBuildDirectory(), "conf/logging.properties").toString());
    }

    @Before
    public void setUp() throws Exception {
        // Create catalina.base directory
        tempDir = new File(System.getProperty("tomcat.test.temp", "output/tmp"));
        if (!tempDir.mkdirs() && !tempDir.isDirectory()) {
            fail("Unable to create temporary directory for test");
        }

        System.setProperty("catalina.base", tempDir.getAbsolutePath());

        // Get log instance after logging has been configured
        log = LogFactory.getLog(getClass());
        log.info("Starting test case [" + testName.getMethodName() + "]");
    }

    @After
    public void tearDown() throws Exception {
        for (File file : deleteOnTearDown) {
            ExpandWar.delete(file);
        }
        deleteOnTearDown.clear();
    }
}
