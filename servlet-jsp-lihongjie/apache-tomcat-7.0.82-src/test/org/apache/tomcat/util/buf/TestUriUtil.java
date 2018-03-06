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
package org.apache.tomcat.util.buf;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class TestUriUtil {

    @Test
    public void testBuildJarUrl01() throws MalformedURLException {
        File jarFile = new File("/patha/pathb!/pathc");
        String result = UriUtil.buildJarUrl(jarFile).toString();

        int index = result.indexOf("!/");
        Assert.assertEquals(result, result.length() - 2, index);
    }


    @Test
    public void testBuildJarUrl02() throws MalformedURLException {
        File jarFile = new File("/patha/pathb*/pathc");
        String result = UriUtil.buildJarUrl(jarFile).toString();

        int index = result.indexOf("!/");
        Assert.assertEquals(result,  result.length() - 2, index);

        index = result.indexOf("*/");
        Assert.assertEquals(result, -1, index);
    }


    @Test
    public void testBuildJarUrl03() throws MalformedURLException {
        File jarFile = new File("/patha/pathb^/pathc");
        String result = UriUtil.buildJarUrl(jarFile).toString();

        int index = result.indexOf("!/");
        Assert.assertEquals(result, result.length() - 2, index);

        index = result.indexOf("^/");
        Assert.assertEquals(result, -1, index);
    }


    // @Test /* Uncomment to test performance for different implementations. */
    public void performanceTestBuildJarUrl() throws MalformedURLException {
        File jarFile = new File("/patha/pathb^/pathc");

        URL url = null;

        int count = 1000000;

        // Warm up
        for (int i = 0; i < count / 10; i++) {
            url = UriUtil.buildJarUrl(jarFile);
        }

        // Test
        long start = System.nanoTime();
        for (int i = 0; i < count / 10; i++) {
            url = UriUtil.buildJarUrl(jarFile);
        }
        long duration = System.nanoTime() - start;

        System.out.println("[" + count + "] iterations took [" +
                duration + "] ns for [" + url + "]");
    }
}
