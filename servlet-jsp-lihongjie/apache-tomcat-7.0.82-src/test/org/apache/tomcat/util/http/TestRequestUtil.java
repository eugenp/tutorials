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
package org.apache.tomcat.util.http;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestRequestUtil {

    @Test
    public void testNormalize01() {
        doTestNormalize("//something", "/something");
    }

    @Test
    public void testNormalize02() {
        doTestNormalize("some//thing", "/some/thing");
    }

    @Test
    public void testNormalize03() {
        doTestNormalize("something//", "/something/");
    }

    @Test
    public void testNormalize04() {
        doTestNormalize("//", "/");
    }

        @Test
    public void testNormalize05() {
        doTestNormalize("//", "/");
    }

    @Test
    public void testNormalize06() {
        doTestNormalize("///", "/");
    }

    @Test
    public void testNormalize07() {
        doTestNormalize("////", "/");
    }

    @Test
    public void testNormalize08() {
        doTestNormalize("/.", "/");
    }

    @Test
    public void testNormalize09() {
        doTestNormalize("/./", "/");
    }

    @Test
    public void testNormalize10() {
        doTestNormalize(".", "/");
    }

    @Test
    public void testNormalize11() {
        doTestNormalize("/..", null);
    }

    @Test
    public void testNormalize12() {
        doTestNormalize("/../", null);
    }

    @Test
    public void testNormalize13() {
        doTestNormalize("..", null);
    }

    @Test
    public void testNormalize14() {
        doTestNormalize("//..", null);
    }

    @Test
    public void testNormalize15() {
        doTestNormalize("//../", null);
    }

    @Test
    public void testNormalize16() {
        doTestNormalize("/./..", null);
    }

    @Test
    public void testNormalize17() {
        doTestNormalize("/./../", null);
    }

    @Test
    public void testNormalize18() {
        doTestNormalize("/a/../..", null);
    }

    @Test
    public void testNormalize19() {
        doTestNormalize("/a/../../", null);
    }

    @Test
    public void testNormalize20() {
        doTestNormalize("/a/..", "/");
    }

    @Test
    public void testNormalize21() {
        doTestNormalize("/a/.", "/a");
    }

    @Test
    public void testNormalize22() {
        doTestNormalize("/a/../", "/");
    }

    @Test
    public void testNormalize23() {
        doTestNormalize("/a/./", "/a/");
    }

    @Test
    public void testNormalize24() {
        doTestNormalize("/a/b/..", "/a");
    }

    @Test
    public void testNormalize25() {
        doTestNormalize("/a/b/.", "/a/b");
    }

    @Test
    public void testNormalize26() {
        doTestNormalize("/a/b/../", "/a/");
    }

    @Test
    public void testNormalize27() {
        doTestNormalize("/a/b/./", "/a/b/");
    }

    private void doTestNormalize(String input, String expected) {
        assertEquals(expected,RequestUtil.normalize(input));
    }
}
