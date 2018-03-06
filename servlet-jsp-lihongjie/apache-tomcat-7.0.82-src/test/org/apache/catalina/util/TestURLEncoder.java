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
package org.apache.catalina.util;

import org.junit.Assert;
import org.junit.Test;

public class TestURLEncoder {

    private static final String SPACE = " ";
    private static final String DOLLAR = "$";
    private static final String AMPERSAND = "&";
    private static final String AMPERSAND_ENCODED = "%26";

    @Test
    public void testClone() {
        URLEncoder original = new URLEncoder();
        URLEncoder clone = (URLEncoder) original.clone();

        // Ensure encode as space is not shared
        original.setEncodeSpaceAsPlus(true);
        Assert.assertNotEquals(original.encode(SPACE, "UTF-8"), clone.encode(SPACE, "UTF-8"));

        // Ensure safe characters is not shared
        original.addSafeCharacter('$');
        Assert.assertNotEquals(original.encode(DOLLAR, "UTF-8"), clone.encode(DOLLAR, "UTF-8"));
    }


    @Test
    public void testRemoveSafeCharacter() {
        URLEncoder xml = (URLEncoder) URLEncoder.DEFAULT.clone();
        // This should not encode '&'
        Assert.assertEquals(AMPERSAND, xml.encode(AMPERSAND, "UTF-8"));
        xml.removeSafeCharacter('&');
        Assert.assertEquals(AMPERSAND_ENCODED, xml.encode(AMPERSAND, "UTF-8"));
    }
}
