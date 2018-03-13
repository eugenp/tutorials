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
package org.apache.juli;

import java.io.File;
import java.net.URLDecoder;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.catalina.startup.LoggingBaseTest;

public class TestFileHandlerNonRotatable extends LoggingBaseTest {
    private FileHandler testHandler;

    @BeforeClass
    public static void setUpPerTestClass() throws Exception {
        System.setProperty("java.util.logging.manager",
                "org.apache.juli.ClassLoaderLogManager");
        String configLoggingPath = TestFileHandlerNonRotatable.class
                .getResource("logging-non-rotatable.properties")
                .getFile();
        System.setProperty("java.util.logging.config.file",
                URLDecoder.decode(configLoggingPath, "UTF-8"));
    }

    @Override
    @After
    public void tearDown() throws Exception {
        if (testHandler != null) {
            testHandler.close();
        }
        super.tearDown();
    }

    @Test
    public void testBug61232() throws Exception {
        testHandler = new FileHandler(this.getTemporaryDirectory().toString(),
                "juli.", ".log");

        File logFile = new File(this.getTemporaryDirectory(), "juli.log");
        assertTrue(logFile.exists());
    }

    @Test
    public void testCustomSuffixWithoutSeparator() throws Exception {
        testHandler = new FileHandler(this.getTemporaryDirectory().toString(),
                "juli.", "log");

        File logFile = new File(this.getTemporaryDirectory(), "juli.log");
        assertTrue(logFile.exists());
    }

    @Test
    public void testCustomPrefixWithoutSeparator() throws Exception {
        testHandler = new FileHandler(this.getTemporaryDirectory().toString(),
                "juli", ".log");

        File logFile = new File(this.getTemporaryDirectory(), "juli.log");
        assertTrue(logFile.exists());
    }
}