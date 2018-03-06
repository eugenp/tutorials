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
 *
 */
package org.apache.tomcat.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.naming.resources.DirContextURLStreamHandlerFactory;
import org.apache.tomcat.util.http.fileupload.FileUtils;

public class TestConfigFileLoader {

    @BeforeClass
    public static void setup() {
        URL.setURLStreamHandlerFactory(
                DirContextURLStreamHandlerFactory.getInstance());
        File buildDir = new File(
                System.getProperty("tomcat.test.tomcatbuild", "output/build"));
        System.setProperty("catalina.base", buildDir.getAbsolutePath());
    }

    @Test
    public void test01() throws IOException {
        doTest("classpath:org/apache/catalina/mbeans-descriptors.xml");
    }

    @Test(expected=FileNotFoundException.class)
    public void test02() throws IOException {
        doTest("classpath:org/apache/catalina/foo");
    }

    @Test
    public void test03() throws IOException {
        doTest("conf/server.xml");
    }

    @Test(expected=FileNotFoundException.class)
    public void test04() throws IOException {
        doTest("conf/unknown");
    }

    @Test
    public void testAbsolutePath() throws IOException {
        File test = new File(System.getProperty("java.io.tmpdir"), "testAbsolutePath");
        if (test.exists()) {
            FileUtils.forceDelete(test);
        }
        test.createNewFile();
        doTest(test.getAbsolutePath());
    }

    private void doTest(String path) throws IOException {
        InputStream is = null;
        try {
            is = ConfigFileLoader.getInputStream(path);
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}