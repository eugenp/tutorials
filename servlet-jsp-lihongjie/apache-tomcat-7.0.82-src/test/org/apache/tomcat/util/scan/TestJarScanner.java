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

package org.apache.tomcat.util.scan;

import java.util.StringTokenizer;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.startup.TomcatBaseTest;

public class TestJarScanner extends TomcatBaseTest {

    @Test
    public void testJarsToSkipFormat() {

        testJarsToSkipFormat(
                org.apache.tomcat.util.scan.Constants.SKIP_JARS_PROPERTY, false);
        testJarsToSkipFormat(
                org.apache.catalina.startup.Constants.PLUGGABILITY_JARS_TO_SKIP,
                true);
        testJarsToSkipFormat(
                org.apache.catalina.startup.Constants.TLD_JARS_TO_SKIP,
                true);

    }

    private static void testJarsToSkipFormat(String propertyName, boolean allowEmpty) {
        String jarList = System.getProperty(propertyName);
        if (jarList == null) {
            if (!allowEmpty) {
                Assert.fail("[" + propertyName + "]: Jar skip list property is not set");
            }
            return;
        }
        jarList = jarList.trim();
        if (jarList.isEmpty()) {
            if (!allowEmpty) {
                Assert.fail("[" + propertyName + "]: Jar skip list is empty");
            }
            return;
        }

        StringTokenizer tokenizer = new StringTokenizer(jarList, ",");
        String token;
        while (tokenizer.hasMoreElements()) {
            token = tokenizer.nextToken().trim();
            Assert.assertTrue("[" + propertyName + "]: Token \"" + token
                    + "\" does not end with \".jar\"", token.endsWith(".jar"));
            Assert.assertEquals("[" + propertyName + "]: Token \"" + token
                    + "\" contains sub string \".jar\""
                    + " or separator \",\" is missing",
                    token.length() - ".jar".length(), token.indexOf(".jar"));
        }
    }
}
