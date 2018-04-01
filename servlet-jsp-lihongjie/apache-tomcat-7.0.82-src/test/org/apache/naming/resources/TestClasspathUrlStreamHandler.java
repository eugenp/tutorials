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
package org.apache.naming.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestClasspathUrlStreamHandler {

    @BeforeClass
    public static void setup() {
        URL.setURLStreamHandlerFactory(DirContextURLStreamHandlerFactory.getInstance());
    }

    @Test
    public void testClasspathURL01() throws IOException {
        URL u = new URL("classpath:/org/apache/naming/resources/LocalStrings.properties");
        InputStream is = null;
        Properties p = new Properties();
        try {
            is = u.openStream();
            p.load(is);
        } finally {
           if (is != null) {
               is.close();
           }
        }
        String msg = (String) p.get("resources.null");
        Assert.assertEquals("Document base cannot be null",  msg);
    }
}